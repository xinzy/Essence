package com.xinzy.essence.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.xinzy.essence.R;
import com.xinzy.essence.adapter.holder.EssenceHolder;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.widget.OnViewEventListener;
import com.xinzy.essence.widget.adapter.BaseRecycleAdapter;

import java.util.List;

/**
 * Created by xinzy on 17/1/17.
 */
public class CategoryAdapter extends BaseRecycleAdapter<EssenceHolder, Essence>
{
    private OnViewEventListener mOnViewEventListener;

    public CategoryAdapter(@NonNull List<Essence> lists)
    {
        super(lists);
    }

    public void setOnViewEventListener(OnViewEventListener l)
    {
        this.mOnViewEventListener = l;
    }

    @Override
    protected int getItemLayout(int viewType)
    {
        return R.layout.item_essence;
    }

    @Override
    protected EssenceHolder onCreateViewHolder(@NonNull View convertView)
    {
        EssenceHolder holder = new EssenceHolder(convertView);
        holder.setOnViewEventListener(mOnViewEventListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(EssenceHolder holder, int position)
    {
        holder.convert(mLists.get(position));
    }
}
