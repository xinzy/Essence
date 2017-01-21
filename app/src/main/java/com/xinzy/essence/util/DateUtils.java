package com.xinzy.essence.util;

import java.util.Date;

/**
 * Created by xinzy on 17/1/21.
 */
public class DateUtils
{
    public static String format(Date date)
    {
        return format(date.getTime());
    }

    public static String format(long time)
    {
        long diffSecond = (System.currentTimeMillis() - time) / 1000;

        if (diffSecond < 60)
        {
            return "刚刚";
        } else if (diffSecond < 60 * 60)
        {
            return diffSecond / 60 + "分前";
        } else if (diffSecond < 60 * 60 * 24)
        {
            return diffSecond / 60 / 60 + "小时前";
        } else if (diffSecond < 60 * 60 * 24 * 30)
        {
            return diffSecond / 60 / 60 / 24 + "天前";
        } else if (diffSecond < 60 * 60 * 24 * 365)
        {
            return diffSecond / 60 / 60 / 24 / 30 + "月前";
        }
        return diffSecond / 60 / 60 / 24 / 365 + "年前";
    }

}
