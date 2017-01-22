package com.xinzy.essence.widget.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinzy.essence.util.Preconditions;

import java.util.Collection;
import java.util.List;

/**
 * Created by Xinzy on 2017-01-17.
 */
public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH>
{
    private final Object mLock = new Object();
    protected List<T> mLists;
    private LayoutInflater mInflater;
    protected Object mTag;

    public BaseRecyclerAdapter(@NonNull List<T> lists)
    {
        this.mLists = Preconditions.checkNull(lists);
    }

    @LayoutRes
    protected abstract int getItemLayout(int viewType);

    protected abstract VH onCreateViewHolder(@NonNull View convertView);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (mInflater == null)
        {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        return onCreateViewHolder(mInflater.inflate(getItemLayout(viewType), parent, false));
    }

    @Override
    public int getItemCount()
    {
        return mLists.size();
    }

    public void setTag(Object tag)
    {
        this.mTag = tag;
    }

    public void add(T t)
    {
        if (t != null)
        {
            synchronized (mLock)
            {
                int size = mLists.size();
                mLists.add(t);
                notifyItemInserted(size);
            }
        }
    }

    public void addAll(Collection<T> ts)
    {
        if (ts != null && ts.size() > 0)
        {
            synchronized (mLock)
            {
                int count = ts.size();
                int size = mLists.size();
                mLists.addAll(ts);
                notifyItemRangeInserted(size, count);
            }
        }
    }

    public void clear()
    {
        synchronized (mLock)
        {
            mLists.clear();
            notifyDataSetChanged();
        }
    }

    public void remove(int position)
    {
        if (position >= 0 && position < mLists.size())
        {
            synchronized (mLock)
            {
                mLists.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public void replace(Collection<T> ts)
    {
        if (ts == null || ts.isEmpty())
        {
            clear();
        } else
        {
            synchronized (mLock)
            {
                mLists.clear();
                mLists.addAll(ts);
                notifyDataSetChanged();
            }
        }
    }
}
