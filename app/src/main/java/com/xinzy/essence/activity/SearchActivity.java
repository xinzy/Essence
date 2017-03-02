package com.xinzy.essence.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.essence.R;
import com.xinzy.essence.adapter.DayProviders;
import com.xinzy.essence.adapter.holder.EssenceHolder;
import com.xinzy.essence.base.BaseActivity;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.SearchPresenter;
import com.xinzy.essence.presenter.impl.SearchPresenterImpl;
import com.xinzy.essence.util.Macro;
import com.xinzy.essence.util.Preconditions;
import com.xinzy.essence.router.RouterPath;
import com.xinzy.essence.widget.InternalRecyclerView;
import com.xinzy.essence.widget.OnViewEventListener;
import com.xinzy.essence.widget.adapter.MultiTypeAdapter;

import java.util.List;

public class SearchActivity extends BaseActivity implements com.xinzy.essence.view.SearchView, OnViewEventListener, SearchView.OnQueryTextListener
{
    private SearchView mSearchView;
    private SwipeRefreshLayout mRefreshLayout;
    private InternalRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;

    private SearchPresenter mSearchPresenter;
    private String mCategory = Macro.CATEGORYS[1];

    public static void start(Activity activity, View view)
    {
        Intent starter = new Intent(activity, SearchActivity.class);
        starter.putExtra("transition", "share");
        String sharedElementName = activity.getString(R.string.shareTransition);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, sharedElementName).toBundle();
        ActivityCompat.startActivity(activity, starter, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchView = (SearchView) findViewById(R.id.searchView);
        SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.searchRefreshLayout);
        mRefreshLayout.setEnabled(false);

        mRecyclerView = (InternalRecyclerView) findViewById(R.id.searchRecyclerView);
        int divideColor = ResourcesCompat.getColor(getResources(), R.color.colorDivide, getTheme());
        mRecyclerView.addItemDecoration(new InternalRecyclerView.SpacesItemDecoration(0, 2, divideColor));
        mRecyclerView.addOnScrollListener(new InternalRecyclerView.InternalScrollListener()
        {
            @Override
            public void onScrollToBottom(RecyclerView view, int state)
            {
                if (state == RecyclerView.SCROLL_STATE_IDLE)
                {
                    mSearchPresenter.loadMore();
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MultiTypeAdapter();
        DayProviders.EssenceProvider essenceProvider = new DayProviders.EssenceProvider();
        essenceProvider.setOnViewEventListener(this);
        mAdapter.registerProvider(Essence.class, essenceProvider);
        mRecyclerView.setAdapter(mAdapter);

        mSearchPresenter = new SearchPresenterImpl(this);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String queryString = intent.getStringExtra(SearchManager.QUERY);
            mSearchView.setQuery(queryString, true);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        query(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String text)
    {
        query(text);
        return true;
    }

    private void query(String text)
    {
        mSearchPresenter.cancel();
        if (TextUtils.isEmpty(text))
        {
            mAdapter.clear();
        } else
        {
            mSearchPresenter.search(text, mCategory);
        }
    }

    @Override
    public void onViewEvent(View view, short event, Object... args)
    {
        if (event == EssenceHolder.EVENT_CONTAINER_CLICKED && args != null)
        {
            Essence essence = (Essence) Preconditions.checkNotNull(args[0]);
            ARouter.getInstance().build(RouterPath.ROUTER_WEBVIEW).withString(WebViewActivity.KEY_URL, essence.getUrl()).navigation();
        }
    }

    @Override
    public void showLoading(boolean isLoading)
    {
        mRefreshLayout.setRefreshing(isLoading);
    }

    @Override
    public void setData(List<Essence> data, boolean isRefresh)
    {
        if (isRefresh)
        {
            mAdapter.replace(data);
        } else
        {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void setPresenter(@NonNull SearchPresenter presenter)
    {
    }
}
