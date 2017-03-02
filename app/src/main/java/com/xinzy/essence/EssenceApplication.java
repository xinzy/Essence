package com.xinzy.essence;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;
import com.xinzy.essence.http.HttpUtil;
import com.xinzy.essence.http.PicassoDownloader;
import com.xinzy.essence.util.Macro;

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

        if (Macro.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    public static EssenceApplication getInstance()
    {
        return instance;
    }
}
