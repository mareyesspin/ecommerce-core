package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommResourceLockedException extends AbstractCoreException {

    public ECommResourceLockedException(String message) {
        super(message);
    }

    public ECommResourceLockedException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommResourceLockedException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

}
