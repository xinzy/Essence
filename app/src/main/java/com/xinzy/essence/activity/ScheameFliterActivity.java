package com.xinzy.essence.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

public class ScheameFliterActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        if (uri != null)
        {
            ARouter.getInstance().build(uri).navigation();
        }
        finish();
    }
}
