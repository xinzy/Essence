package com.xinzy.essence.api.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xinzy.essence.api.ApiCallback;
import com.xinzy.essence.api.ListServiceApi;
import com.xinzy.essence.api.retrofit.ListService;
import com.xinzy.essence.http.HttpUtil;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.model.ListSimple;
import com.xinzy.essence.util.EssenceException;
import com.xinzy.essence.util.L;
import com.xinzy.essence.util.Preconditions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xinzy on 17/1/18.
 */
public class ListServiceApiRetrofitImpl implements ListServiceApi<List<Essence>>
{
    private String mCategory;

    public ListServiceApiRetrofitImpl(@NonNull String category)
    {
        this.mCategory = Preconditions.checkNull(category);
    }

    @Override
    public void category(int count, int page, @Nullable final ApiCallback<List<Essence>> callback)
    {
        ListService               service = HttpUtil.getRetrofitInstance().create(ListService.class);
        Call<ListSimple<Essence>> call    = service.category(mCategory, count, page);

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
}
