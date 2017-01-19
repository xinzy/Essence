package com.xinzy.essence.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.xinzy.essence.R;
import com.xinzy.essence.base.BaseActivity;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageActivity extends BaseActivity
{
    private static final String KEY_IMAGE = "IMAGE";

    private PhotoViewAttacher mAttacher;

    public static void start(Activity activity, View view, String url)
    {
        Intent starter = new Intent(activity, ImageActivity.class);
        starter.putExtra("transition", "share");
        starter.putExtra(KEY_IMAGE, url);
        String sharedElementName = activity.getString(R.string.imageTransitionName);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, sharedElementName).toBundle();
        ActivityCompat.startActivity(activity, starter, bundle);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        String url = getIntent().getStringExtra(KEY_IMAGE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        PhotoView imageView = (PhotoView) findViewById(R.id.contentImage);
        Picasso.with(this).load(url).into(imageView);
        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.update();
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
}
