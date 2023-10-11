package com.techtrove.ecommerce.core.models.response.transactionservice.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryDTO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8269216775575845779L;

    /** Nombre del beneficiario. */
    @ApiModelProperty(
            notes = "Nombre del beneficiario")
    private String beneficiaryName;

    /** Tipo de cuenta del beneficiario 40=CuentaClabe, 10=celular 03=TARJETA debito. */
    @ApiModelProperty(
            notes = "Tipo de cuenta del beneficiario 40=CuentaClabe, 10=celular 03=TARJETA debito")
    private Integer beneficiaryAccountType;

    /** Numero de cuenta del beneficiario. */
    @ApiModelProperty(
            notes = "Numero de cuenta del beneficiario")
    private String beneficiaryAccount;

    /** RFC o CURP del beneficiario. */
    @ApiModelProperty(
            notes = "RFC o CURP del beneficiario")
    private String beneficiaryRfcCurp;
}
