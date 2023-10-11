package com.techtrove.ecommerce.core.models.exception;


import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommServiceUnavailableException extends AbstractCoreException {
    public ECommServiceUnavailableException(String message) {
        super(message);
    }

    public ECommServiceUnavailableException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommServiceUnavailableException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

}
