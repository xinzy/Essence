package com.xinzy.essence.view;

import com.xinzy.essence.base.BaseView;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.CategoryPresenter;

import java.util.List;

/**
 * Created by xinzy on 17/1/17.
 */
public interface CategoryView extends BaseView<CategoryPresenter>
{

    void showRefresh();

    void hideRefresh();

    void setData(List<Essence> data, boolean refresh);
}
