package com.xinzy.essence.util;

import android.graphics.Bitmap;
import android.os.Environment;

import com.xinzy.essence.EssenceApplication;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Xinzy on 2017-01-17.
 */
public class FileUtils
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

    public static boolean saveImage(Bitmap bitmap)
    {
        try
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }
}
