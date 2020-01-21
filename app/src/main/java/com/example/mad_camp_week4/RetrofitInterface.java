package com.example.mad_camp_week4;

import com.example.mad_camp_week4.CafeResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/pullProduct")
    Call<ArrayList<CafeResult>> executePull(@Body HashMap<String,String> map);
    @POST("/pushProduct")
    Call<Void> executePush(@Body HashMap<String, String> map);
    @POST("/deleteProduct")
    Call<Void> executeDelete(@Body HashMap<String, String> map);
    @POST("/pushAll")
    Call<Void> executePushAll(@Body List<HashMap<String, String>> arrayList);

}
