package com.example.lenovo.androidproject1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class FavouritesActivity extends AppCompatActivity {
    private ArrayList<FavouriteItem> mFavouriteList;

    private RecyclerView mRecyclerView;
    private FavouriteNewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button openInChromeButton;
    private ImageButton buttonRemove, buttonShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_favourites);
        createFavList();
        buildRecyclerView();
        setButtons();
    }

    public void openInChrome(int position) {
        String url = mFavouriteList.get(position).getItemUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void shareItem(int position) {
        String sharedTitle = mFavouriteList.get(position).getItemTitle();
        String sharedUrl = mFavouriteList.get(position).getItemUrl();
        Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plan");
            String body = sharedTitle + "\n" + sharedUrl + "\n" + "Share from News App" + "\n";
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share with:"));
    }

    public void removeItem(int position) {
        String mustBeDeleted = mFavouriteList.get(position).getItemTitle();
        mFavouriteList.remove(position);
        SharedPreferences favPreferences = FavouritesActivity.this.getSharedPreferences("favourites", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = favPreferences.edit();
                editor.remove(mustBeDeleted);
                editor.apply();
                recreate();
        mAdapter.notifyItemRemoved(position);
    }

    public void createFavList() {
        SharedPreferences favPreferences = FavouritesActivity.this.getSharedPreferences("favourites", Context.MODE_PRIVATE);
        Map<String, ?> map = favPreferences.getAll();
        mFavouriteList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            mFavouriteList.add(new FavouriteItem(entry.getKey(), entry.getValue().toString()));
        }
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.favRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FavouriteNewsAdapter(mFavouriteList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FavouriteNewsAdapter.OnFavItemClickListener() { //without FavouriteNewsAdapter
            @Override
            public void onItemClick(int position) { }

            @Override
            public void onUrlClick(int position) {
                openInChrome(position);
            }

            @Override
            public void onShareClick(int position) {
                shareItem(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

        });
    }

    public void setButtons() {
        buttonShare = findViewById(R.id.favItemShare);
        buttonRemove = findViewById(R.id.favItemDelete);
        openInChromeButton = findViewById(R.id.favItemOpenInChrome);
    }
}