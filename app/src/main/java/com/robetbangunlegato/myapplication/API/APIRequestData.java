package com.robetbangunlegato.myapplication.API;

import com.robetbangunlegato.myapplication.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrive.php")
    Call<ModelResponse> ardRetrive();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("koordinat") String koordinat,
            @Field("foto") String foto,
            @Field("alamat") String alamat,
            @Field("tentang") String tentang
    );

    @FormUrlEncoded
    @POST("update.php")
    Call <ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("koordinat") String koordinat,
            @Field("foto") String foto,
            @Field("alamat") String alamat,
            @Field("tentang") String tentang
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call <ModelResponse> ardDelete(
            @Field("id") String id
    );

}
