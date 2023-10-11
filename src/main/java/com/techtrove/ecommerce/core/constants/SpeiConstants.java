package com.techtrove.ecommerce.core.constants;

import com.techtrove.ecommerce.core.enums.ChargeAndReceptionType;


public class SpeiConstants {




    public static final String SPEI_QUEUE_TYPE_IN = "RECEPCION";
    public static final String SPEI_QUEUE_TYPE_OUT = "ENVIO";

    public static interface CAUSA_DEVOLUCION{
        final static Integer CUENTA_INEXISTENTE = 1;
        final static Integer CUENTA_BLOQUEDA = 2;
        final static Integer CUENTA_CANCELADA = 3;
        final static Integer CUENTA_EN_OTRA_DIVISA = 5;
        final static Integer FALTA_INFORMACION_MANDATORIA = 14;
        final static Integer TIPO_PAGO_ERRONEO = 15;
        final static Integer TIPO_OPERACION_ERRONEA = 16;
        final static Integer TIPO_CUENTA_NO_CORRESPONDE = 17;
        final static Integer CARACTER_INVALIDO = 19;
        final static Integer EXCEDE_LIMITE_SALDO_CUENTA = 20;
        final static Integer EXCEDE_LIMITE_ABONOS_MES_CUENTA = 21;
        final static Integer NUMERO_TELEFONIA_NO_REGISTRADO = 22;
        final static Integer CUENTA_ADICIONAL_NO_RECIBE_PAGOS_SOLO_DE_BANXICO = 23;
    }



    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: Cuando se reciba una recepcion se considera un abono si el
     *                tipo de pago es 1,2,3,4,5,6,7,8,9,10,11,30,31
     * @Date: 19/06/23
     * @param tipoPago
     * @return Boolean
     */


}
