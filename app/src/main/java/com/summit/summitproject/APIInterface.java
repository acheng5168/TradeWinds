package com.summit.summitproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("1.0/tops")
    Call<List<APIResponse>> getPrices(
            @Query("symbols") String ticker,
            @Query("filter") String filter);


}
