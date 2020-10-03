package com.example.lenovo.androidproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject1.models.Article;

import java.util.List;

public class PopularNewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter = new Adapter();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_popular_news);

        recyclerView = findViewById(R.id.recyclerview);

        layoutManager = new LinearLayoutManager(PopularNewsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        MutableLiveData<List<Article>> data = model.getData();
            data.observe(this, new Observer<List<Article>>() {
                @Override
                public void onChanged(List<Article> articles) {
                    adapter.setData(articles, PopularNewsActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    initListener(articles);
                }
            });
    }

    private void initListener(final List<Article> articles) { //  Обработка нажатия на новость: переход в SingleNewsActivity
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PopularNewsActivity.this, SingleNewsActivity.class);
                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img", article.getUrlToImage());
                intent.putExtra("date", article.getPublishedAt());
                intent.putExtra("source", article.getSource().getName());
                intent.putExtra("author", article.getAuthor());
                intent.putExtra("content", article.getContent());

                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Создание меню
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //Переход в FavouritesActivity
        switch (item.getItemId()) {
            case R.id.action_favourites:
                startActivity(new Intent(PopularNewsActivity.this, FavouritesActivity.class));
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}