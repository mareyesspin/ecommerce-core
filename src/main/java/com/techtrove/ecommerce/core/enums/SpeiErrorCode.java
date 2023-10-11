package com.techtrove.ecommerce.core.enums;

import com.techtrove.ecommerce.core.interfaces.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum SpeiErrorCode implements ErrorCode {

    CIRCUIT_BREAKER_SERVICE_API_UNAVAILABLE("Service unavailable - Circuit Breaker"),
    SERVICE_API_UNAVAILABLE("Service unavailable"),
    INVALID_EXECUTION_SIGNATURE("Invalid execution signature"),
    INCORRECT_FORMAT_DATE("Wrong format date, expected[YYYY-mm-dd]"),
    WRONG_OPERATION_DATE("Wrong operation date"),
    WRONG_TYPE_PAYMENT("Wrong type payment"),
    WRONG_PERSON_TYPE("Wrong person type"),
    WRONG_PAYER_ACCOUNT_TYPE("Wrong payer account type"),
    WRONG_PAYER_NAME("Wrong payer name"),
    WRONG_PAYER_ACCOUNT("Wrong payer account"),
    WRONG_PAYER_RFC_CURP("Wrong payer RFC/CURP"),
    WRONG_BENEFICIARY_NAME("Wrong beneficiary name"),
    WRONG_PAYER_ACCOUNT_LEVEL("Wrong payer account level"),
    WRONG_BENEFICIARY_ACCOUNT_TYPE("Wrong beneficiary account type"),
    WRONG_BENEFICIARY_ACCOUNT("Wrong beneficiary account"),
    WRONG_BENEFICIARY_RFC_CURP("Wrong beneficiary RFC/CURP"),
    WRONG_PAYMENT_CONCEPT("Wrong payment concept"),
    WRONG_REFERENCE_NUMERIC("Wrong reference numeric"),
    WRONG_CHANNEL("Wrong channel"),
    INCORRECT_SIGNATURE("Incorrect signature"),
    WRONG_BANKING_INSTITUTION("Wrong banking institution"),
    WRONG_TRAKINGKEY("Wrong trakingKey."),
    WRONG_CHARGE_TYPE("Wrong Charge Type."),
    OPERATION_NOT_ALLOWED("OPERATION NOT ALLOWED."),
    WRONG_CAPTURE_DATE("Wrong Capture Date.");




    private static final long serialVersionUID = 6529685098267757560L;

    private final String description;

    /**
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: toErrorCode.
     * @Date: 06/08/23
     * @param errorCode
     * @return Optional<SpeiErrorCode>
     */
    public static Optional<SpeiErrorCode> toErrorCode(String errorCode) {
        try {
            return Optional.of(valueOf(errorCode));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
