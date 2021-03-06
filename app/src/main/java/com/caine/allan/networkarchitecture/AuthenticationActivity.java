package com.caine.allan.networkarchitecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.caine.allan.networkarchitecture.web.DataManager;

/**
 * Created by allancaine on 2015-10-19.
 */
public class AuthenticationActivity extends AppCompatActivity {

    private WebView mWebView;
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView = new WebView(this);
        setContentView(mWebView);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(mWebViewClient);

        mDataManager = DataManager.get(this);
        mWebView.loadUrl(mDataManager.getAuthenticationUrl());

    }

    private WebViewClient mWebViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains(DataManager.OAUTH_REDIRECT_URI)){
                FoursquareOauthUriHelper uriHelper =
                        new FoursquareOauthUriHelper(url);
                if(uriHelper.isAuthorized()){
                    String accessToken = uriHelper.getAccessToken();
                    TokenStore tokenStore = new TokenStore(AuthenticationActivity.this);
                    tokenStore.setAccessToken(accessToken);
                    Toast.makeText(AuthenticationActivity.this, "You were successfully authenticated", Toast.LENGTH_LONG).show();;
                }else{
                    Toast.makeText(AuthenticationActivity.this, "You were not authenticated", Toast.LENGTH_LONG).show();
                }
                finish();
            }
            return true;
        }
    };
}
