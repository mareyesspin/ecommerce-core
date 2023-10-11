package com.techtrove.ecommerce.core.models.request.transactionsService;


import lombok.*;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 *
 * @Author: Miguel A.R.S.
 * @Email: miguel.reyes@spinbyoxxo.com.mx
 * @Description: Model utilzado para recibir los parametros de entrada del end point /spei-in
 *               del service spei-transaction-service
 * @Date: 15/06/23
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpeiInTrRequest {

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
    private Long originalPackageFolio;
    private Long originalOrderFolio;


}
