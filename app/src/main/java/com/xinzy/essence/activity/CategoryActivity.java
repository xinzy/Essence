package com.xinzy.essence.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.xinzy.essence.R;
import com.xinzy.essence.base.BaseActivity;
import com.xinzy.essence.fragment.CategoryFragment;
import com.xinzy.essence.util.Macro;

public class CategoryActivity extends BaseActivity
{
    private static final String KEY_CATEGORY = "CATEGORY";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public static void start(Context context, String category)
    {
        Intent starter = new Intent(context, CategoryActivity.class);
        starter.putExtra(KEY_CATEGORY, category);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        String category = getIntent().getStringExtra(KEY_CATEGORY);
        int position = 0;
        for (int i = 0; i < Macro.CATEGORYS.length; i++)
        {
            if (Macro.CATEGORYS[i].equals(category))
            {
                position = i;
                break;
            }
        }
        mViewPager.setCurrentItem(position, false);
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return CategoryFragment.newInstance(Macro.CATEGORYS[position]);
        }

        @Override
        public int getCount()
        {
            return Macro.CATEGORYS.length;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return Macro.CATEGORYS[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
        }
    }
}
