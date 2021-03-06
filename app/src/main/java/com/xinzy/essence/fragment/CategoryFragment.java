package com.xinzy.essence.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.essence.R;
import com.xinzy.essence.activity.WebViewActivity;
import com.xinzy.essence.adapter.DayProviders;
import com.xinzy.essence.adapter.holder.EssenceHolder;
import com.xinzy.essence.base.BaseFragment;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.CategoryPresenter;
import com.xinzy.essence.presenter.impl.CategoryPresenterImpl;
import com.xinzy.essence.util.L;
import com.xinzy.essence.router.RouterPath;
import com.xinzy.essence.view.CategoryView;
import com.xinzy.essence.widget.InternalRecyclerView;
import com.xinzy.essence.widget.OnViewEventListener;
import com.xinzy.essence.widget.adapter.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinzy on 17/1/17.
 */
public class CategoryFragment extends BaseFragment implements CategoryView, SwipeRefreshLayout.OnRefreshListener,
        OnViewEventListener
{
    private static final String ARG_CATEGORY = "CATEGORY";

    private SwipeRefreshLayout mRefreshLayout;
    private MultiTypeAdapter mCategoryAdapter;

    private CategoryPresenter mPresenter;

    private String mCategory;
    private boolean mIsVisible;
    private boolean mIsInited;
    private boolean hasLoadOnce;

    public CategoryFragment()
    {
    }

    public static CategoryFragment newInstance(String category)
    {
        CategoryFragment fragment = new CategoryFragment();
        Bundle           args     = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mCategory = getArguments().getString(ARG_CATEGORY);
        mPresenter = new CategoryPresenterImpl(this, mCategory);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View     rootView = inflater.inflate(R.layout.fragment_category, container, false);
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.categoryRefreshLayout);
        mRefreshLayout.setOnRefreshListener(this);
        InternalRecyclerView recyclerView = (InternalRecyclerView) rootView.findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        int divideColor = ResourcesCompat.getColor(getResources(), R.color.colorDivide, getContext().getTheme());
        recyclerView.addItemDecoration(new InternalRecyclerView.SpacesItemDecoration(0, 2, divideColor));
        recyclerView.addOnScrollListener(new InternalRecyclerView.InternalScrollListener()
        {
            @Override
            public void onScrollToBottom(RecyclerView view, int state)
            {
                if (state == RecyclerView.SCROLL_STATE_IDLE)
                {
                    mPresenter.loading(false);
                }
            }
        });

        mCategoryAdapter = new MultiTypeAdapter(new ArrayList<>());
        DayProviders.EssenceProvider provider = new DayProviders.EssenceProvider();
        provider.setOnViewEventListener(this);
        mCategoryAdapter.registerProvider(Essence.class, provider);
        recyclerView.setAdapter(mCategoryAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mIsInited = true;

        if (mIsVisible && !hasLoadOnce)
        {
            loading();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {
            mIsVisible = true;
            if (!hasLoadOnce && mIsInited)
            {
                loading();
            }
        }
    }

    private void loading()
    {
        hasLoadOnce = true;
        mPresenter.start();
    }

    @Override
    public void onRefresh()
    {
        mPresenter.loading(true);
    }

    @Override
    public void onViewEvent(View view, short event, Object... args)
    {
        if (event == EssenceHolder.EVENT_CONTAINER_CLICKED)
        {
            assert args != null && args[0] != null;
            Essence essence = (Essence) args[0];
            L.v("on item click " + essence);
            ARouter.getInstance().build(RouterPath.ROUTER_WEBVIEW).withString(WebViewActivity.KEY_URL, essence.getUrl()).navigation();
        }
    }

    @Override
    public void showRefresh()
    {
        if (!mRefreshLayout.isRefreshing())
        {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideRefresh()
    {
        if (mRefreshLayout.isRefreshing())
        {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setData(List<Essence> data, boolean refresh)
    {
        if (refresh)
        {
            mCategoryAdapter.replace(data);
        } else
        {
            mCategoryAdapter.addAll(data);
        }
    }

    @Override
    public void setPresenter(@NonNull CategoryPresenter presenter)
    {
    }
}
