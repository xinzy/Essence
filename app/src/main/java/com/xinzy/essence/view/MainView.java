package com.xinzy.essence.view;

import com.xinzy.essence.base.BaseView;
import com.xinzy.essence.presenter.MainPresenter;

/**
 * Created by xinzy on 17/1/16.
 */

public interface MainView extends BaseView<MainPresenter>
{
    void showRefresh();

    void closeRefresh();
}
