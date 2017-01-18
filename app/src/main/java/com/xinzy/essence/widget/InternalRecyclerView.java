package com.xinzy.essence.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

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

        public void onScrollToTop(RecyclerView view, int state) {}

        public void onScrollToBottom(RecyclerView view, int state) {}
    }

    public static class LinearItemDecoration extends ItemDecoration
    {
        private int mOrientation;
        private Drawable mDivider;

        private static final int[] ATTRS = {android.R.attr.listDivider};

        public LinearItemDecoration(Context context, int orientation)
        {
            setOrientation(orientation);
            final TypedArray ta = context.obtainStyledAttributes(ATTRS);
            this.mDivider = ta.getDrawable(0);
            ta.recycle();
        }

        public void setOrientation(int orientation)
        {
            if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL)
            {
                throw new IllegalArgumentException("Wrong Argument orientation");
            }
            this.mOrientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state)
        {
            if (mOrientation == LinearLayoutManager.VERTICAL)
            {
                int left = parent.getPaddingLeft() + parent.getPaddingStart();
                int right = parent.getRight() - parent.getPaddingEnd() - parent.getPaddingRight();

                final int childrenCount = parent.getChildCount();
                for (int i = 0; i < childrenCount; i++)
                {
                    View child = parent.getChildAt(i);
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    int top = child.getBottom() + lp.bottomMargin;
                    int bottom = top + mDivider.getMinimumHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            } else
            {
                int top = parent.getPaddingTop();
                int bottom = parent.getHeight() - parent.getPaddingBottom();
                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++){
                    View child = parent.getChildAt(i);
                    LayoutParams params = (LayoutParams)child.getLayoutParams();
                    int left = child.getRight() + params.rightMargin;
                    int right = left + mDivider.getIntrinsicWidth();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state)
        {
            if (mOrientation == LinearLayoutManager.VERTICAL)
            {
                outRect.set(0, 0, mDivider.getMinimumHeight(), 0);
            } else
            {
                outRect.set(0, 0, 0, mDivider.getMinimumWidth());
            }
        }
    }
}
