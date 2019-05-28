package com.journaldev.mvpdagger2retroiftrxjava;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.journaldev.mvpdagger2retroiftrxjava.R;

import dagger.multibindings.ElementsIntoSet;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        pd = new ProgressDialog(WebViewActivity.this);
        pd.setMessage("Loading ......");

        pd.show();
        String url = getIntent().getStringExtra("url");
        Log.e("MyString",url);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new MyBrowser());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        else
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.loadUrl(url);

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            if (!pd.isShowing())
                    pd.show();
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            if (pd.isShowing())
                pd.dismiss();
        }
    }
}