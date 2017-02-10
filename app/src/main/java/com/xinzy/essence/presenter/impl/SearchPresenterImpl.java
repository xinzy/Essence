package com.xinzy.essence.presenter.impl;

import android.text.TextUtils;

import com.xinzy.essence.api.ApiCallback;
import com.xinzy.essence.api.GankApi;
import com.xinzy.essence.api.impl.GankApiRxImpl;
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
    private String mKeyword;
    private String mCategory;
    private int mPage = 1;
    private boolean isLoadingMore;

    public SearchPresenterImpl(SearchView view)
    {
        this.mSearchView = Preconditions.checkNotNull(view);
        mGankApi = new GankApiRxImpl();
    }

    @Override
    public void search(String keyword, String category)
    {
        mPage = 1;
        mLastKeyword = mKeyword = keyword;
        mCategory = category;
        mSearchView.showLoading(true);
        doSearch();
    }

    @Override
    public void loadMore()
    {
        if (!isLoadingMore)
        {
            isLoadingMore = true;
            mPage++;
            doSearch();
        }
    }

    private void doSearch()
    {
        final boolean isFirstPage = mPage == 1;
        mGankApi.search(mKeyword, mCategory, Macro.PER_PAGE, mPage, new ApiCallback<List<Essence>>()
        {
            @Override
            public void onStart()
            {
                if (isFirstPage)
                {
                    mSearchView.showLoading(true);
                }
            }

            @Override
            public void onSuccess(List<Essence> essences)
            {
                L.d("search onSuccess");
                mSearchView.showLoading(false);
                mSearchView.setData(essences, isFirstPage);
                if (isLoadingMore) isLoadingMore = false;
            }

            @Override
            public void onFailure(EssenceException e)
            {
                if (isLoadingMore)
                {
                    isLoadingMore = false;
                    mPage --;
                }
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
