package com.xinzy.essence.api.retrofit;

import com.xinzy.essence.http.ApiPath;
import com.xinzy.essence.model.DayType;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.model.ListSimple;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
    Call<ListSimple<Essence>> category(@Path("category") String category, @Path("count") int count, @Path("page") int page);

    /**
     * 每日数据
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GET (ApiPath.PATH_DAY)
    Call<DayType> day(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    /**
     * 搜索
     * @param keyword
     * @param category
     * @param count
     * @param page
     * @return
     */
    @GET (ApiPath.PATH_SEARCH)
    Call<ListSimple<Essence>> search(@Path("keyword") String keyword, @Path("category") String category, @Path("count") int count, @Path("page") int page);
}
