package com.androidtechies.hellomeets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    Button load, tab;
    public String link;
    public ProgressDialog dialog;
    private Bundle webViewBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        tab = (Button) findViewById(R.id.btntab);
        load = (Button) findViewById(R.id.btnload);
        webView = (WebView) findViewById(R.id.webview);

        link = getIntent().getDataString();
        Toast.makeText(WebActivity.this, ""+savedInstanceState, Toast.LENGTH_SHORT).show();
        if(savedInstanceState!=null)
        {
            webView.restoreState(savedInstanceState);
        }
        else {

            dialog = new ProgressDialog(WebActivity.this);
            dialog.setMessage("Loading..Please wait.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            webView.loadUrl(link);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

//        Toast.makeText(WebActivity.this, ""+link, Toast.LENGTH_SHORT).show();

        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WebActivity.this, MyDialog.class);
                startActivity(i);
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog = new ProgressDialog(WebActivity.this);
//                webView.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public void onPageFinished(WebView view, String url) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                        view.loadUrl(url);
//                        return true;
//                    }
//                });
//                dialog.setMessage("Loading..Please wait.");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
//                link = getIntent().getDataString();
//                if(link==null)
//                {
//                    webView.loadUrl("http://google.com");
//                }
//                else {
//                    webView.loadUrl(link);
//                }
//
//                Toast.makeText(WebActivity.this, ""+link, Toast.LENGTH_SHORT).show();
//
//                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//
//                WebSettings webSettings = webView.getSettings();
//                webSettings.setJavaScriptEnabled(true);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }
}
