package com.techtrove.ecommerce.core.enums;

public enum ParamsAuditEnum {
    PARAMS_AUDIT("paramAudit"),
    AUDIT_USER("userSpeiCore"),
    AUDIT_CHANNEL_ID("channelID"),
    AUDIT_TRACE_ID("traceID");


    /**
     * Mensaje.
     */
    private String value;

    ParamsAuditEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
