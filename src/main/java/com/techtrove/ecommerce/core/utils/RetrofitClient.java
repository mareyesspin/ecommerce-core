package com.techtrove.ecommerce.core.utils;


import com.techtrove.ecommerce.core.enums.SpeiErrorCode;
import com.techtrove.ecommerce.core.models.errors.ErrorArgument;
import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import com.techtrove.ecommerce.core.enums.GenericErrorCode;
import com.techtrove.ecommerce.core.models.exception.AbstractCoreException;
import com.techtrove.ecommerce.core.models.exception.ECommInternalServerErrorException;
import com.techtrove.ecommerce.core.models.exception.ECommServiceUnavailableException;
import com.techtrove.ecommerce.core.models.response.ErrorException.ErrorMessageResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Optional;

@Data
@Slf4j
@Component
@Scope("prototype")
public class RetrofitClient {


    public <T> T executeService(Call<T> callRequest){
        Response<T> response = null;
        ResponseBody errorBody = null;
        try {

            // se invoca el endpoint
            response = callRequest.execute();
        }catch(ConnectException ex){
            log.error("failed to connect: {}", ExceptionUtils.getSimplifiedStackTrace(ex));
            throw new ECommServiceUnavailableException(SpeiErrorCode.SERVICE_API_UNAVAILABLE.getDescription(),
                    SpeiErrorCode.SERVICE_API_UNAVAILABLE.getInstance().addArg(new ErrorArgument(ErrorArgument.REASON, ex.getMessage())));
        }catch (Throwable e) {
            log.error("Error calling service API {}", ExceptionUtils.getSimplifiedStackTrace(e));
            throw new ECommServiceUnavailableException(SpeiErrorCode.SERVICE_API_UNAVAILABLE.getDescription(),
                    SpeiErrorCode.SERVICE_API_UNAVAILABLE.getInstance()
                            .addArg(new ErrorArgument(ErrorArgument.REASON, e.getMessage()))
                            .addArg(new ErrorArgument(ErrorArgument.ROOT_CAUSE, e.getClass().getName()))
            );
        }
        // si la peticion no fue exitosa
        if (!response.isSuccessful()) {
            handleNotSuccessfulResponse(response);
        }
        // si la peticion fue sastisfactoria, regresamos el objeto(json) esperado
        return response.body();


    }


    /**
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: Genera una excepcion cuando el consumo no fue exitoso
     * @Date: 05/08/23
     * @param response
     */
    @SuppressWarnings("unchecked")
    private <T> void handleNotSuccessfulResponse(Response<T> response) {
        //try to resolve the error code from the server and fallback to the provided error code
        HashMap<String,Object> ErrorCodeAndErrorMessage = getErrorCode2(response, SpeiErrorCode.class);

        log.info("getMessage() "+ ErrorMessageResponse.class.cast(ErrorCodeAndErrorMessage.get("errorMessageResponse")).getMessage());
        log.info("ErrorCode.ErrorCodeInstance "+ ErrorCode.ErrorCodeInstance.class.cast(ErrorCodeAndErrorMessage.get("errorCodeInstance")));
        final ErrorCode.ErrorCodeInstance errorCodeInstance = Optional
                .ofNullable(ErrorCode.ErrorCodeInstance.class.cast(ErrorCodeAndErrorMessage.get("errorCodeInstance")))
                .orElse(GenericErrorCode.GENERIC_ERROR.getInstance()
                        .addArg(new ErrorArgument(ErrorArgument.REASON, String.format("code:%d %s", response.code(), response.message()))));

        final String message = Optional
                .ofNullable(ErrorMessageResponse.class.cast(ErrorCodeAndErrorMessage.get("errorMessageResponse")).getMessage())
                .orElse("----");

        final Class<AbstractCoreException> exceptionToThrow = AbstractCoreException.resolveException(response.code());
        throwException(errorCodeInstance, exceptionToThrow,message);
    }


    private ErrorCode.ErrorCodeInstance getErrorCode(Response<?> response, Type type) {
        return Optional.ofNullable(response.errorBody()).map((errorBody) -> {
            try {

                final ErrorMessageResponse errorMessageResponse = RetrofitBuild.objectMapper.readValue(errorBody.string(), ErrorMessageResponse.class);
                if (errorMessageResponse.getCode() != null) {
                    final ErrorCode.ErrorCodeInstance errorCodeInstance = ExceptionUtils.getError(type, errorMessageResponse.getCode()).getInstance();
                    if (errorMessageResponse.getArgs() != null && !errorMessageResponse.getArgs().isEmpty()) {
                        errorMessageResponse.getArgs().forEach(arg -> errorCodeInstance.getArgs().add(new ErrorArgument(arg.getArg(), arg.getValue())));
                    } else {
                        if (errorMessageResponse.getMessage() != null) {
                            errorCodeInstance.getArgs().add(new ErrorArgument(ErrorArgument.REASON, errorMessageResponse.getMessage()));
                        }
                    }
                    return errorCodeInstance;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }).orElse(null);
    }

    private HashMap<String,Object> getErrorCode2(Response<?> response, Type type) {
        return Optional.ofNullable(response.errorBody()).map((errorBody) -> {
            try {
                HashMap <String,Object> resultado = new HashMap<>();
                final ErrorMessageResponse errorMessageResponse = RetrofitBuild.objectMapper.readValue(errorBody.string(), ErrorMessageResponse.class);
                if(null != errorMessageResponse){
                    resultado.put("errorMessageResponse",errorMessageResponse);
                }
                if (errorMessageResponse.getCode() != null) {
                    final ErrorCode.ErrorCodeInstance errorCodeInstance = ExceptionUtils.getError(type, errorMessageResponse.getCode()).getInstance();
                    if (errorMessageResponse.getArgs() != null && !errorMessageResponse.getArgs().isEmpty()) {
                        errorMessageResponse.getArgs().forEach(arg -> errorCodeInstance.getArgs().add(new ErrorArgument(arg.getArg(), arg.getValue())));
                    } else {
                        if (errorMessageResponse.getMessage() != null) {
                            errorCodeInstance.getArgs().add(new ErrorArgument(ErrorArgument.REASON, errorMessageResponse.getMessage()));
                        }
                    }
                    resultado.put("errorCodeInstance",errorCodeInstance);
                    return resultado;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }).orElse(null);
    }

    private void throwException(ErrorCode.ErrorCodeInstance errorCodeInstance, Class<AbstractCoreException> exceptionToThrow, String message) {
        try {
            throw exceptionToThrow.
                    getConstructor(new Class<?>[]{String.class, ErrorCode.ErrorCodeInstance.class})
                    .newInstance(message, errorCodeInstance);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new ECommInternalServerErrorException(errorCodeInstance.getErrorCode().getDescription(), errorCodeInstance);
        }
    }


}
