package com.xinzy.essence.view;

import android.widget.ImageView;

import com.xinzy.essence.base.BaseView;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.MainPresenter;

import java.util.List;

/**
 * Created by xinzy on 17/1/16.
 */

public interface MainView extends BaseView<MainPresenter>
{
    void showRefresh();

    void hideRefresh();

    void setData(List<Essence> data, boolean refresh);

    void enter(int year, int month, int day);

    public interface OnItemClickListener
    {
        void onImageClick(ImageView img, Essence essence);

        void onTextClick(Essence essence);
    }
}
