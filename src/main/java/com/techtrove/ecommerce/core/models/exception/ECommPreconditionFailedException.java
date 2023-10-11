package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommPreconditionFailedException extends AbstractCoreException {

    public ECommPreconditionFailedException() {
    }

    public ECommPreconditionFailedException(String message) {
        super(message);
    }

    public ECommPreconditionFailedException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommPreconditionFailedException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

    public ECommPreconditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
