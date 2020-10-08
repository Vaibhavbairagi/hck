package com.example.lenovo.androidproject1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class SingleNewsActivity extends AppCompatActivity {

    private TextView contentElement, authorElement, sourceElement, dateElement, titleElement;
    private ImageView imageView;
    private Button openInChromeButton, likeButton;
    private ImageView shareButton;
    private String url, img, title, date, source, author, content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_news);

        imageView = findViewById(R.id.singleNewsImg);
        authorElement = findViewById(R.id.singleNewsAuthor);
        sourceElement = findViewById(R.id.singleNewsSource);
        dateElement = findViewById(R.id.singleNewsDate);
        titleElement = findViewById(R.id.singleNewsTitle);
        contentElement = findViewById(R.id.content);
        openInChromeButton = findViewById(R.id.buttonToInternet);
        shareButton = findViewById(R.id.singleNewsShare);
        likeButton = findViewById(R.id.buttonLike);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        img = intent.getStringExtra("img");
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        source = intent.getStringExtra("source");
        author = intent.getStringExtra("author");
        content = intent.getStringExtra("content");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());

        Glide.with(this)
                .load(img)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

        authorElement.setText(author == null ? "None" : author);
        sourceElement.setText(source == null ? "None" : source);
        dateElement.setText(Utils.DateFormat(date));
        titleElement.setText(title);
        contentElement.setText(content);

        openInChromeButton.setOnClickListener(openInChrome);
        shareButton.setOnClickListener(share);
        likeButton.setOnClickListener(addToLiked);
    }

    private View.OnClickListener openInChrome = new View.OnClickListener() { //private
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    };

    private View.OnClickListener share = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_SEND); //Share
            intent.setType("text/plan");
            String body = title + "\n" + url + "\n" + "Share from News App" + "\n";
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share with:"));
        }
    };

    private View.OnClickListener addToLiked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences likedNews = SingleNewsActivity.this.getSharedPreferences("favourites", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = likedNews.edit();
            editor.putString(title, url);
            editor.apply();

            Toast.makeText(getBaseContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
        }
    };
}