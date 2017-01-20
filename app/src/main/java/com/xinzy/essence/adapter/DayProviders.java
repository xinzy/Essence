package com.xinzy.essence.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinzy.essence.R;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.widget.adapter.MultiTypeAdapter;

/**
 * Created by Xinzy on 2017-01-20.
 */
public class DayProviders
{

    public static class CategoryProvider extends MultiTypeAdapter.ItemViewProvider<String, CategoryHolder>
    {
        @Override
        public CategoryHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            return new CategoryHolder(inflater.inflate(R.layout.item_day_category, parent, false));
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, String data)
        {
            holder.setData(data);
        }
    }

    public static class TitleProvider extends MultiTypeAdapter.ItemViewProvider<Essence, TitleHolder>
    {
        public static final short EVENT_TITLE_CLICK = 0x100;

        @Override
        public TitleHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            return new TitleHolder(inflater.inflate(R.layout.item_day_title, parent, false), mOnViewEventListener);
        }

        @Override
        public void onBindViewHolder(TitleHolder holder, Essence data)
        {
            holder.setData(data);
        }
    }

    static class CategoryHolder extends RecyclerView.ViewHolder
    {
        private TextView mTitleText;
        CategoryHolder(View itemView)
        {
            super(itemView);
            mTitleText = (TextView) itemView.findViewById(R.id.itemTitle);
        }

        void setData(String data)
        {
            mTitleText.setText(data);
        }
    }

    static class TitleHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTitleText;

        private Essence mEssence;
        private MultiTypeAdapter.OnViewEventListener mOnViewEventListener;

        TitleHolder(View itemView, MultiTypeAdapter.OnViewEventListener onViewEventListener)
        {
            super(itemView);
            mOnViewEventListener = onViewEventListener;
            mTitleText = (TextView) itemView.findViewById(R.id.itemTitle);
            mTitleText.setOnClickListener(this);
        }

        void setData(Essence data)
        {
            mEssence = data;
            mTitleText.setText(data.getContent());
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.itemTitle:
                    if (mOnViewEventListener != null)
                    {
                        mOnViewEventListener.onViewEvent(mTitleText, TitleProvider.EVENT_TITLE_CLICK, mEssence);
                    }
                    break;
            }
        }
    }
}
