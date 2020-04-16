package com.example.appli3a_perolo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailsAPI {
   // @GET("/api/v3/stock/real-time-price/") // il faut ajouter la variable qui contient le symbol Ticker
    @GET("api/v3/stock/real-time-price/{name}") Call<RestFinanceResponse> getData(@Path("name") String name);
    Call<RestDetailsResponse> getDetailsResponse();
}
