package com.techtrove.ecommerce.core.models.messagequeue;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeiOutMessage {

    private Long payOrderId;

    private String operationDate;

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

    private Double interestAmount;

    private Double originalAmount;

    private Integer typeBeneficiaryReceiver;
    private String signature;

    private Integer channel;
}
