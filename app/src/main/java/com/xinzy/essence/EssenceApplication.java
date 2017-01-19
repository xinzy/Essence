package com.xinzy.essence;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;
import com.xinzy.essence.http.HttpUtil;
import com.xinzy.essence.http.PicassoDownloader;

/**
 * Created by xinzy on 17/1/16.
 */
public class EssenceApplication extends Application
{
    private static EssenceApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;
        Picasso.setSingletonInstance(new Picasso.Builder(this).downloader(new PicassoDownloader(HttpUtil.getPicassoClient())).build());
        Stetho.initializeWithDefaults(this);
    }

    public static EssenceApplication getInstance()
    {
        return instance;
    }
}
