package com.example.bookworm;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class booksLoader extends AsyncTaskLoader<List<book>> {
    String url;
    public static List<book> arrayList = null;

    public booksLoader(Context context, String url) {
        super(context);
        if(url == null){
            return;
        }
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<book> loadInBackground() {
        if(url == null) {
            return null;
        }
        arrayList = QueryUtils.fetchBooksData(url);
        return arrayList;
    }
}