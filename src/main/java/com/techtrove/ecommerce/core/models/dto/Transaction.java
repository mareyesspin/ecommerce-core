package com.techtrove.ecommerce.core.models.dto;

import lombok.Getter;

import java.util.HashMap;

public class Transaction {

    @Getter
    private HashMap<String, Object> details = null;

    public Transaction(String llave, Object valor){
        details = new HashMap<String, Object>();
        setDetail(llave,  valor);
    }

    public <T> T getDetail(String llave,Class<T> type) {
        return type.cast(details.get(llave));
    }
    /*public <T> T getDetail(String llave) {
        return  (T)details.get(llave);
    }*/
    public void setDetail(String llave, Object valor) {
        details.put(llave, valor);
    }


}
