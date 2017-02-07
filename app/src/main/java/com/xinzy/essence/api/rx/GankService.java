package com.xinzy.essence.api.rx;

import com.xinzy.essence.http.ApiPath;
import com.xinzy.essence.model.DayType;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.model.ListSimple;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Xinzy on 2017-01-22.
 */
public interface GankService
{
    /**
     * 分类数据
     *
     * @param category
     * @param count
     * @param page
     * @return
     */
    @GET(ApiPath.PATH_CATEGORY)
    Observable<ListSimple<Essence>> category(@Path("category") String category, @Path("count") int count, @Path("page") int page);

    /**
     * 每日数据
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GET (ApiPath.PATH_DAY)
    Observable<DayType> day(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    /**
     * 搜索
     * @param keyword
     * @param category
     * @param count
     * @param page
     * @return
     */
    @GET (ApiPath.PATH_SEARCH)
    Observable<ListSimple<Essence>> search(@Path("keyword") String keyword, @Path("category") String category, @Path("count") int count, @Path("page") int page);
}
