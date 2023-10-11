package com.techtrove.ecommerce.core.models.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@AllArgsConstructor
public class ErrorArgument implements Serializable {

    private static final long serialVersionUID = 13456788734L;

    public static final String REASON = "REASON";
    public static final String ROOT_CAUSE = "CAUSE";
    private String arg;

    private String value;
}
