package com.techtrove.ecommerce.core.models.advice;


import com.techtrove.ecommerce.core.interfaces.ThrowableConverter;
import com.techtrove.ecommerce.core.enums.GenericErrorCode;
import com.techtrove.ecommerce.core.models.exception.AbstractCoreException;
import com.techtrove.ecommerce.core.models.exception.ECommBadRequestException;
import com.techtrove.ecommerce.core.models.exception.ECommDownstreamException;
import com.techtrove.ecommerce.core.models.response.ErrorException.ErrorMessageResponse;
import com.techtrove.ecommerce.core.utils.ExceptionUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;


/**
 *
 * @Author: Miguel A.R.S.
 * @Email: miguel.reyes@spinbyoxxo.com.mx
 * @Date: 15/05/23
 */
@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice(basePackages = "com.spin.spei")
public class GlobalControllerAdvice {


    @ExceptionHandler({
            Exception.class,
            AbstractCoreException.class
    })
    public ResponseEntity<ErrorMessageResponse> processException(Exception ex) {
        log.info(ExceptionUtils.getSimplifiedStackTrace(ex));
        ErrorMessageResponse errorMessageResponse = ThrowableConverter.INSTANCE.toErrorMessageResponse(ex);
        HttpStatus httpStatus = Optional.ofNullable(
                HttpStatus.resolve(
                        Integer.valueOf(errorMessageResponse.getStatus().substring(0, 3))
                )
        ).orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }


    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
            MaxUploadSizeExceededException.class,
            MissingRequestHeaderException.class,
            MethodArgumentTypeMismatchException.class,
            ValidationException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorMessageResponse> processBadRequest(Exception ex) {
        return createBadRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> processException(MethodArgumentNotValidException ex) {
        final String message = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("|"));
        return createBadRequestResponse(message);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorMessageResponse> processException(BindException ex) {
        final String message = (ex.hasFieldErrors()) ? ex.getFieldError().getDefaultMessage() : ex.getMessage();
        return createBadRequestResponse(message);
    }

    private ResponseEntity<ErrorMessageResponse> createBadRequestResponse(String message) {
        return ResponseEntity.badRequest()
                .body(
                        ThrowableConverter.INSTANCE.toErrorMessageResponse(
                                new ECommBadRequestException(message, GenericErrorCode.BAD_REQUEST.getInstance())
                        )
                );
    }

    @ExceptionHandler(ECommDownstreamException.class)
    public ResponseEntity<ErrorMessageResponse> processException(ECommDownstreamException ex) {
        return new ResponseEntity<>(ex.getErrorMessageResponse(), HttpStatus.valueOf(ex.getCode()));
    }



}
