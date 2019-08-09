package com.summit.summitproject;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    private static final String BASE_URL = "https://api.iextrading.com/";

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
        }
        return retrofit;

    }

}
