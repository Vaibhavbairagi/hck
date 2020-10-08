package com.example.lenovo.androidproject1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject1.models.Article;

import java.util.List;

public class MyViewModel extends ViewModel {
    private MutableLiveData<List<Article>> data = null;
    RemoteDataSource remoteDataSource = new RemoteDataSource();

    public MutableLiveData<List<Article>> getData() {
        if (data == null) {
            data = remoteDataSource.getArticles();
        }
        return data;
    }
}