package com.xinzy.essence;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by xinzy on 17/1/16.
 *
 */
public class EssenceApplication extends TinkerApplication
{
    private static EssenceApplication instance;

    public EssenceApplication()
    {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.xinzy.essence.TinkerDelegateApplication");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;
    }

    public static EssenceApplication getInstance()
    {
        return instance;
    }
}
