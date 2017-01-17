package com.xinzy.essence.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;

import com.xinzy.essence.EssenceApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static void draw()
    {
        Bitmap bitmap = Bitmap.createBitmap(192, 192, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect targetRect = new Rect(0, 0, 192, 192);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF303F9F);
        canvas.drawRoundRect(new RectF(targetRect), 24, 24, paint);

        paint.setColor(0xFFFFFFFF);
        paint.setTextSize(64);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("干货", targetRect.centerX(), baseline, paint);

        File file = new File(getCacheRootDirection(), "192.png");
        try
        {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();

            Matrix matrix = new Matrix();
            float s = 144f / 192;
            matrix.postScale(s, s);
            Bitmap b144 = Bitmap.createBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()), 0, 0, 192, 192, matrix, true);
            outputStream = new FileOutputStream(new File(getCacheRootDirection(), "144.png"));
            b144.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();

            s = 96f / 192;
            matrix.postScale(s, s);
            Bitmap b96 = Bitmap.createBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()), 0, 0, 192, 192, matrix, true);
            outputStream = new FileOutputStream(new File(getCacheRootDirection(), "96.png"));
            b96.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();

        } catch (IOException e)
        {
        }
    }
}
