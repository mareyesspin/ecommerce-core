package com.techtrove.ecommerce.core.constants;

import software.amazon.ion.Decimal;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class General {

    public static final String CADENA_VACIA = "";
    public static final String SIN_VALOR = "N/A";
    public static final String ESPACIO_BLANCO = " ";
    public static final Decimal DECIMAL_VACIO = Decimal.valueOf( 0.00);
    public static final Double DOUBLE_VACIO = 0.00;
    public static final Calendar FECHA_VACIA = new GregorianCalendar(1900,00,01) ;
    public static final Integer ENTERO_CERO = 0;
    public final static String[] STR_SUCESS_CODE_PRAXIS         ={"0"," Procesamiento Exitoso."};

}
