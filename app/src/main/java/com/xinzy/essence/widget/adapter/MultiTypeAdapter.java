package com.xinzy.essence.widget.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinzy.essence.widget.OnViewEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Xinzy on 2017-01-20.
 */
public class MultiTypeAdapter extends RecyclerView.Adapter<MultiTypeAdapter.ViewHolder>
{
    private final Object mLock = new Object();

    private List<Object> mData;
    private TypePool mTypePool;
    private LayoutInflater mInflater;

    public MultiTypeAdapter()
    {
        this(new ArrayList<>());
    }

    public MultiTypeAdapter(@NonNull List<Object> data)
    {
        this.mData = data;
        mTypePool = new TypePool();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (mInflater == null)
        {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        ItemViewProvider provider = mTypePool.get(viewType);
        provider.setAdapter(this);
        return provider.onCreateViewHolder(mInflater, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Object data = mData.get(position);
        ItemViewProvider provider = mTypePool.get(data.getClass());
        provider.onBindViewHolder(holder, data);
    }

    @Override
    public int getItemViewType(int position)
    {
        return mTypePool.indexOf(mData.get(position).getClass());
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public MultiTypeAdapter registerProvider(Class<?> clazz, ItemViewProvider provider)
    {
        mTypePool.register(clazz, provider);
        return this;
    }

    public void add(Object t)
    {
        if (t != null)
        {
            synchronized (mLock)
            {
                int size = mData.size();
                mData.add(t);
                notifyItemInserted(size);
            }
        }
    }

    public void addAll(Collection<?> ts)
    {
        if (ts != null && ts.size() > 0)
        {
            synchronized (mLock)
            {
                int count = ts.size();
                int size = mData.size();
                mData.addAll(ts);
                notifyItemRangeInserted(size, count);
            }
        }
    }

    public void clear()
    {
        synchronized (mLock)
        {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void remove(int position)
    {
        if (position >= 0 && position < mData.size())
        {
            synchronized (mLock)
            {
                mData.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public void replace(Collection<?> ts)
    {
        if (ts == null || ts.isEmpty())
        {
            clear();
        } else
        {
            synchronized (mLock)
            {
                mData.clear();
                mData.addAll(ts);
                notifyDataSetChanged();
            }
        }
    }

    private class TypePool
    {
        private List<Class<?>> mTypes;
        private List<ItemViewProvider> mProviders;

        TypePool()
        {
            mTypes = new ArrayList<>();
            this.mProviders = new ArrayList<>();
        }

        void register(@NonNull Class<?> clazz, @NonNull ItemViewProvider provider)
        {
            if (mTypes.contains(clazz))
            {
                int index = mTypes.indexOf(clazz);
                mProviders.set(index, provider);
            } else
            {
                mTypes.add(clazz);
                mProviders.add(provider);
            }
        }

        int indexOf(Class<?> clazz)
        {
            int index = mTypes.indexOf(clazz);
            if (index >= 0)
            {
                return index;
            }

            for (int i = 0; i < mTypes.size(); i++)
            {
                if (mTypes.get(i).isAssignableFrom(clazz))
                {
                    return i;
                }
            }

            throw new RuntimeException(clazz + " has not register as a ItemViewProvider");
        }

        ItemViewProvider get(int position)
        {
            return mProviders.get(position);
        }

        ItemViewProvider get(Class<?> clazz)
        {
            return mProviders.get(indexOf(clazz));
        }
    }

    public static abstract class ItemViewProvider<T, VH extends ViewHolder<T>>
    {
        private   MultiTypeAdapter    mAdapter;
        protected OnViewEventListener mOnViewEventListener;

        void setAdapter(MultiTypeAdapter adapter)
        {
            this.mAdapter = adapter;
        }

        public void setOnViewEventListener(OnViewEventListener l)
        {
            this.mOnViewEventListener = l;
        }

        public MultiTypeAdapter getAdapter()
        {
            return mAdapter;
        }

        public abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent);

        public void onBindViewHolder(VH holder, T data)
        {
            holder.convert(data);
        }
    }

    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder
    {
        protected View mRootView;
        private SparseArray<View> mViews;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mRootView = itemView;
            mViews = new SparseArray<>();
        }

        public <V> V get(@IdRes int id)
        {
            View view = mViews.get(id);
            if (view == null)
            {
                view = mRootView.findViewById(id);
                if (view == null)
                {
                    throw new IllegalArgumentException("Wrong id");
                }
                mViews.put(id, view);
            }
            return (V) view;
        }

        public abstract void convert(T data);
    }
}
