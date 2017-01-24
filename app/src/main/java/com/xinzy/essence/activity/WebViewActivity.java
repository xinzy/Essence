package com.xinzy.essence.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xinzy.essence.R;
import com.xinzy.essence.base.BaseActivity;
import com.xinzy.essence.widget.SafetyWebView;

public class WebViewActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, View.OnClickListener
{
    private static final String KEY_URL = "URL";

    private SafetyWebView mWebView;
    private ProgressBar   mProgressBar;

    private NestedScrollView mScrollView;
    private FloatingActionButton mFab;
    private MenuItem mReloadMenuItem;

    private boolean isFabShow;
    private boolean isFabAniming;

    public static void start(Context context, String url)
    {
        Intent starter = new Intent(context, WebViewActivity.class);
        starter.putExtra(KEY_URL, url);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        mScrollView.setOnScrollChangeListener(this);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mWebView = (SafetyWebView) findViewById(R.id.webView);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new ViewClient());

        String url = getIntent().getStringExtra(KEY_URL);
        mWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mWebView.clearHistory();
        mWebView.removeAllViews();
        mWebView.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        mReloadMenuItem = menu.findItem(R.id.menuRefresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case android.R.id.home:
            finish();
            return true;
        case R.id.menuRefresh:
            mWebView.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if (mWebView.canGoBack())
        {
            mWebView.goBack();
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.fab:
            mScrollView.smoothScrollTo(0, 0);
            break;
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
    {
        int dy = scrollY - oldScrollY;
        if (dy > 0 && ! isFabShow && !isFabAniming)
        {
            isFabShow = true;
            isFabAniming = true;
            ViewCompat.animate(mFab).alpha(1f).setDuration(500).withLayer().withEndAction(new Runnable()
            {
                @Override
                public void run()
                {
                    isFabAniming = false;
                }
            }).start();
        } else if (dy < 0 && isFabShow && !isFabAniming && scrollY == 0)
        {
            isFabAniming = true;
            isFabShow = false;
            ViewCompat.animate(mFab).alpha(0f).setDuration(500).withLayer().withEndAction(new Runnable()
            {
                @Override
                public void run()
                {
                    isFabAniming = false;
                }
            }).start();
        }
    }

    class ChromeClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress)
        {
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100)
            {
                mProgressBar.setVisibility(View.GONE);
                if (mReloadMenuItem != null)
                {
                    mReloadMenuItem.setVisible(true);
                }
            }
        }
    }

    class ViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            mProgressBar.setVisibility(View.VISIBLE);
            if (mReloadMenuItem != null)
            {
                mReloadMenuItem.setVisible(false);
            }
        }
    }
}
