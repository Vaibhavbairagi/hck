package com.example.lenovo.androidproject1;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.androidproject1.api.ApiClient;
import com.example.androidproject1.api.ApiInterface;
import com.example.androidproject1.models.Article;
import com.example.androidproject1.models.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource {
    public static final String API_KEY = "62fde5352a36459394875dbc6750813b";

    MutableLiveData<List<Article>> articles = new MutableLiveData<List<Article>>();

        public void LoadJson() { //final String keyword

            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class); //Creating ApiInterface to work with Api

            String country = Utils.getCountry();
            String language = Utils.getLanguage();

            Call<News> call;
            call = apiInterface.getNews(country, API_KEY);

            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    if (response.isSuccessful() && response.body().getArticle() != null) {
                        if (articles.getValue() != null) {
                            articles.setValue(null);
                        }
                        articles.postValue(response.body().getArticle());
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    return;
                }


            });
        }

    public MutableLiveData<List<Article>> getArticles() {
            LoadJson();
            return articles;
    }
}