package com.xinzy.essence.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.Method;

/**
 * Created by Xinzy on 2017-01-18.
 */
public class SafetyWebView extends WebView
{
    private OnScrollListener mOnScrollListener;

    public SafetyWebView(Context context)
    {
        super(context);
        init();
    }

    public SafetyWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SafetyWebView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SafetyWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init()
    {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);

        setVerticalScrollBarEnabled(true);
        setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");
    }

    public void setNightMode(ClassLoader loader)
    {
        try
        {
            Class cls = loader.loadClass("android.webkit.WebSettingsClassic");
            Method md = cls.getMethod("setProperty", String.class, String.class);
            md.invoke(getSettings(), "inverted", "true");
            md.invoke(getSettings(), "inverted_contrast", "1");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollListener != null)
        {
            mOnScrollListener.onScroll(l - oldl, t - oldt);
        }
    }

    public void onDestory()
    {
        stopLoading();
        removeAllViews();
        destroy();
    }

    public void setOnScrollListener(OnScrollListener l)
    {
        this.mOnScrollListener = l;
    }

    public interface OnScrollListener
    {
        void onScroll(int dx, int dy);
    }
}
