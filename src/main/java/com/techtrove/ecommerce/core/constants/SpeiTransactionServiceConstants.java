package com.techtrove.ecommerce.core.constants;

public class SpeiTransactionServiceConstants {


    public final static String SPEI_OUT =  "spei-out";
    public final static String SPEI_IN =  "spei-in";
    public final static String SPEI_UPDATE_STATUS = SPEI_OUT + "/{id}/status";
    public final static String SPEI_IN_UPDATE_STATUS = SPEI_IN + "/{id}/status";
    public final static String SPEI_UPDATE_STATUS_TRACKING_KEY = SPEI_OUT + "/{trackingKey}/status/tracking";
    public final static String SPEI_IN_DEVOLUTION = SPEI_IN + "/devolution/{id}";
    public final static String SPEI_OUT_STATUS = SPEI_OUT + "/status/{prefix}";
    public final static String SPEI_IN_STATUS = SPEI_IN + "/status/{prefix}";



}
