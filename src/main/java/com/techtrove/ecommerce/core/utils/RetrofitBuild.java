package com.techtrove.ecommerce.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.techtrove.ecommerce.core.interceptors.ParamsAudInterceptor;
import com.techtrove.ecommerce.core.interceptors.EcommerceCoreRfitLogInterceptor;
import lombok.Data;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @Author: Miguel A.R.S.
 * @Email: miguel.reyes@spinbyoxxo.com.mx
 * @Description: Esta clase servira para contruir un Clien de retroFit
 * @Date: 07/06/23
 * @param <T>
 */
@Data
public class RetrofitBuild<T> {

    private String baseURL;
    private Class<T> repositoryClass;
    private String apiKey;

    private Long connectTimeout = Long.valueOf(15);
    private Long readTimeout=Long.valueOf(15);;
    private Long writeTimeout=Long.valueOf(15);;

    private TimeUnit timeUnit = TimeUnit.SECONDS;


    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, Boolean.FALSE)
                .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, Boolean.FALSE)
                .registerModule(new JavaTimeModule())
                .findAndRegisterModules() //needed to support instantiation by jackson
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: Genera un ApiClient don T es la Interfaz que contiene las llamadas a una API
     * @Date: 07/06/23
     * @return T
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public   T buildVendor() throws NoSuchAlgorithmException, KeyManagementException {
        final Interceptor loggingInterceptor = EcommerceCoreRfitLogInterceptor
                .configure()
                .withLoggingCondition(null)
                .redactHeaders(
                        HttpHeaders.ACCEPT,
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE
                        )
                .instance();
        final ParamsAudInterceptor paramsAudInterceptor = new ParamsAudInterceptor();
       




        // Authentication interceptor that adds token bearer to every request.
        final Interceptor authInterceptor = chain -> {
            final Request.Builder requestBuilder = chain.request().newBuilder()
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);



            if (this.apiKey != null) {
                requestBuilder.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.getApiKey());
            }
            return chain.proceed(requestBuilder.build());
        };

        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(paramsAudInterceptor);
        client.addInterceptor(loggingInterceptor);
        client.connectTimeout(this.getConnectTimeout(), this.getTimeUnit());
        client.readTimeout(this.getReadTimeout(), this.getTimeUnit());
        client.writeTimeout(this.getWriteTimeout(), this.getTimeUnit());

        return new Retrofit.Builder()
                .baseUrl(this.getBaseURL())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client.build())
                .build()
                .create(repositoryClass);
    }






}
