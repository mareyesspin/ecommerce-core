package com.techtrove.ecommerce.core.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


/**
 *
 * @Author: Miguel A.R.S.
 * @Email: miguel.reyes@spinbyoxxo.com.mx
 * @Description: Calse que servira, como el unico Model que deben recibir los @Services
 * @Date: 15/05/23
 */


public class ServiceModel {


    @Setter
    private Object model;

    @Setter
    @Getter
    private String processNumber;

    @Setter
    @Getter
    private String listNumber;

    @Setter
    @Getter
    private String queryNumber;

    private HashMap<String, String> aditionalFields = new HashMap<String, String>();
    public String getAditionalField(String key) {
        return aditionalFields.get(key);
    }
    public void setAditionalField(String key, String value) {
        aditionalFields.put(key, value);
    }

    public <T> T getModel(Class<T> type) {
        return type.cast(model);
    }

}
