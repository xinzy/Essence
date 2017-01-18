package com.xinzy.essence.util;

import android.os.Environment;

import com.xinzy.essence.EssenceApplication;

import java.io.File;

/**
 * Created by Xinzy on 2017-01-17.
 */
public class FileUtil
{
    public static File getCacheRootDirection()
    {
        File rootDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            File dir = new File(Environment.getExternalStorageDirectory(), "Android/data/com.xinzy.essence");
            rootDir = dir.exists() || dir.mkdirs() ? dir : null;
        }
        if (rootDir == null)
        {
            rootDir = EssenceApplication.getInstance().getCacheDir();
        }

        return rootDir;
    }
}
