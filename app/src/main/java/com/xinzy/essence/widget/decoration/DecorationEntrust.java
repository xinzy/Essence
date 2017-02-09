package com.xinzy.essence.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Xinzy on 2017-02-09.
 */
public abstract class DecorationEntrust
{
    Drawable mDivider;
    int leftRight;
    int topBottom;

    DecorationEntrust(int leftRight, int topBottom, int mColor)
    {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
        if (mColor != 0)
        {
            mDivider = new ColorDrawable(mColor);
        }
    }

    public static DecorationEntrust get(RecyclerView.LayoutManager manager, int leftRight, int topBottom, int color)
    {
        if (manager instanceof GridLayoutManager) {
            return new GridEntrust(leftRight, topBottom, color);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return new StaggeredGridEntrust(leftRight, topBottom, color);
        } else {//其他的都当做Linear来进行计算
            return new LinearEntrust(leftRight, topBottom, color);
        }
    }

    public abstract void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state);

    public abstract void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);
}
