package com.journaldev.mvpdagger2retroiftrxjava.retrofit;

import com.journaldev.mvpdagger2retroiftrxjava.pojo.NewsData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface APIInterface {

   /* @GET("ticker/?")
    Observable<List<NewsData>> getData(@Query("limit") String limit);*/

    @GET("top-headlines")
    Observable<NewsData> getData(@Query("country") String country, @Query("apiKey") String apiKey);
}
