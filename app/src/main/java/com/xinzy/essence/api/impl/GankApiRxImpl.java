package com.xinzy.essence.api.impl;

import android.support.annotation.Nullable;

import com.xinzy.essence.api.ApiCallback;
import com.xinzy.essence.api.GankApi;
import com.xinzy.essence.api.rx.GankService;
import com.xinzy.essence.http.HttpUtil;
import com.xinzy.essence.model.DayType;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.model.ListSimple;
import com.xinzy.essence.util.EssenceException;
import com.xinzy.essence.util.L;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Xinzy on 2017-01-22.
 */
public class GankApiRxImpl implements GankApi
{
    @Override
    public void category(String category, int count, int page, @Nullable final ApiCallback<List<Essence>> callback)
    {
        GankService service = HttpUtil.getRxRetrofitInstance().create(GankService.class);
        Observable<ListSimple<Essence>> observable = service.category(category, count, page);
        if (callback != null) callback.onStart();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ListSimple<Essence>>()
        {
            @Override
            public void onCompleted()
            {}

            @Override
            public void onError(Throwable e)
            {
                L.e("request failure", e);
                if (callback != null)
                {
                    callback.onFailure(new EssenceException(e));
                }
            }

            @Override
            public void onNext(ListSimple<Essence> data)
            {
                L.d("request success");
                if (callback != null)
                {
                    callback.onSuccess(data.getResults());
                }
            }
        });
    }

    @Override
    public void day(int year, int month, int day, @Nullable final ApiCallback<DayType> callback)
    {
        GankService service = HttpUtil.getRxRetrofitInstance().create(GankService.class);
        Observable<DayType> observable = service.day(year, month, day);
        if (callback != null) callback.onStart();
        observable.subscribeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<DayType>()
        {
            @Override
            public void onCompleted()
            {}

            @Override
            public void onError(Throwable e)
            {
                L.e("request failure", e);
                if (callback != null)
                {
                    callback.onFailure(new EssenceException(e));
                }
            }

            @Override
            public void onNext(DayType dayType)
            {
                L.d("request success");
                if (callback != null)
                {
                    callback.onSuccess(dayType);
                }
            }
        });
    }
}
