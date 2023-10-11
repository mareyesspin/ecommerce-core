package com.techtrove.ecommerce.core.models.request.transactionsService;

import lombok.Data;

@Data
public class BankingInstitution {
    private Long institutionId;
    private String institutionName;
    private String statusReception;
    private String statusInstitution;

}
