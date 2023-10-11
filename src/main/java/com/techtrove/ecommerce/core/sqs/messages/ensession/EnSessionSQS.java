package com.techtrove.ecommerce.core.sqs.messages.ensession;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnSessionSQS {

    /** ensession. */
    private Boolean ensession;

    /**
     * folio.
     */
    private String folio;

}
