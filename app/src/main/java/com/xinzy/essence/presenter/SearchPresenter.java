package com.xinzy.essence.presenter;

import com.xinzy.essence.base.BasePresenter;

/**
 * Created by Xinzy on 2017-02-07.
 *
 */
public interface SearchPresenter extends BasePresenter
{
    void search(String keyword, String category);

    void loadMore();

    void cancel();
}
