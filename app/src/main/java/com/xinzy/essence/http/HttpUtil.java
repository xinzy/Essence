package com.xinzy.essence.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xinzy.essence.util.FileUtils;
import com.xinzy.essence.util.Macro;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xinzy on 17/1/16.
 */
public class HttpUtil
{
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    private static Retrofit mRxRetrofit;

    private static OkHttpClient getClientInstance()
    {
        if (mOkHttpClient == null)
        {
            synchronized (HttpUtil.class)
            {
                if (mOkHttpClient == null)
                {
                    mOkHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
                }
            }
        }

        return mOkHttpClient;
    }

    public static OkHttpClient getPicassoClient()
    {
        return new OkHttpClient.Builder().cache(new Cache(FileUtils.getPicassoCacheDir(), Macro.IMAGE_CACHE_SIZE)).build();
    }

    public static Retrofit getRetrofitInstance()
    {
        if (mRetrofit == null)
        {
            synchronized (HttpUtil.class)
            {
                if (mRetrofit == null)
                {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

                    mRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).client(getClientInstance())
                            .baseUrl(ApiPath.BASE_URL).build();
                }
            }
        }

        return mRetrofit;
    }

    public static Retrofit getRxRetrofitInstance()
    {
        if (mRxRetrofit == null)
        {
            synchronized (HttpUtil.class)
            {
                if (mRxRetrofit == null)
                {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

                    mRxRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).client(getClientInstance())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).baseUrl(ApiPath.BASE_URL).build();
                }
            }
        }

        return mRxRetrofit;
    }

}
