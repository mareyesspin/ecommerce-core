package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommNotAcceptableException extends AbstractCoreException {

    public ECommNotAcceptableException(String message) {
        super(message);
    }

    public ECommNotAcceptableException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommNotAcceptableException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

}
