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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;



public interface ApiInterface {



    @FormUrlEncoded
    @POST("user-login")
    Call<ResponseBody> login(@FieldMap Map<String, String> params);

//    @FormUrlEncoded
    @POST("user-logout")
    Call<ResponseBody> logout();

    @GET
    Call<ResponseBody> loadSuggestions(@Url String url);

    @GET
    Call<ResponseBody> loadPlaceDetails(@Url String url);

    @GET
    Call<ResponseBody> getAddress(@Url String url);


    @Multipart
    @POST("lystant-signup-step1")
    Call<ResponseBody> lystantSignupS1(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> param);

    @FormUrlEncoded
    @POST("lystant-signup-step3")
    Call<ResponseBody> lystantSignupS2(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("check-duplicate-data")
    Call<ResponseBody> lystantCheckDuplicateData(@FieldMap Map<String, String> params);


    @Multipart
    @POST("save-user")
    Call<ResponseBody> sellerSignup(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> param);

    @FormUrlEncoded
    @POST("forget_password")
    Call<ResponseBody> forgotPWD(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("lystant-update-username")
    Call<ResponseBody> lystantUpdateUsername(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("lystant-update-password")
    Call<ResponseBody> lystantUpdatePWD(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("seller-update-username")
    Call<ResponseBody> sellerUpdateUsername(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("seller-update-password")
    Call<ResponseBody> sellerUpdatePWD(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("get-setting-data-byslug")
    Call<ResponseBody> getSettingsData(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("get-bank-details")
    Call<ResponseBody> getBankDetails(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("seller-save-card")
    Call<ResponseBody> sellerSaveCard(@FieldMap Map<String, String> params);


    @Multipart
    @POST("save-seller-bank-details")
    Call<ResponseBody> saveSellerBankDetails(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> param);

    @FormUrlEncoded
    @POST("save-bank-details")
    Call<ResponseBody> saveLystantBankDetails(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("get-plaid-access-token")
    Call<ResponseBody> getPLAIDToken(@FieldMap Map<String, String> params);



    @GET("get-emplyment-list")
    Call<ResponseBody> getEmploymentList();

    @FormUrlEncoded
    @POST("save-emplyment-data")
    Call<ResponseBody> addEmployment(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("update-employment-history")
    Call<ResponseBody> editEmploymentHist(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("delete-employment-history")
    Call<ResponseBody> deleteEmploymentHist(@FieldMap Map<String, String> params);


    @GET("get-education-list")
    Call<ResponseBody> getEducationList();


    @FormUrlEncoded
    @POST("save-education-data")
    Call<ResponseBody> addEducation(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("update-education-data")
    Call<ResponseBody> editEducation(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("delete-education-details")
    Call<ResponseBody> deleteEducation(@FieldMap Map<String, String> params);









    @Multipart
    @POST("new-item-post")
    Call<ResponseBody> postJob(@Part List<MultipartBody.Part> files, @PartMap Map<String, RequestBody> param);





}
