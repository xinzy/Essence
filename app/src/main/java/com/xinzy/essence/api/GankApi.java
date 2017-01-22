package com.xinzy.essence.api;

import android.support.annotation.Nullable;

import com.xinzy.essence.model.DayType;
import com.xinzy.essence.model.Essence;

import java.util.List;

/**
 * Created by Xinzy on 2017-01-22.
 */
public interface GankApi
{
    void category(String category, int count, int page, @Nullable ApiCallback<List<Essence>> callback);

    void day(int year, int month, int day, @Nullable ApiCallback<DayType> callback);
}
