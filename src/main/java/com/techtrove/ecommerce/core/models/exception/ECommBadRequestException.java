package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommBadRequestException extends AbstractCoreException {

    public ECommBadRequestException(String message) {
        super(message);
    }

    public ECommBadRequestException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommBadRequestException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

}
