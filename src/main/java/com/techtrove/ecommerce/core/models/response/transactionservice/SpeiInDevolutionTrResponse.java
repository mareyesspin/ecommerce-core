package com.techtrove.ecommerce.core.models.response.transactionservice;

import com.techtrove.ecommerce.core.models.response.MasterTransactionalResponse;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpeiInDevolutionTrResponse extends MasterTransactionalResponse {
    private Long orderNumber;

}
