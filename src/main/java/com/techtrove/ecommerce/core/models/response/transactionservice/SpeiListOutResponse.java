package com.techtrove.ecommerce.core.models.response.transactionservice;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpeiListOutResponse {

    private List<SpeiOutTrResponse> speiOutTransactionResponse;
}
