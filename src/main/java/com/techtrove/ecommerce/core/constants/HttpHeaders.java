package com.techtrove.ecommerce.core.constants;


import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpHeaders {


    /**auditoria en speiCore**/
    public static final String TRACE_ID = "X-TraceId";
    public static final String USER_SPEI_CORE= "User-Spei-Core";
    public static final String CHANEL_ID_SPEI_CORE= "ChannelId-Spei-Core";
    /**========**/
    public final String X_FORWARDED_FOR = "X-Forwarded-For";

    public final String SPAN_ID = "spanId";

    public final String IP_ADDRESS = "x-ip-address";

    public final String CLIENT_VERSION = "X-CLIENT-VERSION";

    public final String OS_TYPE = "X-OS-TYPE";

    public final String X_LOG_REQUEST_BODY= "x-log-request-body";

    public final String X_LOG_RESPONSE_BODY = "x-log-response-body";


    public final String X_API_KEY = "x-api-key";


}
