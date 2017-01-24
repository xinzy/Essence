package com.xinzy.essence.presenter.impl;

import com.xinzy.essence.api.ApiCallback;
import com.xinzy.essence.api.GankApi;
import com.xinzy.essence.api.impl.GankApiRetrofitImpl;
import com.xinzy.essence.model.DayType;
import com.xinzy.essence.presenter.DayPresenter;
import com.xinzy.essence.util.EssenceException;
import com.xinzy.essence.util.Preconditions;
import com.xinzy.essence.view.DayView;

/**
 * Created by Xinzy on 2017-01-19.
 */
public class DayPresenterImpl implements DayPresenter
{
    private DayView mView;
    private GankApi mGankApi;

    private int year, month, day;
    private boolean isLoading;

    public DayPresenterImpl(DayView view, int year, int month, int day)
    {
        this.mView = Preconditions.checkNotNull(view);
        this.year = year;
        this.month = month;
        this.day = day;
        mGankApi = new GankApiRetrofitImpl();
    }

    @Override
    public void start()
    {
        if (isLoading) return;
        isLoading = true;
        mGankApi.day(year, month, day, new ApiCallback<DayType>()
        {
            @Override
            public void onStart()
            {
                mView.showRefresh();
            }

            @Override
            public void onSuccess(DayType dayType)
            {
                isLoading = false;
                mView.hideRefresh();
                mView.setData(dayType);
            }

            @Override
            public void onFailure(EssenceException e)
            {
                isLoading = false;
                mView.hideRefresh();
            }
        });
    }
}
