package com.example.appli3a_perolo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SymbolAPI {

    @GET("/api/v3/company/stock/list")
    Call<RestFinanceResponse> getSymbolResponse();
}
