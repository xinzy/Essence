package com.xinzy.essence.presenter.impl;

import android.support.annotation.NonNull;

import com.xinzy.essence.api.retrofit.ListService;
import com.xinzy.essence.http.HttpUtil;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.model.ListSimple;
import com.xinzy.essence.presenter.CategoryPresenter;
import com.xinzy.essence.util.L;
import com.xinzy.essence.util.Macro;
import com.xinzy.essence.util.Preconditions;
import com.xinzy.essence.view.CategoryView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by xinzy on 17/1/17.
 */

public class CategoryPresenterImpl implements CategoryPresenter
{
    private CategoryView mView;

    private String mCategory;
    private int mPage = 1;
    private boolean isLoading;

    public CategoryPresenterImpl(@NonNull CategoryView view, String category)
    {
        mView = Preconditions.checkNull(view);
        mCategory = category;
    }

    @Override
    public void loading(boolean refresh)
    {
        if (isLoading) return;
        isLoading = true;
        if (refresh)
        {
            mPage = 1;
            mView.showRefresh();
        }

        Retrofit                  retrofit = HttpUtil.getRetrofitInstance();
        ListService               service  = retrofit.create(ListService.class);
        Call<ListSimple<Essence>> call     = service.category(mCategory, Macro.PER_PAGE, mPage);
        call.enqueue(new Callback<ListSimple<Essence>>() {
            @Override
            public void onResponse(Call<ListSimple<Essence>> call, Response<ListSimple<Essence>> response)
            {
                L.d("load success " + response.body());
                mView.hideRefresh();
                mView.setData(response.body().getResults(), mPage == 1);
                mPage ++;
                isLoading = false;
            }

            @Override
            public void onFailure(Call<ListSimple<Essence>> call, Throwable t)
            {
                L.e("loading error", t);
                mView.hideRefresh();
                isLoading = false;
            }
        });
    }

    @Override
    public void start()
    {
        loading(true);
    }
}
