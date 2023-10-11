package com.techtrove.ecommerce.core.models.exception;


import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommForbiddenException extends AbstractCoreException {

    public ECommForbiddenException(String message) {
        super(message);
    }

    public ECommForbiddenException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommForbiddenException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

}
