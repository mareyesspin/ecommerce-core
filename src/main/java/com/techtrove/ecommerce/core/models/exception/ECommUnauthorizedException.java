package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommUnauthorizedException extends AbstractCoreException {

    public ECommUnauthorizedException(String message) {
        super(message);
    }

    public ECommUnauthorizedException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

    public ECommUnauthorizedException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }


}
