package com.techtrove.ecommerce.core.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;


public class MasterTransactionalResponse implements Serializable {

    @JsonProperty
    @Setter
    @Getter
    private HashMap<String,Object> transactionDetails;
    private static final long serialVersionUID = 5318063708359922770L;


}
