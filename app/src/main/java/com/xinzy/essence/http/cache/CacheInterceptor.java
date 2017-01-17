package com.xinzy.essence.http.cache;

import android.text.TextUtils;

import com.xinzy.essence.util.Macro;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by xinzy on 17/1/17.
 */
public class CacheInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Response rawResponse = chain.proceed(chain.request());
        if (!TextUtils.isEmpty(rawResponse.header("Cache-Control")))
        {
            return rawResponse;
        }

        return rawResponse.newBuilder().addHeader("Cache-Control", "max-age=" + Macro.HTTP_CACHE_CONTROL_MAX_AGE).build();
    }
}
