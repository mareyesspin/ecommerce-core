package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommInternalServerErrorException extends AbstractCoreException {

    public ECommInternalServerErrorException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommInternalServerErrorException(String message) {
        super(message);
    }

    public ECommInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ECommInternalServerErrorException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

    public ECommInternalServerErrorException(Throwable cause) {
        super(cause);
    }

}
