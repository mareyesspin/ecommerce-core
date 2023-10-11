package com.techtrove.ecommerce.core.models.request.transactionsService;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;


import javax.validation.constraints.*;
import java.math.BigDecimal;

@JsonPropertyOrder({

        "chargeType",
        "targetInstitution",
        "amount",
        "typePaymentNumber",
        "trackingKey",
        "payerName",
        "personType",
        "payerAccountType",
        "payerAccount",
        "payerAccountLevel",
        "payerRfcCurp",
        "beneficiaryName",
        "beneficiaryAccountType",
        "beneficiaryAccount",
        "beneficiaryRfcCurp",
        "paymentConcept",
        "referenceNumeric",
        "repaymentCauseNumber",
        "originalOperationDate",
        "originalTrackingKey",
        "interestAmount",
        "originalAmount",
        "typeBeneficiaryReceiver",
        "signature",
        "channel",
        "status"
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpeiOutTrRequest {

    private String chargeType;
    private Integer targetInstitution;
    private BigDecimal amount;

    private Integer typePaymentNumber;

    private String trackingKey;
    private String payerName;

    private String personType;
    private Integer payerAccountType;
    private String payerAccount;
    private String payerAccountLevel;
    private String payerRfcCurp;
    private String beneficiaryName;
    private Integer beneficiaryAccountType;
    private String beneficiaryAccount;
    private String beneficiaryRfcCurp;
    private String paymentConcept;
    private Integer referenceNumeric;
    private Integer repaymentCauseNumber;
    private String originalOperationDate;
    private String originalTrackingKey;
    private BigDecimal interestAmount;
    private BigDecimal originalAmount;
    private Integer typeBeneficiaryReceiver;
    private String signature;
    private Integer channel;
    private String status;
    private String reasonCancel;
    private String captureDate;

}
