package com.xinzy.essence.presenter.impl;

import android.support.annotation.NonNull;

import com.xinzy.essence.api.retrofit.ListService;
import com.xinzy.essence.http.HttpUtil;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.model.ListSimple;
import com.xinzy.essence.presenter.MainPresenter;
import com.xinzy.essence.util.L;
import com.xinzy.essence.util.Macro;
import com.xinzy.essence.util.Preconditions;
import com.xinzy.essence.view.MainView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by xinzy on 17/1/16.
 */

public class MainPresenterImpl implements MainPresenter
{
    private static final int PER_PAGE = Macro.PER_PAGE;

    private MainView mMainView;
    private String mCategory;

    private int page = 1;
    private boolean isLoading;

    public MainPresenterImpl(@NonNull MainView view, @NonNull String category)
    {
        this.mMainView = Preconditions.checkNull(view);
        this.mCategory = Preconditions.checkNull(category);
    }

    @Override
    public void loading(boolean refresh)
    {
        if (isLoading) return;
        isLoading = true;

        if (refresh)
        {
            mMainView.showRefresh();
            page = 1;
        }

        L.v("start loading page = " + page);
        Retrofit retrofit = HttpUtil.getRetrofitInstance();
        ListService listService = retrofit.create(ListService.class);
        listService.category(mCategory, PER_PAGE, page).enqueue(new Callback<ListSimple<Essence>>() {
            @Override
            public void onResponse(Call<ListSimple<Essence>> call, Response<ListSimple<Essence>> response)
            {
                L.d("result " + response.body());
                mMainView.setData(response.body().getResults(), page == 1);
                mMainView.closeRefresh();
                page ++;
                isLoading = false;
            }

            @Override
            public void onFailure(Call<ListSimple<Essence>> call, Throwable t)
            {
                L.e("load error", t);
                mMainView.closeRefresh();
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
