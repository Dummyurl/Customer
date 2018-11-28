package com.thimble.customer.rest;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "http://staging.uiplonline.com:7457/service/";
//    public static final String BASE_URL = "http://192.168.1.129:7457/service/";
    public static final String BASE_URL_IMG = "http://staging.uiplonline.com/lystant-server/uploads/";


    public static final String URL_FEES_COMMISSIONS = "https://www.lystant.com/fees-and-commissions";
    public static final String URL_FAQ = "https://www.lystant.com/mobile/faq";
    public static final String URL_PRIVACY_POLICY = "https://www.lystant.com/mobile/privacy-policy";
    public static final String URL_TERMS_CONDITIONS = "https://www.lystant.com/mobile/terms-condition";

    private static Retrofit retrofit ;

    public static Retrofit getClient(Context context) {

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(buildHTTPClient(context))
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient buildHTTPClient(Context context){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        return httpClient.build();
    }


}
