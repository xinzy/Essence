package com.xinzy.essence.view;

import com.xinzy.essence.base.BaseView;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.SearchPresenter;

import java.util.List;

/**
 * Created by Xinzy on 2017-02-07.
 *
 */
public interface SearchView extends BaseView<SearchPresenter>
{
    void setData(List<Essence> data, boolean isAppend);
}
