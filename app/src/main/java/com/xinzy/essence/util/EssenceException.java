package com.xinzy.essence.util;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by xinzy on 17/1/18.
 */
public class EssenceException extends Exception
{
    public EssenceException()
    {
    }

    public EssenceException(String message)
    {
        super(message);
    }

    public EssenceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EssenceException(Throwable cause)
    {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public EssenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
