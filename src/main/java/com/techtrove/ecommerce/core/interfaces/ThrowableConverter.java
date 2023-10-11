package com.techtrove.ecommerce.core.interfaces;

import com.techtrove.ecommerce.core.models.errors.ErrorArgument;
import com.techtrove.ecommerce.core.enums.GenericErrorCode;
import com.techtrove.ecommerce.core.models.exception.AbstractCoreException;
import com.techtrove.ecommerce.core.models.exception.ECommInternalServerErrorException;
import com.techtrove.ecommerce.core.models.response.ErrorException.ErrorMessageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Mapper(componentModel = "spring")
public interface ThrowableConverter {


    ThrowableConverter INSTANCE = Mappers.getMapper(ThrowableConverter.class);

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: parsea de Throwable a ErrorMessageResponse.
     * @Date: 03/08/23
     * @param throwable
     * @return ErrorMessageResponse
     */
    default ErrorMessageResponse toErrorMessageResponse(Throwable throwable) {
        final AbstractCoreException abstractCoreException = (throwable instanceof AbstractCoreException)
                ? (AbstractCoreException) throwable
                : toInternalServerErrorException(throwable);

        if (abstractCoreException.getErrorCode() == null) {
            abstractCoreException.setErrorCode(GenericErrorCode.GENERIC_ERROR.getInstance());
        }

        return toErrorMessageResponse(abstractCoreException);
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: parsea de Throwable  a SpeiCoreInternalServerErrorException.
     * @Date: 03/08/23
     * @param throwable
     * @return SpeiCoreInternalServerErrorException
     */
    default ECommInternalServerErrorException toInternalServerErrorException(Throwable throwable) {
        return new ECommInternalServerErrorException(throwable.getMessage(), GenericErrorCode.GENERIC_ERROR.getInstance()
                .addArg(new ErrorArgument(ErrorArgument.REASON, throwable.getClass().toString())));
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: parsea de AbstractSpeiCoreException a ErrorMessageResponse.
     * @Date: 03/08/23
     * @param exception
     * @return ErrorMessageResponse
     */
    @Mapping(target = "message", source = "exception.message")
    @Mapping(target = "code", expression = "java(exception.getErrorCode().getErrorCode().name())")
    @Mapping(target = "description", source = "exception.errorCode.errorCode.description")
    @Mapping(target = "args", source = "exception.errorCode.args")
    @Mapping(target = "status", source = "exception", qualifiedByName = "toStatus")
    ErrorMessageResponse toErrorMessageResponse(AbstractCoreException exception);

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: toStatus.
     * @Date: 03/08/23
     * @param throwable
     * @return String
     */
    @Named("toStatus")
    default String toStatus(Throwable throwable) {
        final ResponseStatus responseStatusAnnotation = findMergedAnnotation(throwable.getClass(), ResponseStatus.class);
        final HttpStatus httpStatus =
                Optional.ofNullable(responseStatusAnnotation)
                        .map(ResponseStatus::value)
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        return httpStatus.toString();
    }
}
