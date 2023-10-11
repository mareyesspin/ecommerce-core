package com.techtrove.ecommerce.core.models.request.transactionsService;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpeiListOutRequest {
    private List<SpeiOutTrRequest> speiOutTrRequest;
}
