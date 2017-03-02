package com.xinzy.essence.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xinzy.essence.R;
import com.xinzy.essence.adapter.DayProviders;
import com.xinzy.essence.adapter.holder.EssenceHolder;
import com.xinzy.essence.model.DayType;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.DayPresenter;
import com.xinzy.essence.presenter.impl.DayPresenterImpl;
import com.xinzy.essence.util.Preconditions;
import com.xinzy.essence.view.DayView;
import com.xinzy.essence.widget.InternalRecyclerView;
import com.xinzy.essence.widget.OnViewEventListener;
import com.xinzy.essence.widget.adapter.MultiTypeAdapter;

public class DayActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, DayView,
        OnViewEventListener, View.OnClickListener
{
    private static final String KEY_ESSENCE = "ESSENCE";
    
    private SwipeRefreshLayout mRefreshLayout;
    private MultiTypeAdapter mAdapter;

    private DayPresenter mDayPresenter;
    private Essence mDayEssence;
    
    public static void start(Context context, Essence essence)
    {
        Intent starter = new Intent(context, DayActivity.class);
        starter.putExtra(KEY_ESSENCE, essence);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        ImageView topImageView = (ImageView) findViewById(R.id.dayImage);
        topImageView.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.dayRefreshLayout);
        mRefreshLayout.setOnRefreshListener(this);

        InternalRecyclerView recyclerView = (InternalRecyclerView) findViewById(R.id.dayRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        int divideColor = ResourcesCompat.getColor(getResources(), R.color.colorDivide, getTheme());
        recyclerView.addItemDecoration(new InternalRecyclerView.SpacesItemDecoration(0, 2, divideColor));

        DayProviders.CategoryProvider categoryProvider = new DayProviders.CategoryProvider();
        DayProviders.EssenceProvider titleProvider = new DayProviders.EssenceProvider();
        titleProvider.setOnViewEventListener(this);
        mAdapter = new MultiTypeAdapter();
        mAdapter.registerProvider(String.class, categoryProvider).registerProvider(Essence.class, titleProvider);
        recyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        mDayEssence = intent.getParcelableExtra(KEY_ESSENCE);
        Picasso.with(this).load(mDayEssence.getUrl()).into(topImageView);

        mDayPresenter = new DayPresenterImpl(this, mDayEssence);
        mDayPresenter.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.dayImage:
            ImageActivity.start(this, v, mDayEssence.getUrl());
            break;
        }
    }

    @Override
    public void onRefresh()
    {
        mDayPresenter.start();
    }

    @Override
    public void showTitle(String title)
    {
        setTitle(title);
    }

    @Override
    public void showRefresh()
    {
        if (! mRefreshLayout.isRefreshing()) mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefresh()
    {
        if (mRefreshLayout.isRefreshing()) mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setData(DayType data)
    {
        mAdapter.replace(data.getItems());
    }

    @Override
    public void setPresenter(@NonNull DayPresenter presenter)
    {
    }

    @Override
    public void onViewEvent(View view, short event, Object... args)
    {
        if (event == EssenceHolder.EVENT_CONTAINER_CLICKED && args != null)
        {
            Essence essence = (Essence) Preconditions.checkNotNull(args[0]);
            WebViewActivity.start(this, essence.getUrl());
        }
    }
}
