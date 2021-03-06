package com.xinzy.essence.util;

import android.support.annotation.NonNull;

/**
 * Created by xinzy on 17/1/16.
 */
public class Preconditions
{

    public static <T> T checkNotNull(T t)
    {
        return checkNotNull(t, "arg cannot be null");
    }

    public static <T> T checkNotNull(T t, @NonNull String msg)
    {
        if (t == null)
        {
            throw new NullPointerException(msg);
        }

        return t;
    }

}
