package com.xinzy.essence.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.xinzy.essence.R;
import com.xinzy.essence.model.DayType;
import com.xinzy.essence.presenter.DayPresenter;
import com.xinzy.essence.view.DayView;
import com.xinzy.essence.widget.InternalRecyclerView;

public class DayActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, DayView
{
    private SwipeRefreshLayout mRefreshLayout;

    private DayPresenter mDayPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.dayRefreshLayout);
        mRefreshLayout.setOnRefreshListener(this);

        InternalRecyclerView recyclerView = (InternalRecyclerView) findViewById(R.id.dayRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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
    public void onRefresh()
    {
        mDayPresenter.start();
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
    }

    @Override
    public void setPresenter(@NonNull DayPresenter presenter)
    {
    }
}
