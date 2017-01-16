package com.xinzy.essence.base;

import android.support.annotation.NonNull;

/**
 * Created by xinzy on 17/1/16.
 */
public interface BaseView<T>
{

    void setPresenter(@NonNull T presenter);
}
