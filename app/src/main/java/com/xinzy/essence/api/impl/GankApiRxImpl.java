package com.xinzy.essence.api.impl;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

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
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
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
            public void onCompleted() {}

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
                    callback.onSuccess(data == null ? null : data.getResults());
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
            public void onCompleted() {}

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

    @Override
    public void search(String keyword, String category, int count, int page, @Nullable final ApiCallback<List<Essence>> callback)
    {
        GankService service = HttpUtil.getRxRetrofitInstance().create(GankService.class);
        Observable<ListSimple<Essence>> observable = service.search(keyword, category, count, page);
        if (callback != null) callback.onStart();
        Subscription subscription = observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ListSimple<Essence>>()
        {
            @Override
            public void onCompleted() {}

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
                    callback.onSuccess(data == null ? null : data.getResults());
                }
            }
        });
        RxApiManager.getInstance().add(keyword, subscription);
    }

    @Override
    public void cancelSearch(String tag)
    {
        RxApiManager.getInstance().cancel(tag);
    }

    private static class RxApiManager implements ApiManager<Subscription>
    {
        private static RxApiManager sInstance;

        private Map<String, Subscription> mSubscriptions;

        static RxApiManager getInstance()
        {
            if (sInstance == null)
            {
                synchronized (RxApiManager.class)
                {
                    if (sInstance == null)
                    {
                        sInstance = new RxApiManager();
                    }
                }
            }

            return sInstance;
        }

        private RxApiManager()
        {
            mSubscriptions = new ArrayMap<>();
        }

        @Override
        public void add(String tag, Subscription subscription)
        {
            mSubscriptions.put(tag, subscription);
        }

        @Override
        public void cancel(String tag)
        {
            final Subscription subscription = mSubscriptions.get(tag);
            if (subscription != null)
            {
                subscription.unsubscribe();
                remove(tag);
            }
        }

        @Override
        public void remove(String tag)
        {
            mSubscriptions.remove(tag);
        }

        @Override
        public void clear()
        {
            mSubscriptions.clear();
        }
    }
}
