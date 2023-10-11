package com.techtrove.ecommerce.core.models.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaskedLogging {
    int initPosition() default 0;
    String defaultMask() default "*";
}
