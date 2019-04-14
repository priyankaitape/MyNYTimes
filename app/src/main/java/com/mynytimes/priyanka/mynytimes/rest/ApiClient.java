package com.mynytimes.priyanka.mynytimes.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Priyanka on 4/12/2019.
 */

public class ApiClient {

    public static final String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}