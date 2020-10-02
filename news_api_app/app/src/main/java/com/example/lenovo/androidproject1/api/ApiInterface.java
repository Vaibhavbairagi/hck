package com.example.lenovo.androidproject1.api;

import com.example.androidproject1.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<News> getNews(@Query("country") String country, @Query("apiKey") String apiKey);
}
