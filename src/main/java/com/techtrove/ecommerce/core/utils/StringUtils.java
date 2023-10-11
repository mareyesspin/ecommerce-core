package com.techtrove.ecommerce.core.utils;

public class StringUtils {

    public static  String lineBreakRemove(String msg){
        msg = msg.replaceAll(System.getProperty("line.separator", ""),"");
        return msg;
    }
}
