package com.xinzy.essence.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xinzy.essence.R;
import com.xinzy.essence.base.BaseActivity;

public class AboutActivity extends BaseActivity
{

    public static void start(Context context)
    {
        Intent starter = new Intent(context, AboutActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
