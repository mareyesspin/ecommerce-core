package com.techtrove.ecommerce.core.filters;

import com.techtrove.ecommerce.core.constants.General;
import com.techtrove.ecommerce.core.constants.HttpHeaders;
import com.techtrove.ecommerce.core.models.dto.ParamsAuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class HandlerFilter {



    private  final Tracer tracer;

    @Autowired
    public HandlerFilter(Tracer tracer) {
        this.tracer = tracer;
    }


    public ParamsAuditDto estableceParametrosAuditoria(HttpServletRequest request){
        Optional<Span> currentSpan = Optional.ofNullable(tracer.currentSpan());
        ParamsAuditDto paramsAuditDto = new ParamsAuditDto();
         String traceIdHeader = request.getHeader(HttpHeaders.TRACE_ID);
        String userSpeiCore = request.getHeader(HttpHeaders.USER_SPEI_CORE);
        String channelID = request.getHeader(HttpHeaders.CHANEL_ID_SPEI_CORE);
        String traceId = "";
        if (traceIdHeader != null) {
            traceId = traceIdHeader;
        } else if (currentSpan.isPresent()) {
            traceId = currentSpan.get().context().traceId();
        }
        //Establecemos los parametros de auditoria
        paramsAuditDto.setUserSpeiCore(null != userSpeiCore && !userSpeiCore.isEmpty()?userSpeiCore: General.CADENA_VACIA);
        paramsAuditDto.setTraceID(traceId);
        paramsAuditDto.setChannelID(null != channelID && !channelID.isEmpty()? channelID:General.CADENA_VACIA);

        return paramsAuditDto;
    }

}
