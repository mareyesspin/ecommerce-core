package com.techtrove.ecommerce.core.models.response.transactionservice;

import com.techtrove.ecommerce.core.models.response.MasterTransactionalResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SpeiOutStatusResponse extends MasterTransactionalResponse {
    private Long payOrderID;

}