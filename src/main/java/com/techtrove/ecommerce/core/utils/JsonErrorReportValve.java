package com.techtrove.ecommerce.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.Constants;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.coyote.ActionCode;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.res.StringManager;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @Author: Miguel A.R.S.
 * @Email: miguel.reyes@spinbyoxxo.com.mx
 * @Description: Esta clase nos servira para sustituir  el mensage default que arroja el  tomcat
 *               cuando se consume un recurso que no existe
 * @Date: 12/06/23
 */
@Slf4j
public class JsonErrorReportValve extends ErrorReportValve {

    public JsonErrorReportValve() {
        super();
    }

    @Override
    protected void report(Request request, Response response, Throwable throwable) {

        int statusCode = response.getStatus();
        String message = "Error.....";

        if (statusCode < 400 || response.getContentWritten() > 0 || !response.setErrorReported()) {
            return;
        }


        AtomicBoolean result = new AtomicBoolean(false);
        response.getCoyoteResponse().action(ActionCode.IS_IO_ALLOWED, result);
        if (!result.get()) {
            return;
        }

        StringManager smClient = StringManager.getManager(Constants.Package, request.getLocales());
        response.setLocale(smClient.getLocale());
        String type = null;
        if (throwable != null) {
            type = smClient.getString("errorReportValve.exceptionReport");
        } else {
            type = smClient.getString("errorReportValve.statusReport");
        }
        message = response.getMessage();
        if (message == null && throwable != null) {
            message = throwable.getMessage();
        }
        String description = null;
        description = smClient.getString("http." + statusCode + ".desc");
        if (description == null) {
            if (message == null || message.isEmpty()) {
                return;
            } else {
                description = smClient.getString("errorReportValve.noDescription");
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("responseCodeApplication", "9998");
        params.put("message", message==null?description:message);
        params.put("error", description);
        String jsonReport = null;
        try {
            jsonReport = new ObjectMapper().writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                log.debug("Failure to set the content-type of response", t);
            }
            Writer writer = response.getReporter();
            if (writer != null) {
                writer.write(jsonReport);
                response.finishResponse();
                return;
            }
        } catch (IOException | IllegalStateException e) {
            // Ignore
            e.printStackTrace();
            log.error(String.valueOf(e));
        }
    }

}
