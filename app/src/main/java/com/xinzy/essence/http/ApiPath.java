package com.xinzy.essence.http;

/**
 * Created by xinzy on 17/1/16.
 */
public class ApiPath
{
    public static final String BASE_URL = "http://gank.io";

    /**
     * 所有干货，支持配图数据返回 （除搜索 Api）
     * etc: http://gank.io/api/data/Android/10/1
     */
    public static final String PATH_ALL = "/api/data/Android/{count}/{page}";

    /**
     * 搜索 API：
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * count 最大 50
     *
     * etc: http://gank.io/api/search/query/listview/category/Android/count/10/page/1
     */
    public static final String PATH_SEARCH = "/api/search/query/{keyword}/category/{category}/count/{count}/page/{page}";

    /**
     * 获取某几日干货网站数据:
     *
     * etc: http://gank.io/api/history/content/10/1
     */
    public static final String PATH_CONTENT = "/api/history/content/{count}/{page}";

    /**
     * 获取特定日期网站数据:
     *
     * etc: http://gank.io/api/history/content/day/2016/05/11
     */
    public static final String PATH_CONTENT_DAY = "/api/history/content/day/{year}/{month}/{day}";

    /**
     * 获取发过干货日期接口
     *
     * etc: http://gank.io/api/day/history
     */
    public static final String PATH_HISTORY = "/api/day/history ";

    /**
     * 分类数据:
     * etc:
     *  http://gank.io/api/data/Android/10/1
     *  http://gank.io/api/data/福利/10/1
     *  http://gank.io/api/data/iOS/20/2
     *  http://gank.io/api/data/all/20/2
     */
    public static final String PATH_CATEGORY = "/api/data/{category}/{count}/{page}";

    /**
     * 每日数据
     * etc: http://gank.io/api/day/2015/08/06
     */
    public static final String PATH_DAY = "/api/day/{year}/{month}/{day}";

    /**
     * 随机数据
     *
     * etc: http://gank.io/api/random/data/Android/20
     */
    public static final String PATH_RANDOM = "/api/random/data/{category}/{count}";
}
