package com.xinzy.essence.api.retrofit;

import com.xinzy.essence.http.ApiPath;
import com.xinzy.essence.model.DayType;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Xinzy on 2017-01-19.
 */
public interface DayService
{
    /**
     * 每日数据
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GET (ApiPath.PATH_DAY)
    Call<DayType> day(@Path("year") int year, @Path("month") int month, @Path("day") int day);
}
