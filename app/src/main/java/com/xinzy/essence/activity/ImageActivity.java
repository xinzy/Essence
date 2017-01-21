package com.xinzy.essence.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xinzy.essence.R;
import com.xinzy.essence.base.BaseActivity;
import com.xinzy.essence.util.FileUtils;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.xinzy.essence.R.id.fab;

public class ImageActivity extends BaseActivity implements View.OnClickListener,
        PhotoViewAttacher.OnPhotoTapListener
{
    private static final String KEY_IMAGE = "IMAGE";

    private AppBarLayout         mAppBarLayout;
    private Toolbar              mToolbar;
    private FloatingActionButton mActionButton;
    private Animation            mShowAnim;
    private Animation            mHideAnim;


    private PhotoViewAttacher mAttacher;
    private String            mImageUrl;
    private boolean           isHiddenBar;

    public static void start(Activity activity, View view, String url)
    {
        Intent starter = new Intent(activity, ImageActivity.class);
        starter.putExtra("transition", "share");
        starter.putExtra(KEY_IMAGE, url);
        String sharedElementName = activity.getString(R.string.imageTransitionName);
        Bundle bundle            = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, sharedElementName).toBundle();
        ActivityCompat.startActivity(activity, starter, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mImageUrl = getIntent().getStringExtra(KEY_IMAGE);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.imageAppBar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mActionButton.setOnClickListener(this);

        PhotoView imageView = (PhotoView) findViewById(R.id.contentImage);
        Picasso.with(this).load(mImageUrl).into(imageView, new Callback() {
            @Override
            public void onSuccess()
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        hideToolbar();
                    }
                }, 3000);
            }

            @Override
            public void onError() {}
        });
        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.setOnPhotoTapListener(this);
        mAttacher.update();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        isHiddenBar = false;
        mAppBarLayout.setVisibility(View.VISIBLE);
        mActionButton.setVisibility(View.VISIBLE);
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
        case fab:
            Picasso.with(this).load(mImageUrl).into(new Target()
            {
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
        if (isHiddenBar)
        {
            showToolbar();
        } else
        {
            hideToolbar();
        }
    }

    @Override
    public void onOutsidePhotoTap()
    {}

    private void hideToolbar()
    {
        if (isHiddenBar) return;
        isHiddenBar = true;
        if (mHideAnim == null)
        {
            mHideAnim = AnimationUtils.loadAnimation(this, R.anim.alpha_hide);
        }
        mAppBarLayout.startAnimation(mHideAnim);
        mActionButton.startAnimation(mHideAnim);
    }

    private void showToolbar()
    {
        if (!isHiddenBar) return;
        isHiddenBar = false;
        if (mShowAnim == null)
        {
            mShowAnim = AnimationUtils.loadAnimation(this, R.anim.alpha_show);
        }
        mAppBarLayout.startAnimation(mShowAnim);
        mActionButton.startAnimation(mShowAnim);
    }
}
