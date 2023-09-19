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


        Intent intent = getIntent();
        if(intent.getStringExtra("type").equals("stat"))
            Objects.requireNonNull(getSupportActionBar()).setTitle("Ratings & Statistics");
        else
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
                if (!url.equals("") && (url.startsWith("http://") || url.startsWith("https://"))) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Intent intent1 = getIntent();
                if(intent1.getStringExtra("type").equals("recommendation"))
                {
                    view.loadUrl("javascript:(function() { " +
                            "var container = document.getElementsByClassName('container')[0];" +
                            "var practiceTab = document.getElementById('practice_tab');" +
                            "container.innerHTML = practiceTab.innerHTML;" +
                            "document.getElementsByClassName('navbar')[0].innerHTML = '<h4>Practice Recommendation:(Scroll Down)</h4>'"+
                            "})()");
                }
                else
                {
                    view.loadUrl("javascript:(function() { " +
                            "var container = document.querySelector('.container');" +
                            "if (container) {" +
                            "   var rows = container.querySelectorAll('.row');" +
                            "   for (var i = 0; i < rows.length; i++) {" +
                            "       if (i>1 && i <= 5) {" +
                            "           rows[i].style.display = 'block';" +
                            "       } else {" +
                            "           rows[i].style.display = 'none';" +
                            "       }" +
                            "   }" +
                            "}" +
                            "document.getElementsByClassName('navbar')[0].innerHTML = ''"+
                            "})()");
                }
                progressBar.setVisibility(View.GONE);
            }

        });
        String url = intent.getStringExtra("url");
        String type = intent.getStringExtra("type");
        myWebView.loadUrl(url);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
