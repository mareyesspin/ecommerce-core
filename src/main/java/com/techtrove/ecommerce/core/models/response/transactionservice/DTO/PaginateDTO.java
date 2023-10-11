package com.techtrove.ecommerce.core.models.response.transactionservice.DTO;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginateDTO {

    private int size;

    private int totalPages;

    private int currentPage;
}
