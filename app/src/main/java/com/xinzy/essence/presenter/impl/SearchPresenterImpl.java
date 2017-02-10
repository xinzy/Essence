package com.xinzy.essence.presenter.impl;

import android.text.TextUtils;

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

    private String mLastKeyword;
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
        mLastKeyword = keyword;
        mSearchView.showLoading(true);
        mGankApi.search(keyword, category, Macro.PER_PAGE, mPage, new ApiCallback<List<Essence>>()
        {
            @Override
            public void onStart()
            {
                mSearchView.showLoading(true);
            }

            @Override
            public void onSuccess(List<Essence> essences)
            {
                L.d("search onSuccess");
                mSearchView.showLoading(false);
                mSearchView.setData(essences, false);
            }

            @Override
            public void onFailure(EssenceException e)
            {
//                mSearchView.showLoading(false);
            }
        });
    }

    @Override
    public void cancel()
    {
        if (!TextUtils.isEmpty(mLastKeyword))
        {
            mGankApi.cancelSearch(mLastKeyword);
        }
    }

    @Override
    public void start()
    {
    }
}
