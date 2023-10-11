package com.techtrove.ecommerce.core.models.messagequeue;


import com.techtrove.ecommerce.core.models.dto.ParamsAuditDto;
import lombok.*;

import java.util.UUID;




@ToString

@NoArgsConstructor
public class ModelMessageSpeiQueues {
    @Getter
    @Setter
    private UUID keyMessage;
    @Getter
    @Setter
    private String transactionType;
    @Getter
    @Setter
    private String messageType;
    @Getter
    @Setter
    private Object message;
    @Getter
    @Setter
    private ParamsAuditDto paramsAuditDto;


    @Builder
    public ModelMessageSpeiQueues(UUID keyMessage, String transactionType,
                                  String messageType, Object message,
                                  ParamsAuditDto paramsAuditDto) {
        this.keyMessage = keyMessage;
        this.transactionType = transactionType;
        this.messageType = messageType;
        this.message = message;
        this.paramsAuditDto = paramsAuditDto;
    }



}
