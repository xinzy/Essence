package com.xinzy.essence.api;

import com.xinzy.essence.util.EssenceException;

/**
 * Created by xinzy on 17/1/18.
 */
public interface ApiCallback<T>
{

    void onStart();

    void onSuccess(T t);

    void onFailure(EssenceException e);
}
