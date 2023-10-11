package com.techtrove.ecommerce.core.models.response.ErrorException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techtrove.ecommerce.core.models.errors.ErrorArgumentResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageResponse implements Serializable {

    private String message;

    private String code;

    private String description;

    @Builder.Default
    private List<ErrorArgumentResponseDTO> args = new ArrayList<>();

    private String status;




    private static final long serialVersionUID = 5318063708359922770L;

}
