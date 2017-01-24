package com.xinzy.essence.presenter.impl;

import android.support.annotation.NonNull;

import com.xinzy.essence.api.ApiCallback;
import com.xinzy.essence.api.GankApi;
import com.xinzy.essence.api.impl.GankApiRxImpl;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.MainPresenter;
import com.xinzy.essence.util.EssenceException;
import com.xinzy.essence.util.L;
import com.xinzy.essence.util.Macro;
import com.xinzy.essence.util.Preconditions;
import com.xinzy.essence.view.MainView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by xinzy on 17/1/16.
 */
public class MainPresenterImpl implements MainPresenter
{
    private static final int PER_PAGE = Macro.PER_PAGE;

    private MainView mMainView;
    private String mCategory;
    private GankApi mGankApi;

    private int page = 1;
    private boolean isLoading;

    public MainPresenterImpl(@NonNull MainView view, @NonNull String category)
    {
        this.mMainView = Preconditions.checkNotNull(view);
        this.mCategory = Preconditions.checkNotNull(category);
        mGankApi = new GankApiRxImpl();
    }

    @Override
    public void loading(final boolean refresh)
    {
        if (isLoading) return;
        isLoading = true;

        if (refresh) page = 1;

        L.v("start loading page = " + page);
        mGankApi.category(mCategory, PER_PAGE, page, new ApiCallback<List<Essence>>() {
            @Override
            public void onStart()
            {
                if (refresh) mMainView.showRefresh();
            }

            @Override
            public void onSuccess(List<Essence> essences)
            {
                mMainView.setData(essences, refresh);
                mMainView.hideRefresh();
                page ++;
                isLoading = false;
            }

            @Override
            public void onFailure(EssenceException e)
            {
                mMainView.hideRefresh();
                isLoading = false;
            }
        });
    }

    @Override
    public void start()
    {
        loading(true);
    }

    @Override
    public void onTextClick(Essence essence)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(essence.getCreatedAt());
        mMainView.enter(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }
}
