package com.xinzy.essence.api.impl;

import android.support.annotation.Nullable;

import com.xinzy.essence.api.ApiCallback;
import com.xinzy.essence.api.DayServiceApi;
import com.xinzy.essence.api.retrofit.DayService;
import com.xinzy.essence.http.HttpUtil;
import com.xinzy.essence.model.DayType;
import com.xinzy.essence.util.EssenceException;
import com.xinzy.essence.util.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Xinzy on 2017-01-19.
 */
public class DayServiceApiRetrofitImp implements DayServiceApi<DayType>
{
    @Override
    public void day(int year, int month, int day, @Nullable final ApiCallback<DayType> callback)
    {
        Retrofit retrofit = HttpUtil.getRetrofitInstance();
        DayService service = retrofit.create(DayService.class);
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
