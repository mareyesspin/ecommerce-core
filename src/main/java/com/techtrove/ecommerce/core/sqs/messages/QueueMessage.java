package com.techtrove.ecommerce.core.sqs.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.function.BiConsumer;

@Getter
@Setter
public abstract class QueueMessage {

    /**errorCallback for sqs.*/
    @JsonIgnore
    private BiConsumer<QueueMessage, Throwable> errorCallback;

    /**delayInSeconds.*/
    private Integer delayInSeconds;

}