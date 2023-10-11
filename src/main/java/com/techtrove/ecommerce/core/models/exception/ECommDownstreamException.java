package com.techtrove.ecommerce.core.models.exception;

import com.techtrove.ecommerce.core.models.response.ErrorException.ErrorMessageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ECommDownstreamException extends RuntimeException {

    private ErrorMessageResponse errorMessageResponse;

    private int code;

}