package com.xinzy.essence.api;

import android.support.annotation.Nullable;

/**
 * Created by xinzy on 17/1/18.
 */
public interface ListServiceApi<T>
{
    void category(int count, int page, @Nullable ApiCallback<T> callback);
}
