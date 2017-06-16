package perseverance.li.quiet.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseToolbarActivity;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/16 14:08
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/16 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class WebActivity extends BaseToolbarActivity {

    public static final String TITLE_TAG = "title";
    public static final String URL_TAG = "url";
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onMenuHome() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.web_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onInitWebViewSettings();
        loadUrl();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.h5_webview);
        mProgressBar = (ProgressBar) findViewById(R.id.webview_pb);
    }

    protected void onInitWebViewSettings() {
        WebSettingsUtil.initSettings(this, mWebView);
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        };
        mWebView.setWebChromeClient(webChromeClient);
        WebViewClient webViewClient = new WebViewClient();
        mWebView.setWebViewClient(webViewClient);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.requestFocus();
    }

    private void loadUrl() {
        String title = getIntent().getStringExtra(TITLE_TAG);
        if (TextUtils.isEmpty(title)) {
            title = getResources().getString(R.string.web_label);
        }
        mActionbar.setTitle(title);
        String url = getIntent().getStringExtra(URL_TAG);
        mWebView.loadUrl(url);
    }
}
