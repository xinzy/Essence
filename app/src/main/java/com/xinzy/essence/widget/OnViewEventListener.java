package com.xinzy.essence.widget;

import android.view.View;

/**
 * Created by xinzy on 17/1/21.
 */

public interface OnViewEventListener
{
    void onViewEvent(View view, short event, Object... args);
}
