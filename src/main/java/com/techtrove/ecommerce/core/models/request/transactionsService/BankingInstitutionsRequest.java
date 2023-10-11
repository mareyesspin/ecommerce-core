package com.techtrove.ecommerce.core.models.request.transactionsService;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BankingInstitutionsRequest {

    @Setter
    @Getter
    private String fechaOperativa;
    @Setter
    @Getter
    private List<BankingInstitution> bankingInstitutions;

}
