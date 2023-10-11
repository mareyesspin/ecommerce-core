package com.techtrove.ecommerce.core.utils;

import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.techtrove.ecommerce.core.enums.GenericErrorCode;
import com.techtrove.ecommerce.core.models.errors.ErrorArgument;
import com.techtrove.ecommerce.core.models.exception.ECommInternalServerErrorException;
import com.techtrove.ecommerce.core.models.messagequeue.ParamsConnectionQueue;
import lombok.extern.slf4j.Slf4j;


import java.util.Optional;

import static com.techtrove.ecommerce.core.utils.StringUtils.lineBreakRemove;

/**
 * @Author: Miguel A.R.S.
 * @Email: miguel.reyes@spinbyoxxo.com.mx
 * @Description: Clase generica para enviar mensages por sqs
 * @Date: 31/05/23
 */
@Slf4j
public final class SQSMessageSenderV2 {

    /** constructor for utility class.*/
    private  SQSMessageSenderV2() {
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo para enviar mensajes a un sqs
     * @Date: 06/04/23
     * @param paramsConnectionQueue
     * @return String
     */
    public static Optional<String> sendMessageToQueue(ParamsConnectionQueue paramsConnectionQueue) {
        SendMessageRequest sendMessageQueue = null;
        SendMessageResult result = null;
        try {
            sendMessageQueue = new SendMessageRequest()
                    .withQueueUrl(paramsConnectionQueue.getUrlQueue())
                    .withMessageBody(paramsConnectionQueue
                            .getObjectMapper()
                            .writeValueAsString(paramsConnectionQueue.getMessage()));
            if(paramsConnectionQueue.getDelaySeconds()>0)
                sendMessageQueue.setDelaySeconds(paramsConnectionQueue.getDelaySeconds());
        }catch (JsonProcessingException e){
            log.error("Error calling service API {}", ExceptionUtils.getSimplifiedStackTrace(e));
            throw new ECommInternalServerErrorException(GenericErrorCode.GENERIC_ERROR.getDescription(),
                    GenericErrorCode.GENERIC_ERROR.getInstance()
                            .addArg(new ErrorArgument(ErrorArgument.REASON, e.getMessage()))
                            .addArg(new ErrorArgument(ErrorArgument.ROOT_CAUSE, e.getClass().getName())));
        }

        try{
            result = paramsConnectionQueue.getAmazonSQS().sendMessage(sendMessageQueue);
        }catch (AmazonSQSException e){
            log.error("Error sending message to queue", ExceptionUtils.getSimplifiedStackTrace(e));
            throw new ECommInternalServerErrorException(GenericErrorCode.GENERIC_ERROR.getDescription(),
                    GenericErrorCode.GENERIC_ERROR.getInstance()
                            .addArg(new ErrorArgument(ErrorArgument.REASON, e.getMessage()))
                            .addArg(new ErrorArgument(ErrorArgument.ROOT_CAUSE, e.getClass().getName())));
        }
        log.info("message sent to queue || {} {}",
                paramsConnectionQueue.getUrlQueue(),
                lineBreakRemove(sendMessageQueue.getMessageBody()));
        return Optional.of(result.getMessageId());
    }

}
