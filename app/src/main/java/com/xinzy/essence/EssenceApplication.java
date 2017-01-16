package com.xinzy.essence;

import android.app.Application;

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
    }

    public static EssenceApplication getInstance()
    {
        return instance;
    }
}
