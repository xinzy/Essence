package com.xinzy.essence.presenter;

import com.xinzy.essence.base.BasePresenter;
import com.xinzy.essence.model.Essence;

/**
 * Created by xinzy on 17/1/16.
 */

public interface MainPresenter extends BasePresenter
{
    void loading(boolean refresh);

    void onTextClick(Essence essence);
}
