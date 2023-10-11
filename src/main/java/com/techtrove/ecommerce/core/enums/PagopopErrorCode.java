package com.techtrove.ecommerce.core.enums;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum PagopopErrorCode implements ErrorCode {

    BLOCKED_ACCOUNT("The account is blocked."),
    CANCELED_ACCOUNT("The account is canceled."),
    ACCOUNT_NOTFOUND("The account does not exist."),
    ACCOUNT_LIMITS_EXCEEDED("account limits  were exceeded");


    private final String description;

    private static final long serialVersionUID = 6529685098267757L;
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
