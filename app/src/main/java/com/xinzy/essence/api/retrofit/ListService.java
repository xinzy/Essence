package com.xinzy.essence.api.retrofit;

import com.xinzy.essence.http.ApiPath;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.model.ListSimple;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by xinzy on 17/1/16.
 */
public interface ListService
{
    /**
     * 分类数据
     * @param category
     * @param count
     * @param page
     * @return
     */
    @GET (ApiPath.PATH_CATEGORY)
    Call<ListSimple<Essence>> category(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}
