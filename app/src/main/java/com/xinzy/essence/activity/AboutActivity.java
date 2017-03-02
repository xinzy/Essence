package com.xinzy.essence.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xinzy.essence.R;
import com.xinzy.essence.base.BaseActivity;
import com.xinzy.essence.router.RouterPath;

@Route(path = RouterPath.ROUTER_ABOUT)
public class AboutActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void onContainerClicked(View v)
    {
        finish();
    }
}
