package com.techtrove.ecommerce.core.interceptors;

import com.techtrove.ecommerce.core.constants.HttpHeaders;
import com.techtrove.ecommerce.core.models.dto.ParamsAuditDto;
import com.techtrove.ecommerce.core.utils.ParamsAuditUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class ParamsAudInterceptor implements Interceptor {




    public ParamsAudInterceptor() {
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        ParamsAuditDto paramsAuditDto  = ParamsAuditUtils.getParamsAuditFromMdc();
        return chain.proceed(chain.request()
                .newBuilder()
                .addHeader(HttpHeaders.TRACE_ID, paramsAuditDto.getTraceID())
                .addHeader(HttpHeaders.USER_SPEI_CORE,paramsAuditDto.getUserSpeiCore())
                .addHeader(HttpHeaders.CHANEL_ID_SPEI_CORE,paramsAuditDto.getChannelID())
                .build());
    }

}
