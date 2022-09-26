package com.scg.data.api

import com.scg.data.constant.NEWS_TESLA
import com.scg.data.model.remote.NewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET(NEWS_TESLA)
    suspend fun fetchTeslaNews(
        @Query("from") q: String = "2022-08-26",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: String = "10",
        @Query("page") page: String = "1",
        @Query("apiKey") apiKey: String = "d7dc5b56ab6e4f73834482e75904a145"
    ): Response<NewsData>
}
