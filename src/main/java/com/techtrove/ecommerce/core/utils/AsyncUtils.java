package com.techtrove.ecommerce.core.utils;


import com.techtrove.ecommerce.core.constants.HttpHeaders;
import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

import java.util.function.Supplier;

@UtilityClass
public class AsyncUtils {

    public void runVoidAsyncWithTrace(Runnable runnable, String traceId) {
        MDC.put(HttpHeaders.TRACE_ID, traceId);
        runnable.run();
        MDC.remove(HttpHeaders.TRACE_ID);
    }

    public <U> U runAsyncWithTrace(Supplier<U> supplier, String traceId) {
        MDC.put(HttpHeaders.TRACE_ID, traceId);
        U result = supplier.get();
        MDC.remove(HttpHeaders.TRACE_ID);
        return result;
    }

    public <U> Supplier<U> supplierAsyncWithTrace(Supplier<U> supplier, String traceId) {
        return () -> {
            MDC.put(HttpHeaders.TRACE_ID, traceId);
            U result = supplier.get();
            MDC.remove(HttpHeaders.TRACE_ID);
            return result;
        };
    }
}
