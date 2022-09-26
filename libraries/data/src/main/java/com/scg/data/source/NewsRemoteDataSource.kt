package com.scg.data.source

import com.scg.data.api.NewsApi
import com.scg.data.model.remote.NewsData
import retrofit2.Response
import javax.inject.Inject

interface NewsRemoteDataSource {
    suspend fun getTeslaNews(date: String, page: Int): Response<NewsData>
}

class NewsRemoteDataSourceImpl @Inject constructor(
    private val api: NewsApi
) : NewsRemoteDataSource {
    override suspend fun getTeslaNews(date: String, page: Int) = api.fetchTeslaNews(
        q = date,
        page = page.toString()
    )
}
