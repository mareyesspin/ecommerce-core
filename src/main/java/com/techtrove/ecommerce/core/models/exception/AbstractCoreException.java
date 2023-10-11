package com.techtrove.ecommerce.core.models.exception;


import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class AbstractCoreException extends RuntimeException{

    @SuppressWarnings("rawtypes")
    private static Map<Integer, Class> exceptionsMapping = new HashMap<>();
    private ErrorCode.ErrorCodeInstance errorCode;

    public AbstractCoreException() {
    }

    public AbstractCoreException(String message) {
        super(message);
    }

    public AbstractCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractCoreException(Throwable cause) {
        super(cause);
    }

    public AbstractCoreException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode.getErrorCode().getDescription());
        this.errorCode = errorCode;
    }

    public AbstractCoreException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message);
        this.errorCode = errorCode;
    }



    static {
        exceptionsMapping.put(HttpStatus.BAD_REQUEST.value(), ECommBadRequestException.class);
        exceptionsMapping.put(HttpStatus.UNPROCESSABLE_ENTITY.value(), ECommBadRequestException.class);
        exceptionsMapping.put(HttpStatus.NOT_FOUND.value(), ECommResourceNotFoundException.class);
        exceptionsMapping.put(HttpStatus.CONFLICT.value(), ECommConflictException.class);
        exceptionsMapping.put(HttpStatus.SERVICE_UNAVAILABLE.value(), ECommServiceUnavailableException.class);
        exceptionsMapping.put(HttpStatus.INTERNAL_SERVER_ERROR.value(), ECommInternalServerErrorException.class);
        exceptionsMapping.put(HttpStatus.PRECONDITION_FAILED.value(), ECommPreconditionFailedException.class);
        exceptionsMapping.put(HttpStatus.LOCKED.value(), ECommResourceLockedException.class);
        exceptionsMapping.put(HttpStatus.UNAUTHORIZED.value(), ECommUnauthorizedException.class);
        exceptionsMapping.put(HttpStatus.PAYMENT_REQUIRED.value(), ECommPaymentRequiredException.class);
        exceptionsMapping.put(HttpStatus.FORBIDDEN.value(), ECommForbiddenException.class);
        exceptionsMapping.put(HttpStatus.NOT_ACCEPTABLE.value(), ECommNotAcceptableException.class);
        exceptionsMapping.put(HttpStatus.METHOD_NOT_ALLOWED.value(), ECommMethodNotAllowedException.class);

    }

    public ErrorCode.ErrorCodeInstance getErrorCodeInstance() {
        return this.errorCode;
    }

    @SuppressWarnings("rawtypes")
    public static Class resolveException(int httpErrorCode) {
        return Optional.ofNullable(exceptionsMapping.get(httpErrorCode)).orElse(ECommServiceUnavailableException.class);
    }

}
