package com.techtrove.ecommerce.core.filters;

import com.techtrove.ecommerce.core.constants.HttpHeaders;
import com.techtrove.ecommerce.core.models.dto.ParamsAuditDto;
import com.techtrove.ecommerce.core.utils.ParamsAuditUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class EcommerceCoreParamAuditFilter extends OncePerRequestFilter {


    @Autowired
    protected HandlerFilter handlerFilter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        ParamsAuditDto paramsAuditDto  = handlerFilter.estableceParametrosAuditoria(request);
        setCommonResponseData(response, paramsAuditDto.getTraceID());
        ParamsAuditUtils.addParamsAuditToMdc(paramsAuditDto);
        filterChain.doFilter(request, response);
    }

    private void setCommonResponseData(HttpServletResponse response, String traceId) {
        response.setHeader(HttpHeaders.TRACE_ID, traceId);
        MDC.put(HttpHeaders.TRACE_ID, traceId);
    }

}

