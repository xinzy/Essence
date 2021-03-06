package com.xinzy.essence.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.essence.R;
import com.xinzy.essence.adapter.BeautyAdapter;
import com.xinzy.essence.base.BaseActivity;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.presenter.MainPresenter;
import com.xinzy.essence.presenter.impl.MainPresenterImpl;
import com.xinzy.essence.router.RouterPath;
import com.xinzy.essence.util.L;
import com.xinzy.essence.util.Macro;
import com.xinzy.essence.view.MainView;
import com.xinzy.essence.widget.InternalRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener, MainView, MainView.OnItemClickListener
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DEFAULT_CATEGORY = Macro.EXT_CATEGORY[0];
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 627;

    private AppBarLayout mAppBar;
    private SwipeRefreshLayout mRefreshLayout;
    private InternalRecyclerView mRecycleView;
    private BeautyAdapter mAdapter;
    private MainPresenter mPresenter;

    private long mLastBackTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppBar = (AppBarLayout) findViewById(R.id.mainAppBar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mRecycleView.smoothScrollToPosition(0);
                mPresenter.start();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.inflateMenu(R.menu.menu_main_nav);
        SwitchCompat themeSwicher = (SwitchCompat) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menuTheme))
                .findViewById(R.id.viewSwitch);
        themeSwicher.setChecked(isNightMode());
        themeSwicher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                setNightMode(isChecked);
                recreate();
            }
        });

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRefreshLayout.setOnRefreshListener(this);
        mRecycleView = (InternalRecyclerView) findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.addOnScrollListener(new InternalRecyclerView.InternalScrollListener()
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
        mAdapter = new BeautyAdapter(new ArrayList<Essence>());
        mAdapter.setTag(TAG);
        mAdapter.setOnItemClickListener(this);
        mRecycleView.setAdapter(mAdapter);

        mPresenter = new MainPresenterImpl(this, DEFAULT_CATEGORY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            L.v("has write external storage permission");
            mPresenter.start();
        } else
        {
            requestPermission();
        }
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            long time = SystemClock.uptimeMillis();
            if (time - mLastBackTime < 2000)
            {
                super.onBackPressed();
            } else
            {
                mLastBackTime = time;
                Snackbar.make(mRecycleView, R.string.pressToExit, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                mPresenter.start();
            } else
            {
                remindPermission();
            }
        }
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
    }

    private void remindPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(this).setMessage(R.string.remindWriteExternalStorage).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    requestPermission();
                }
            }).setNegativeButton(R.string.cancel, null).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.menuTheme)
        {
            L.e("Change theme");
            return true;
        }
        ARouter.getInstance().build(RouterPath.ROUTER_CATEGORY).withString(CategoryActivity.KEY_CATEGORY, item.getTitle().toString()).navigation();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_search:
                SearchActivity.start(this, mAppBar);
                return true;
            case R.id.action_about:
                ARouter.getInstance().build(RouterPath.ROUTER_ABOUT).navigation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh()
    {
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull MainPresenter presenter)
    {
    }

    @Override
    public void showRefresh()
    {
        if (! mRefreshLayout.isRefreshing())
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
            mAdapter.replace(data);
        } else
        {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void enter(Essence essence)
    {
        ARouter.getInstance().build(RouterPath.ROUTER_DAY).withParcelable(DayActivity.KEY_ESSENCE, essence).navigation();
    }

    @Override
    public void onImageClick(ImageView img, Essence essence)
    {
        ImageActivity.start(this, img, essence.getUrl());
    }

    @Override
    public void onTextClick(Essence essence)
    {
        mPresenter.onTextClick(essence);
    }
}
