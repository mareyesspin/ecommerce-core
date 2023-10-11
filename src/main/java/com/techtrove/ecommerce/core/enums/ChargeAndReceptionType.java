package com.techtrove.ecommerce.core.enums;

import java.util.stream.Stream;

public enum ChargeAndReceptionType {
    ABONO("AB"),
    DEVOLUCION("DV"),
    CODI("CD"),
    NOMINA("NM"),
    PAGOS("PG"),
    CARGO("CG")
    ;


    private String value;

    /**
     * constructos spei mensaje.
     *
     * @param value the mensaje
     */
    ChargeAndReceptionType(String value) {
        this.value = value;
    }

    /**
     * Retorna el mensaje de la enumeracion.
     *
     * @return String the String
     */
    public String getValue() {
        return this.value;
    }


    /**
     * Retorna el enum por sus propiedades.
     *
     * @return Stream.of(TypePaymentNumber.values())
     */
    public static Stream<ChargeAndReceptionType> stream() {
        return Stream.of(ChargeAndReceptionType.values());
    }
}
