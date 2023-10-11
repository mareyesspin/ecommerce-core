package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommResourceNotFoundException extends AbstractCoreException {
    public ECommResourceNotFoundException(String message) {
        super(message);
    }

    public ECommResourceNotFoundException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommResourceNotFoundException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }


}
