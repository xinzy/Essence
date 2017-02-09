package com.xinzy.essence.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.xinzy.essence.widget.decoration.DecorationEntrust;

/**
 * Created by Xinzy on 2017-01-18.
 */
public class InternalRecyclerView extends RecyclerView
{
    public InternalRecyclerView(Context context)
    {
        this(context, null);
    }

    public InternalRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public InternalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
    }

    public static class InternalScrollListener extends OnScrollListener
    {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState)
        {
            final LayoutManager manager = recyclerView.getLayoutManager();
            if (manager.getItemCount() > 0)
            {
                final View lastChildView = manager.getChildAt(manager.getChildCount() - 1);
                final View firstChildView = manager.getChildAt(0);
                if (lastChildView.getBottom() >= recyclerView.getBottom() - recyclerView.getPaddingBottom() - 1
                        && manager.getPosition(lastChildView) == manager.getItemCount() - 1)
                {
                    onScrollToBottom(recyclerView, newState);
                } else if (firstChildView.getTop() <= recyclerView.getTop() + recyclerView.getPaddingTop() + 1
                        && manager.getPosition(firstChildView) == 0)
                {
                    onScrollToTop(recyclerView, newState);
                }
            }
        }

        public void onScrollToTop(RecyclerView view, int state)
        {
        }

        public void onScrollToBottom(RecyclerView view, int state)
        {
        }
    }

    public static class SpacesItemDecoration extends ItemDecoration
    {
        private DecorationEntrust mEntrust;
        private int mColor;
        private int leftRight;
        private int topBottom;

        public SpacesItemDecoration(int leftRight, int topBottom)
        {
            this(leftRight, topBottom, 0);
        }

        public SpacesItemDecoration(int leftRight, int topBottom, int mColor)
        {
            this.leftRight = leftRight;
            this.topBottom = topBottom;
            this.mColor = mColor;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state)
        {
            if (mEntrust == null)
            {
                mEntrust = DecorationEntrust.get(parent.getLayoutManager(), leftRight, topBottom, mColor);
            }
            mEntrust.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state)
        {
            if (mEntrust == null)
            {
                mEntrust = DecorationEntrust.get(parent.getLayoutManager(), leftRight, topBottom, mColor);
            }
            mEntrust.getItemOffsets(outRect, view, parent, state);
        }
    }
}
