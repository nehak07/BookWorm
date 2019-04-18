package com.example.bookworm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class bookListViewActivity extends AppCompatActivity {

    private WebView webVw =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_view);

        String infoUrl = getIntent().getStringExtra("infoUrl");

        this.webVw = (WebView) findViewById(R.id.webvw);

        webVw.setWebViewClient(new webViewCLient());

        WebSettings settings = webVw.getSettings();
        settings.setJavaScriptEnabled(true);

        webVw.loadUrl(infoUrl);



    }

    public class webViewCLient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

}