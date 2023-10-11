package com.techtrove.ecommerce.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j

public class DatesConverter {

    public static interface FORMATO {
        String DD_MM_AAAA_Diag = "dd/MM/yyyy";
        String DD_MMM_AAAA_Diag = "dd/MMM/yyyy";
        String DD_MM_AAAA_Guion = "dd-MM-yyyy";
        String DD_MM_AAAA_Guion_Minutos = "dd-MM-yyyy HH:mm:ss";
        String AAAA__MM_DD_Guion_Minutos = "yyyy-MM-dd HH:mm:ss";
        String AAAA_MM_DD_Guion_MinutosSegMilSeg="yyyy-MM-dd HH:mm:ss:SSS";
        String AA__MM_DD_Guion_Minutos = "yy-MM-dd HH:mm:ss";
        String AAAA__DD_MM_Guion_Minutos = "yyyy-dd-MM HH:mm:ss";
        String AAAA_MM_DD_Guion_MinutosSeg = "yyyy-MM-dd mm:ss";
        String DD_MMM_AAAA_Guion = "dd-MMM-yyyy";
        String DD_MMMM_AAAA      = "dd 'de' MMMMM 'del' yyyy";
        String DD_MMMM_AAAACorto = "dd 'de' MMMMM 'del' yyyy";
        String HHmm = "HH:mm";
        String HHmmss = "HH:mm:ss";
        String HHmmssSS = "HH:mm:ss:SS";
        String AAAA_MM_DD_Guion = "yyyy-MM-dd";
        String AAAA_MMM_DD_Guion = "yyyy-MMM-dd";
        String AAAA_MM_DD_Diag = "yyyy/MM/dd";
        String AAAA_MMM_DD_Diag = "yyyy/MMM/dd";
        String AAAA_MM_DD_SinGuion = "yyyyMMdd";
        String AAAA_MM_DD_SinGuionMinunos = "yyyyMMdd HH:mm:ss";
        String AAAA_MM_DD_Diag_Minutos = "yyyy/MM/dd HH:mm:ss";
        String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    }
    public static String TIME_ZONE_DEFAULT="America/Monterrey";





    public static String convierteStr(Date fecha, String formato)
    {

        SimpleDateFormat fechaFormato = new SimpleDateFormat (formato);
        String fechaStr = "";
        /**Validamos si lo que entra es una fecha vacia */

        if (fecha != null) {
            fechaStr = fechaFormato.format(fecha);
        }
        return fechaStr;
    }

    public static Date convierteDate(String fechaStr, String formato) {
        SimpleDateFormat fechaEntrada= new SimpleDateFormat(formato);

        Date fecha = null;

        fechaEntrada.setLenient(false);
        try {
            fecha = fechaEntrada.parse(fechaStr);
        } catch (Exception e) {
            log.error("Error",ExceptionUtils.getSimplifiedStackTrace(e));


        }
        return fecha;
    }

    public static String convierteStr(String fechaStr, String formatoIn, String formatoOut) {
        SimpleDateFormat fechaEntrada= new SimpleDateFormat(formatoIn);
        SimpleDateFormat fechaSalida= new SimpleDateFormat(formatoOut);

        Date fecha = null;
        String strFecha="";

        fechaEntrada.setLenient(false);
        fechaSalida.setLenient(false);

        try {
            fecha = fechaEntrada.parse(fechaStr);
            strFecha=convierteStr(fecha, formatoOut);
        } catch (Exception e) {
            log.error("Error",ExceptionUtils.getSimplifiedStackTrace(e));

        }
        return strFecha;
    }


    public static String getFechaServidorStr( String formato) {
        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE_DEFAULT);
        TimeZone.setDefault(tz);
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat(formato);
        return formateador.format(ahora);
    }

    public static Date getFechaServidorDate( String formato) {
        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE_DEFAULT);
        TimeZone.setDefault(tz);
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat(formato);
        return convierteDate(formateador.format(ahora),formato);
    }
    public static LocalDateTime getFechaServidorLocalDateTime() {
        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE_DEFAULT);
        TimeZone.setDefault(tz);
        LocalDateTime ahora = LocalDateTime.now();
        return ahora;
    }
    public static LocalDateTime toLocalDateTime(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO.AAAA__MM_DD_Guion_Minutos);
        LocalDateTime formatDateTime = LocalDateTime.parse(fecha, formatter);
        return  formatDateTime;
    }

    public static String localDateTimeToString(LocalDateTime fecha,String formato){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return  fecha.format(formatter);
    }

    public static boolean esFechaValida(String fecha, String formato) {
        SimpleDateFormat fechaFormato = new SimpleDateFormat(formato);
        fechaFormato.setLenient(false);

        try {
            fechaFormato.parse(fecha.trim());
        } catch (Exception pe) {
            return false;
        }

        return true;
    }

    public final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

    public String formatCalendarToYYYYMMDD(Calendar date){

        String formatted = format1.format(date.getTime());

        return formatted;
    }
}

