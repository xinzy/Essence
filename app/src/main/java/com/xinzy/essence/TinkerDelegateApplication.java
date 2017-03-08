package com.xinzy.essence;

import android.app.Application;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.xinzy.essence.http.HttpUtil;
import com.xinzy.essence.http.PicassoDownloader;
import com.xinzy.essence.util.Macro;

/**
 * Created by Xinzy on 2017-03-08.
 */
//@DefaultLifeCycle(application = "com.xinzy.essence.EssenceApplication", flags = ShareConstants.TINKER_ENABLE_ALL)
public class TinkerDelegateApplication extends DefaultApplicationLike
{
    private Application mApp;

    public TinkerDelegateApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent)
    {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
        mApp = application;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Picasso.setSingletonInstance(new Picasso.Builder(mApp).downloader(new PicassoDownloader(HttpUtil.getPicassoClient())).build());
        Stetho.initializeWithDefaults(mApp);

        if (Macro.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(mApp);
    }
}
