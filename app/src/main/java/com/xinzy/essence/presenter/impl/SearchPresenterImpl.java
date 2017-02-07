package com.xinzy.essence.presenter.impl;

import com.xinzy.essence.api.ApiCallback;
import com.xinzy.essence.api.GankApi;
import com.xinzy.essence.api.impl.GankApiRetrofitImpl;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.SearchPresenter;
import com.xinzy.essence.util.EssenceException;
import com.xinzy.essence.util.L;
import com.xinzy.essence.util.Macro;
import com.xinzy.essence.util.Preconditions;
import com.xinzy.essence.view.SearchView;

import java.util.List;

/**
 * Created by Xinzy on 2017-02-07.
 *
 */
public class SearchPresenterImpl implements SearchPresenter
{
    private SearchView mSearchView;

    private GankApi mGankApi;
    private int mPage = 1;

    public SearchPresenterImpl(SearchView view)
    {
        this.mSearchView = Preconditions.checkNotNull(view);
        mGankApi = new GankApiRetrofitImpl();
    }

    @Override
    public void search(String keyword, String category)
    {
        L.d("search " + keyword);
        mPage = 1;
        mGankApi.search(keyword, category, mPage, Macro.PER_PAGE, new ApiCallback<List<Essence>>()
        {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(List<Essence> essences)
            {
                L.d("search onSuccess");
                mSearchView.setData(essences, false);
            }

            @Override
            public void onFailure(EssenceException e) {}
        });
    }

    @Override
    public void cancel()
    {
    }

    @Override
    public void start()
    {
    }
}
