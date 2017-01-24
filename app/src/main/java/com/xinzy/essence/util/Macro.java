package com.xinzy.essence.util;

import com.xinzy.essence.BuildConfig;

/**
 * Created by xinzy on 17/1/16.
 */
public class Macro
{
    /**
     * 是否开启debug
     */
    public static final boolean DEBUG = BuildConfig.LOG_DEBUG;

    /**
     * 单页请求数
     */
    public static final int PER_PAGE = 15;

    /**
     * 图片缓存大小
     */
    public static final int IMAGE_CACHE_SIZE = 1024 * 1024 * 100;

    /**
     * 网络请求缓存
     */
    public static final int HTTP_CACHE_SIZE = 1024 * 1024 * 10;

    /**
     * 网络请求缓存有效期
     */
    public static final int HTTP_CACHE_CONTROL_MAX_AGE = 60 * 30;

    /**
     * 支持分类
     */
    public static final String[] CATEGORYS = {"all", "Android", "iOS", "拓展资源", "前端", "瞎推荐", "App"};

    /**
     * 扩展分类
     */
    public static final String[] EXT_CATEGORY = {"福利", "休息视频"};
}
