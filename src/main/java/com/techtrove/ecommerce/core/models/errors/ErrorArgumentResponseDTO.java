package com.techtrove.ecommerce.core.models.errors;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorArgumentResponseDTO {

    private String arg;

    private String value;

}
