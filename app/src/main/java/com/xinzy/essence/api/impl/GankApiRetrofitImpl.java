package com.xinzy.essence.api.impl;

import android.support.annotation.Nullable;

import com.xinzy.essence.api.ApiCallback;
import com.xinzy.essence.api.GankApi;
import com.xinzy.essence.api.retrofit.GankService;
import com.xinzy.essence.http.HttpUtil;
import com.xinzy.essence.model.DayType;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.model.ListSimple;
import com.xinzy.essence.util.EssenceException;
import com.xinzy.essence.util.L;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xinzy on 2017-01-22.
 */
public class GankApiRetrofitImpl implements GankApi
{

    @Override
    public void category(String category, int count, int page, @Nullable final ApiCallback<List<Essence>> callback)
    {
        GankService service = HttpUtil.getRetrofitInstance().create(GankService.class);
        Call<ListSimple<Essence>> call    = service.category(category, count, page);

        if (callback != null) callback.onStart();
        call.enqueue(new Callback<ListSimple<Essence>>()
        {
            @Override
            public void onResponse(Call<ListSimple<Essence>> call, Response<ListSimple<Essence>> response)
            {
                ListSimple<Essence> data = response.body();
                L.d("request success " + data);
                if (callback != null) callback.onSuccess(data == null ? null : data.getResults());
            }

            @Override
            public void onFailure(Call<ListSimple<Essence>> call, Throwable t)
            {
                L.e("request failure", t);
                if (callback != null) callback.onFailure(new EssenceException(t));
            }
        });
    }

    @Override
    public void day(int year, int month, int day, @Nullable final ApiCallback<DayType> callback)
    {
        GankService service = HttpUtil.getRetrofitInstance().create(GankService.class);
        Call<DayType> call = service.day(year, month, day);
        if (callback != null) callback.onStart();
        call.enqueue(new Callback<DayType>()
        {
            @Override
            public void onResponse(Call<DayType> call, Response<DayType> response)
            {
                L.d("request success " + response.body());
                if (callback != null) callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DayType> call, Throwable t)
            {
                L.e("request failure", t);
                if (callback != null) callback.onFailure(new EssenceException(t));
            }
        });
    }
}
