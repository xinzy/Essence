package com.xinzy.essence.api;

import android.support.annotation.Nullable;

/**
 * Created by Xinzy on 2017-01-19.
 */
public interface DayServiceApi<T>
{
    void day(int year, int month, int day, @Nullable ApiCallback<T> call);
}
