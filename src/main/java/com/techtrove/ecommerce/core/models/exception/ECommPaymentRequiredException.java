package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ECommPaymentRequiredException extends AbstractCoreException {

    public ECommPaymentRequiredException(String message) {
        super(message);
    }

    public ECommPaymentRequiredException(ErrorCode.ErrorCodeInstance errorCode) {
        super(errorCode);
    }

    public ECommPaymentRequiredException(String message, ErrorCode.ErrorCodeInstance errorCode) {
        super(message, errorCode);
    }

}
