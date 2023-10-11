package com.techtrove.ecommerce.core.models.request.sqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpeiInQueueDto {
    private Long payOrderId;
    private String receptionDate;
    private String operationDate;
    private String receptionType;
    private Long numberOperationEkarpay;
    private Integer sendingInstitution;
    private BigDecimal amount;
    private Integer paymetTypeKey;
    private String trackingKey;
    private String payerName;
    private Integer payerAccountType;
    private String payerAccount;
    private String payerRfcCurp;
    private String beneficiaryName;
    private Integer beneficiaryAccountType;
    private String beneficiaryAccount;
    private String beneficiaryRfcCurp;
    private String paymentConcept;
    private Integer referenceNumeric;
    private String transactionSignature;
    private Integer repaymentCauseNumber;
    private String reasonDevolution;
    private String originalOperationDate;
    private String originalTrackingKey;
    private BigDecimal interestAmount;
    private BigDecimal originalAmount;
    private String reasonCancel;

}
