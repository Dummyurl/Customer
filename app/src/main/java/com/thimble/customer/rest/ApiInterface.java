package com.thimble.customer.rest;



import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

import static com.thimble.customer.rest.ApiHelper.ACCESS_TOKEN;


public interface ApiInterface {

    @POST(ApiHelper.LOGIN)
    Call<ResponseBody> login(@Body Map<String, Object> params);


    @Headers("Content-Type: application/json")
    @POST(ApiHelper.CUST_LIST)
    Call<ResponseBody> loadCustomers(@Header("Authorization") String auth,@Body Map<String, Object> params);


    @Headers("Content-Type: application/json")
    @POST(ApiHelper.SAVE_CUSTOMER)
    Call<ResponseBody> saveCustomer(@Header("Authorization") String auth,@Body Map<String, Object> params);

    @Headers("Content-Type: application/json")
    @POST(ApiHelper.STATES)
    Call<ResponseBody> loadStates(@Header("Authorization") String auth);

    @GET
    Call<ResponseBody> getAddress(@Url String url);


    @Multipart
    @POST("new-item-post")
    Call<ResponseBody> postJob(@Part List<MultipartBody.Part> files, @PartMap Map<String, RequestBody> param);





}
