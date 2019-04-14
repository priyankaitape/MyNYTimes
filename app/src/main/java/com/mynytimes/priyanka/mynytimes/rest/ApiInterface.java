package com.mynytimes.priyanka.mynytimes.rest;

import com.mynytimes.priyanka.mynytimes.models.TopNewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Priyanka on 4/12/2019.
 */

public interface ApiInterface {
    @GET("mostviewed/all-sections/7")
    Call<TopNewsResponse> getTopRatedNews(@Query("api-key") String apiKey);
}