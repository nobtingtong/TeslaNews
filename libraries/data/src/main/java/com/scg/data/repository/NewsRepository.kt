package com.scg.data.repository

import com.scg.data.model.remote.NewsData
import com.scg.data.source.NewsRemoteDataSource
import javax.inject.Inject

interface NewsRepository {
    suspend fun getTeslaNews(date: String, page: Int): NewsData
}

class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getTeslaNews(date: String, page: Int): NewsData {
        return remoteDataSource.getTeslaNews(date = date, page = page).body()!!
    }
}
