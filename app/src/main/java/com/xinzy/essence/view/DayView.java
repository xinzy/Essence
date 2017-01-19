package com.xinzy.essence.view;

import com.xinzy.essence.base.BaseView;
import com.xinzy.essence.model.DayType;
import com.xinzy.essence.presenter.DayPresenter;

/**
 * Created by Xinzy on 2017-01-19.
 */
public interface DayView extends BaseView<DayPresenter>
{
    void showRefresh();

    void hideRefresh();

    void setData(DayType data);
}
