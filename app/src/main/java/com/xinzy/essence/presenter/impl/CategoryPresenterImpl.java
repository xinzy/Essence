package com.xinzy.essence.presenter.impl;

import android.support.annotation.NonNull;

import com.xinzy.essence.api.ApiCallback;
import com.xinzy.essence.api.ListServiceApi;
import com.xinzy.essence.api.impl.ListServiceApiRetrofitImpl;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.CategoryPresenter;
import com.xinzy.essence.util.EssenceException;
import com.xinzy.essence.util.Preconditions;
import com.xinzy.essence.view.CategoryView;

import java.util.List;

import static com.xinzy.essence.util.Macro.PER_PAGE;

/**
 * Created by xinzy on 17/1/17.
 */
public class CategoryPresenterImpl implements CategoryPresenter
{
    private CategoryView mView;

    private String                        mCategory;
    private ListServiceApi<List<Essence>> mServiceApi;

    private int mPage = 1;
    private boolean isLoading;

    public CategoryPresenterImpl(@NonNull CategoryView view, String category)
    {
        mView = Preconditions.checkNull(view);
        mCategory = Preconditions.checkNull(category);
        mServiceApi = new ListServiceApiRetrofitImpl(mCategory);
    }

    @Override
    public void loading(final boolean refresh)
    {
        if (isLoading) return;
        isLoading = true;
        if (refresh) mPage = 1;

        mServiceApi.category(PER_PAGE, mPage, new ApiCallback<List<Essence>>() {
            @Override
            public void onStart()
            {
                if (refresh) mView.showRefresh();
            }

            @Override
            public void onSuccess(List<Essence> essences)
            {
                mView.setData(essences, refresh);
                mView.hideRefresh();
                mPage ++;
                isLoading = false;
            }

            @Override
            public void onFailure(EssenceException e)
            {
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
