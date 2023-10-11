package com.techtrove.ecommerce.core.enums;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum GenericErrorCode implements ErrorCode {
    BAD_REQUEST("Request is not valid"),
    GENERIC_ERROR("Generic error");


    private final String description;

    /**
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: NA.
     * @Date: 06/08/23
     * @param errorCode
     * @return Optional<ErrorCode>
     */
    static Optional<ErrorCode> toErrorCode(String errorCode) {
        try {
            return Optional.of(valueOf(errorCode));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
