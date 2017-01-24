package com.xinzy.essence.util;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.xinzy.essence.R;

/**
 * Created by Xinzy on 2017-01-24.
 */
public class AnimUtils
{

    public static void alphaHide(@NonNull View view)
    {
        Preconditions.checkNull(view);

        Animation anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.alpha_hide);
        view.startAnimation(anim);
    }

    public static void alphaShow(@NonNull View view)
    {
        Preconditions.checkNull(view);

        Animation anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.alpha_show);
        view.startAnimation(anim);
    }
}
