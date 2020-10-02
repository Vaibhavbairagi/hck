package com.example.lenovo.androidproject1;

public class FavouriteItem {
    private String itemTitle, itemUrl;

    public FavouriteItem(String title, String url) {
        itemTitle = title;
        itemUrl = url;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemUrl() {
        return itemUrl;
    }
}
