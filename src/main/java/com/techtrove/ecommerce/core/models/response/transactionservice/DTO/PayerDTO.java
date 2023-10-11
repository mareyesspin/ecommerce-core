package com.techtrove.ecommerce.core.models.response.transactionservice.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayerDTO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8269216775575885779L;

    /** Nombre del ordenante, persona que envia la recepcion desde la institucion contraparte. */
    @ApiModelProperty(
            notes = "Nombre del ordenante, persona que envia la recepcion desde la institucion contraparte")
    private String payerName;

    /** Nombre del ordenante, persona que envia la recepcion desde la institucion contraparte. */
    @ApiModelProperty(
            notes = "Tipo de persona ordenante, FISICA O MORAL")
    private String personType;

    /** Tipo de cuenta del ordenante, 40=CuentaClabe, 10=celular 03=TARJETA debito. */
    @ApiModelProperty(
            notes = "Tipo de cuenta del ordenante, 40=CuentaClabe, 10=celular 03=TARJETA debito")
    private Integer payerAccountType;

    /** Cuenta del ordenante. */
    @ApiModelProperty(
            notes = "Cuenta del ordenante")
    private String payerAccount;

    /** Nivel de cuenta del ordenante. */
    @ApiModelProperty(
            notes = "Nivel de cuenta del ordenante")
    private String payerAccountLevel;

    /** RFC o CURP del ordenante. */
    @ApiModelProperty(
            notes = "RFC o CURP del ordenante")
    private String payerRfcCurp;
}
