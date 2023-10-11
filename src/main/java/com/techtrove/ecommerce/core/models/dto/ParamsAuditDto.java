package com.techtrove.ecommerce.core.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Component
public class ParamsAuditDto {

    @Setter
    @Getter
    private String traceID;
    @Setter
    @Getter
    private String userSpeiCore;
    @Setter
    @Getter
    private String channelID;
}
