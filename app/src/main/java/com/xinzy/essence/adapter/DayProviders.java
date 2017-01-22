package com.xinzy.essence.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinzy.essence.R;
import com.xinzy.essence.adapter.holder.EssenceHolder;
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
    }

    public static class EssenceProvider extends MultiTypeAdapter.ItemViewProvider<Essence, EssenceHolder>
    {
        @Override
        public EssenceHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            EssenceHolder holder = new EssenceHolder(inflater.inflate(R.layout.item_essence, parent, false));
            holder.setOnViewEventListener(mOnViewEventListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(EssenceHolder holder, Essence data)
        {
            holder.convert(data);
        }
    }

    static class CategoryHolder extends MultiTypeAdapter.ViewHolder<String>
    {
        private TextView mTitleText;
        CategoryHolder(View itemView)
        {
            super(itemView);
            mTitleText = get(R.id.itemTitle);
        }

        @Override
        public void convert(String data)
        {
            mTitleText.setText(data);
        }
    }
}
