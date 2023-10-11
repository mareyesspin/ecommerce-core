package com.techtrove.ecommerce.core.models.response.transactionservice.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpeiStatusResponseDTO  implements Serializable {
    private static final long serialVersionUID = 5316343708359922770L;

    /** channel. */
    @ApiModelProperty(
            notes = "Canal de spei out")
    private Integer channel;

    /** id de spei out. */
    @ApiModelProperty(
            notes = "Id del spei")
    private Long payOrderId;

    /** fecha de operación de spei out. */
    @ApiModelProperty(
            notes = "Fecha de operación del spei")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd",
            without = {
                    JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
            })
    private Date operationDate;

    /** Clave de rastreo de spei. */
    @ApiModelProperty(
            notes = "Monto de spei")
    private String trackingKey;

    /** Clave de rastreo de spei. */
    @ApiModelProperty(
            notes = "Monto de spei")
    private BigDecimal amount;

    /** Tipo de pago de spei. */
    @ApiModelProperty(
            notes = "Tipo de pago de spei")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String paymetTypeKey;

    /** Institución a la que se envia el spei. */
    @ApiModelProperty(
            notes = "Institución a la que se envia el spei")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer targetInstitution;

    @ApiModelProperty(
            notes = "Institución a la que se envia el spei")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer sendingInstitution;

    /** Datos del ordenante. */
    @ApiModelProperty(
            notes = "Datos del ordenante")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private PayerDTO payer;

    /** Datos del beneficiario. */
    @ApiModelProperty(
            notes = "Datos del beneficiario")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BeneficiaryDTO beneficiary;

    /** Concepto del pago de spei out. */
    @ApiModelProperty(
            notes = "Concepto del pago de spei out")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String paymentConcept;

    /** Referencia numerica. */
    @ApiModelProperty(
            notes = "Referencia numerica")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer referenceNumeric;

    /** Tipo de recepcion
     * AB=Abono(paymet_type_key=1,2,3,4,8,9,30,31),
     * DV=Devolucion(paymet_type_key=0,16,17,18,23,24)
     * CD=CODI(paymet_type_key=19,20,21,22,32,33,34)
     * RM=REMESAS(paymet_type_key=35,36)
     * NM =NOMINA(paymet_type_key=12)
     * PG=pagos(paymet_type_key=15,14). */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(
            notes = "Tipo de recepcion " +
                    "AB=Abono(paymet_type_key=1,2,3,4,8,9,30,31), " +
                    "DV=Devolucion(paymet_type_key=0,16,17,18,23,24) " +
                    "CD=CODI(paymet_type_key=19,20,21,22,32,33,34) " +
                    "RM=REMESAS(paymet_type_key=35,36) " +
                    "NM =NOMINA(paymet_type_key=12) " +
                    "PG=pagos(paymet_type_key=15,14)")
    private String chargeType;

    /** Estatus de la transaction. */
    @ApiModelProperty(
            notes = "Estatus de la transaction")
    private String status;

    /** Ultimo valor de la transición de estatus. */
    @ApiModelProperty(
            notes = "Ultimo valor de la transición de estatus")
    private String statusDescription;


}
