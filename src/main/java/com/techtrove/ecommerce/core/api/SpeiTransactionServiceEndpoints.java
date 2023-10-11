package com.techtrove.ecommerce.core.api;



import com.techtrove.ecommerce.core.constants.SpeiTransactionServiceConstants;

import com.techtrove.ecommerce.core.models.request.transactionsService.*;
import com.techtrove.ecommerce.core.models.response.transactionservice.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.PUT;


/**
 * Repository with methods http for call transactions service.
 * @author juan.lima
 *
 * */

public interface SpeiTransactionServiceEndpoints {


    /** send spei retrofit.
     *
     * @param speiOutTrRequest the speiOutTrRequest
     * @return   Call<SpeiOutTrResponse>  the  Call<SpeiOutTrResponse>
     * */
    @Headers("Accept: application/json")
    @POST(value = SpeiTransactionServiceConstants.SPEI_OUT)
    Call<SpeiOutTrResponse> sendSpeiV2(@Body SpeiOutTrRequest speiOutTrRequest);

    /** update spei retrofit.
     *
     * @param speiOutStatusRequest the speiOutStatusRequest
     * @param id the id
     * @return   Call<SpeiOutTrResponse>  the  Call<SpeiOutTrResponse>
     * */
    @Headers("Accept: application/json")
    @PUT(value =  SpeiTransactionServiceConstants.SPEI_UPDATE_STATUS)
    Call<SpeiOutStatusResponse> updateSpeiOutStatus(@Body SpeiOutStatusRequest speiOutStatusRequest,
                                                    @Path(value="id") Long id);

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: interfaz para consumir el endpoint que realiza una
     *               actualizacion en el estatus del spei-in
     * @Date: 01/08/23
     * @param speiInStatusRequest
     * @param id
     * @return Call<SpeiInStatusResponse>
     */
    @Headers("Accept: application/json")
    @PUT(value =  SpeiTransactionServiceConstants.SPEI_IN_UPDATE_STATUS)
    Call<SpeiInStatusResponse> updateSpeiInStatus(@Body SpeiInStatusRequest speiInStatusRequest,
                                                  @Path(value="id") Long id);


    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: call para para Retrofit para invocar un SpeiIn.
     * @Date: 28/06/23
     * @param speiInTrRequest  the speiInTrRequest
     * @return Call<SpeiInTrResponse>
     */
    @Headers("Accept: application/json")
    @POST(value =SpeiTransactionServiceConstants.SPEI_IN)
    Call<SpeiInTrResponse> speiIn(@Body SpeiInTrRequest speiInTrRequest);



    /**
     *
     * @Author: JuanMa Lima.
     * @Email: juan.lima@spinbyoxxo.com.mx
     * @Description: Call para actualizar estatus por tracking
     * @Date: 03/08/23
     * @param speiOutStatusRequest the speiOutStatusRequest
     * @param trackingKey the trackingKey
     * @return Call<SpeiOutStatusResponse>
     */
    @Headers("Accept: application/json")
    @PUT(value =  SpeiTransactionServiceConstants.SPEI_UPDATE_STATUS_TRACKING_KEY)
    Call<SpeiOutStatusResponse> updateSpeiOutStatusTrackingKey(@Body SpeiOutStatusRequest speiOutStatusRequest,
                                                                @Path(value="trackingKey") String trackingKey);

    @Headers("Accept: application/json")
    @POST(value =  SpeiTransactionServiceConstants.SPEI_IN_DEVOLUTION)
    Call<SpeiInDevolutionTrResponse> speiInDevolution(@Body SpeiInDevolutionTrRequest speiInDevolutionTrRequest,
                                                      @Path(value="id") Long id);

}
