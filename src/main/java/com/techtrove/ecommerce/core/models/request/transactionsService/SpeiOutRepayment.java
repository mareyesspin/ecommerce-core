package com.techtrove.ecommerce.core.models.request.transactionsService;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpeiOutRepayment {

    private Integer repaymentCauseNumber;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="DD/MM/yyyy")
    private Date originalOperationDate;

    private String originalTrackingKey;

    private BigDecimal interestAmount;

    private BigDecimal originalAmount;

    private Integer typeBeneficiaryReceiver;
}
