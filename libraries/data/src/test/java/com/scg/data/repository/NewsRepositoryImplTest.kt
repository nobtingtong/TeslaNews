package com.scg.data.repository

import com.scg.data.model.remote.NewsData
import com.scg.data.source.NewsRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class NewsRepositoryImplTest {

    @MockK
    lateinit var newsRemoteDataSource: NewsRemoteDataSource

    private lateinit var newsRepositoryImpl: NewsRepositoryImpl

    @Before
    fun init() {
        MockKAnnotations.init(this, relaxed = true)
        newsRepositoryImpl = NewsRepositoryImpl(newsRemoteDataSource)
    }

    @Test
    fun `test get tesla news from news remote data source then success`() {
        //Given
        val page = 1
        val date = "2022-08-26"
        coEvery {
            newsRemoteDataSource.getTeslaNews(date = date, page = page)
        } returns Response.success(
            NewsData(
                articles = listOf(),
                status = "",
                totalResults = 10
            )
        )

        //When
        runBlocking {
            newsRepositoryImpl.getTeslaNews(date, page)
        }

        //Then
        coVerify {
            newsRemoteDataSource.getTeslaNews(date = date, page = page)
        }
    }
}