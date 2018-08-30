package perseverance.li.quiet.web;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import perseverance.li.quiet.QuietApplication;
import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseToolbarActivity;
import perseverance.li.quiet.util.Util;

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
    private static final int THUMB_SIZE = 150;
    private String mWebUrl;
    private String mTitle;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private LinearLayout mContentLayout;
    private WebDownloadListener mWebDownloadListener;

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
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = mWebUrl;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = mTitle;

                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.share_logo);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                bmp.recycle();
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = Util.buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                QuietApplication.getWxAPi().sendReq(req);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        mWebDownloadListener = new WebDownloadListener(this);
        mWebView.setWebChromeClient(webChromeClient);
        WebViewClient webViewClient = new WebViewClient();
        mWebView.setWebViewClient(webViewClient);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.setDownloadListener(mWebDownloadListener);
        mWebView.requestFocus();
    }

    private void loadUrl() {
        mTitle = getIntent().getStringExtra(TITLE_TAG);
        if (TextUtils.isEmpty(mTitle)) {
            mTitle = getResources().getString(R.string.web_label);
        }
        mActionbar.setTitle(mTitle);
        mWebUrl = getIntent().getStringExtra(URL_TAG);
        mWebView.loadUrl(mWebUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            //1.先把webview移除
            if (mContentLayout != null && mContentLayout.getChildCount() != 0) {
                mContentLayout.removeView(mWebView);
            }

            //2.再将webview内容置空
            if (mWebView != null) {
                mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                mWebView.clearHistory();

                mWebView.removeAllViews();
                mWebView.destroy();
                mWebView = null;
            }

            if (mWebDownloadListener != null) {
                mWebDownloadListener.onDestroy();
                mWebDownloadListener = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}