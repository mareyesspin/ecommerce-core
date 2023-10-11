package com.techtrove.ecommerce.core.models.messagequeue;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.HashMap;

/**
 *
 * @Author: Miguel A.R.S.
 * @Email: miguel.reyes@spinbyoxxo.com.mx
 * @Description: Clase que servira como Model para enviar un mensage por sqs
 * @Date: 31/05/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParamsConnectionQueue {

    @Setter
    @Getter
    protected String urlQueue;

    @Setter
    @Getter
    protected Object message;

    @Setter
    @Getter
    protected AmazonSQS amazonSQS;


    @Builder.Default
    @Getter
    protected ObjectMapper objectMapper = new ObjectMapper();
    @Setter
    @Getter
    protected int delaySeconds;


    @Builder.Default
    protected HashMap<String, String> aditionalFields = new HashMap<String, String>();

    public String getAditionalField(String key) {
        return aditionalFields.get(key);
    }
    public void setAditionalField(String key, String value) {
        aditionalFields.put(key, value);
    }

}
