package com.techtrove.ecommerce.core.models.response.transactionservice;

import com.techtrove.ecommerce.core.models.response.MasterQueryResponse;
import com.techtrove.ecommerce.core.models.response.transactionservice.DTO.PaginateDTO;
import com.techtrove.ecommerce.core.models.response.transactionservice.DTO.SpeiStatusResponseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeiStatusResponse extends MasterQueryResponse {

    /** Content de lista. */
    @ApiModelProperty(
            notes = "Content de lista")
    List<SpeiStatusResponseDTO> transactions;

    /** Valores de paginación. */
    @ApiModelProperty(
            notes = "Valores de paginación")
    private PaginateDTO pageInfo;
}
