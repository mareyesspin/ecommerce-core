package com.techtrove.ecommerce.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techtrove.ecommerce.core.enums.ParamsAuditEnum;
import com.techtrove.ecommerce.core.models.dto.ParamsAuditDto;
import org.slf4j.MDC;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Optional;

public class ParamsAuditUtils {

    public static  <T> void addObjectToMdc(String key,T object){
        try{
            MDC.put(key, RetrofitBuild.objectMapper.writeValueAsString(object));
        }catch (JsonProcessingException e) {
            System.out.println("ERROR AL ASIGNAR VALOR A MDC.put");
        }
    }

    public static void addParamsAuditToMdc( ParamsAuditDto paramsAuditDto){
        try{
            MDC.put(ParamsAuditEnum.PARAMS_AUDIT.getValue(),
                    RetrofitBuild.objectMapper.writeValueAsString(paramsAuditDto));
        }catch (JsonProcessingException e) {
            System.out.println("ERROR AL ASIGNAR VALOR A MDC.put");
        }
    }
    public static ParamsAuditDto getParamsAuditFromMdc(){
        ParamsAuditDto paramsAuditDto ;
        try {
            /*String paramsStr=Optional
                    .ofNullable(MDC.get(ParamsAuditUtils.KEY_PARAMAUDIT))
                    .orElse("{\"traceID\":\"\",\"userSpeiCore\":\"\",\"channelID\":\"\"}");
            System.out.println("PARAMETROS DE AUDITORIA: " + paramsStr);

             */
            paramsAuditDto  = RetrofitBuild.objectMapper.readValue(Optional
                    .ofNullable(MDC.get(ParamsAuditEnum.PARAMS_AUDIT.getValue()))
                    .orElse("{\"traceID\":\"\",\"userSpeiCore\":\"\",\"channelID\":\"\"}"),ParamsAuditDto.class);
        } catch (JsonProcessingException e) {
            System.out.println("NO SE PUDO OBTENER LOS PARAMETROS DE AUDITORIA DE MDC");
            paramsAuditDto = new ParamsAuditDto();
        }
        return paramsAuditDto;
    }

    public static String getParamAuditFromMdc(ParamsAuditEnum paramsAuditEnum){
        ParamsAuditDto paramsAuditDto ;
        try {
            paramsAuditDto  = RetrofitBuild.objectMapper.readValue(Optional
                    .ofNullable(MDC.get(ParamsAuditEnum.PARAMS_AUDIT.getValue()))
                    .orElse("{\"traceID\":\"\",\"userSpeiCore\":\"\",\"channelID\":\"\"}"),ParamsAuditDto.class);
        } catch (JsonProcessingException e) {
            System.out.println("NO SE PUDO OBTENER LOS PARAMETROS DE AUDITORIA DE MDC");
            paramsAuditDto = new ParamsAuditDto();
        }

        return getField(paramsAuditDto,paramsAuditEnum.getValue());
    }

    private static String getField(Object target, String name) {
        String resultado="";
        Assert.notNull(target, "Target object must not be null");
        Field field = org.springframework.util.ReflectionUtils.findField(target.getClass(), name);
        if (field == null) {
            throw new IllegalStateException("Could not find field [" + name + "] on target [" + target + "]");
        }
        org.springframework.util.ReflectionUtils.makeAccessible(field);
        resultado = String.class.cast(org.springframework.util.ReflectionUtils.getField(field, target));
        return resultado;
    }
}
