package com.cp.contesttracker.problemsuggestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cp.contesttracker.R;

import java.util.Objects;

public class WebViewActivity extends AppCompatActivity {

    private WebView myWebView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Practice Recommendation");


        myWebView = (WebView) findViewById(R.id.webview);

        progressBar = findViewById(R.id.progress_bar_web);
        progressBar.setVisibility(View.VISIBLE);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:(function() { " +
                        "var container = document.getElementsByClassName('container')[0];" +
                        "var practiceTab = document.getElementById('practice_tab');" +
                        "container.innerHTML = practiceTab.innerHTML;" +
                        "document.getElementsByClassName('navbar')[0].innerHTML = '<h4>Practice Recommendation:(Scroll Down)</h4>'"+
                        "})()");
                progressBar.setVisibility(View.GONE);
            }

        });
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String type = intent.getStringExtra("type");
        myWebView.loadUrl(url);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
