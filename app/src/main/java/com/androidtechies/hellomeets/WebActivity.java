package com.androidtechies.hellomeets;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    Button load, tab;
    public String link, URL;
    public ProgressDialog dialog;
    private Bundle webViewBundle;
    View view;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        tab = (Button) findViewById(R.id.btntab);
        load = (Button) findViewById(R.id.btnload);
        webView = (WebView) findViewById(R.id.webview);
        view = (View) findViewById(R.id.dialog_top);
        startService(new Intent(WebActivity.this, ChatHeadService.class));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        link = getIntent().getDataString();
        URL = getIntent().getStringExtra("url");
//        Toast.makeText(WebActivity.this, "" + savedInstanceState, Toast.LENGTH_SHORT).show();
        if (!isNetworkAvailable()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            Toast.makeText(WebActivity.this, "Network Unavailable", Toast.LENGTH_SHORT).show();
        } else if(link!=null||URL!=null){

            dialog = new ProgressDialog(WebActivity.this);
            dialog.setMessage("Loading..Please wait.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
            webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default
            if(URL!=null)
            {
                webView.loadUrl(URL);
            }
            else {
                webView.loadUrl(link);
            }
        }

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });



        final WebViewClient webViewClient = new WebViewClient() {

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

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                WebResourceResponse response = null;
                if ("GET".equals(request.getMethod())) {
                    try {
                        response = getWebResponse(view.getContext().getApplicationContext(), request.getUrl().toString());
                    } catch (Exception e) {
                        Log.e(Utils.LogTag, "Error while overriding getting web response", e);
                    }
                }
                return response;
            }

            @SuppressWarnings("deprecation")
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                WebResourceResponse response = null;
                try {
                    response = getWebResponse(view.getContext().getApplicationContext(), url);
                } catch (Exception e) {
                    Log.e(Utils.LogTag, "Error while overriding getting web response", e);
                }
                return response;
            }

            WebResourceResponse getWebResponse(Context context, String url) {
                // YOUR IMPLEMENTATION that will save resource located at passed url
                return null;
            }
        };
        webView.setWebViewClient(webViewClient);




        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

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

                Intent i = new Intent(WebActivity.this, MyDialog.class);
                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(link!=null) {
                    editor.putString("add", link);
                }
                if(URL!=null) {
                    editor.putString("add", URL);
                }
                editor.commit();
                if(link!=null||URL!=null)
                    Toast.makeText(WebActivity.this, "Link added Succesfully", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(WebActivity.this, "No link specified.", Toast.LENGTH_SHORT).show();
                }
//                link = getIntent().getDataString();
//                Toast.makeText(WebActivity.this, "" + savedInstanceState, Toast.LENGTH_SHORT).show();
//                if (savedInstanceState != null) {
//                    webView.restoreState(savedInstanceState);
//                } else {
//
//                    dialog = new ProgressDialog(WebActivity.this);
//                    dialog.setMessage("Loading..Please wait.");
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.show();
//                    webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
//                    webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
//                    webView.getSettings().setAllowFileAccess( true );
//                    webView.getSettings().setAppCacheEnabled( true );
//                    webView.getSettings().setJavaScriptEnabled(true);
//                    webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default
//
//                    if ( !isNetworkAvailable() ) { // loading offline
//                        webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
//                    }
//
//                    webView.loadUrl("http://www.google.com");
//                    webView.loadUrl(link);
//                }
//
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
//
////        Toast.makeText(WebActivity.this, ""+link, Toast.LENGTH_SHORT).show();
//
//                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//
////                WebSettings webSettings = webView.getSettings();
////                webSettings.setJavaScriptEnabled(true);
////                webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Utils.LogTag, "onDestroy called");
    }
}
