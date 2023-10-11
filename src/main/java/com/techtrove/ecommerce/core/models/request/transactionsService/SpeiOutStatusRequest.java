package com.techtrove.ecommerce.core.models.request.transactionsService;

import lombok.Data;

import java.util.Date;

@Data
public class SpeiOutStatusRequest {
    private String status;
    private String statusDescripcion;

    /**
     * Obligatorio para cuando se cambia el estatus a cancelado
     */
    private String reasonCancel;

    /**
     * Obligatorio cuando se cambia el estatus a enviado
     */
    private Date operationDate;

}
