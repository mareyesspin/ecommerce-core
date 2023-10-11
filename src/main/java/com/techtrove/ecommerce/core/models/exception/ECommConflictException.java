package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ECommConflictException extends AbstractCoreException {

    public ECommConflictException() {
    }

    public ECommConflictException(String message) {
        super(message);
    }

    public ECommConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ECommConflictException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

    public ECommConflictException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

}
