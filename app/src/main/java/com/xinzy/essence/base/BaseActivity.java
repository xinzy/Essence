package com.xinzy.essence.base;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by xinzy on 17/1/16.
 */
public class BaseActivity extends AppCompatActivity
{
    private static final String KEY_NIGHT_MODE = "nightMode";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        AppCompatDelegate.setDefaultNightMode(isNightMode() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
    }

    protected boolean isNightMode()
    {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_NIGHT_MODE, false);
    }

    protected void setNightMode(boolean isNightMode)
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(KEY_NIGHT_MODE, isNightMode).apply();
    }
}
