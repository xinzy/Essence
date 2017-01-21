package com.xinzy.essence.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xinzy.essence.R;
import com.xinzy.essence.base.BaseActivity;
import com.xinzy.essence.util.FileUtils;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageActivity extends BaseActivity implements View.OnClickListener, PhotoViewAttacher.OnPhotoTapListener
{
    private static final String KEY_IMAGE = "IMAGE";

    private String mImageUrl;
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
        mImageUrl = getIntent().getStringExtra(KEY_IMAGE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        PhotoView imageView = (PhotoView) findViewById(R.id.contentImage);
        Picasso.with(this).load(mImageUrl).into(imageView);
        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.setOnPhotoTapListener(this);
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

    @Override
    public void onClick(final View v)
    {
        switch (v.getId())
        {
        case R.id.fab:
            Picasso.with(this).load(mImageUrl).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                {
                    FileUtils.saveImage(bitmap);
                    Snackbar.make(v, "Save Success", Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {}

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {}
            });
            break;
        }
    }

    @Override
    public void onPhotoTap(View view, float x, float y)
    {

    }

    @Override
    public void onOutsidePhotoTap()
    {}
}
