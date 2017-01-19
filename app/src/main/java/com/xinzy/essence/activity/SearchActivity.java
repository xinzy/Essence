package com.xinzy.essence.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.xinzy.essence.R;
import com.xinzy.essence.base.BaseActivity;
import com.xinzy.essence.util.L;
import com.xinzy.essence.widget.InternalRecyclerView;

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener
{
    private SearchView mSearchView;

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

        InternalRecyclerView recyclerView = (InternalRecyclerView) findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSearchView = (SearchView) findViewById(R.id.searchView);
        SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);
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
        L.d("query " + text);
    }
}
