package com.xinzy.essence.presenter;

import android.support.annotation.Nullable;

import com.xinzy.essence.base.BasePresenter;

/**
 * Created by xinzy on 17/1/16.
 */

public interface MainPresenter extends BasePresenter
{
    void loading(@Nullable String category, boolean refresh);
}
