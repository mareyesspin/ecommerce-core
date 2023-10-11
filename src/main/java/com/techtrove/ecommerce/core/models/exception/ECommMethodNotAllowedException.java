package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommMethodNotAllowedException extends AbstractCoreException {
    public ECommMethodNotAllowedException(String message) {
        super(message);
    }

    public ECommMethodNotAllowedException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommMethodNotAllowedException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

}
