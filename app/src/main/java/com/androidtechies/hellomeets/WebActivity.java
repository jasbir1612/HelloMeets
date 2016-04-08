package com.androidtechies.hellomeets;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    WebView webView;
    Button load;
    public String link;
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        load = (Button) findViewById(R.id.load);
        webView = (WebView) findViewById(R.id.webview);
        load.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        link = getIntent().getStringExtra("url");
        dialog = new ProgressDialog(WebActivity.this);
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
        dialog.setMessage("Loading..Please wait.");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        webView.loadUrl("google.com");
        Toast.makeText(WebActivity.this, ""+link, Toast.LENGTH_SHORT).show();

        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    protected void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
    }

}
