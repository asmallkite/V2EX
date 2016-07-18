package com.example.a10648.v2ex.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.a10648.v2ex.R;

public class UserDetailActivity extends AppCompatActivity {

    WebView webView;

    String target_url ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detail);

        webView = (WebView)findViewById(R.id.user_detail);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        target_url = getIntent().getStringExtra("url");
        webView.loadUrl(target_url);
    }
}
