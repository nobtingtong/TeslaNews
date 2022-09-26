package com.scg.data.source

import com.scg.data.api.NewsApi
import com.scg.data.model.remote.NewsData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NewsRemoteDataSourceImplTest {

    @MockK
    lateinit var newsApi: NewsApi

    lateinit var newsRemoteDataSourceImpl: NewsRemoteDataSourceImpl

    @Before
    fun init() {
        MockKAnnotations.init(this, relaxed = true)
        newsRemoteDataSourceImpl = NewsRemoteDataSourceImpl(newsApi)
    }

    @Test
    fun `test get tesla news then verify api function is call`() {

        //Given
        val page = 1
        val date = "2022-08-26"
        coEvery {
            newsApi.fetchTeslaNews("1")
        } returns Response.success(
            NewsData(
                articles = listOf(),
                status = "",
                totalResults = 10
            )
        )

        //When
        runBlocking {
            newsRemoteDataSourceImpl.getTeslaNews(date, page)
        }

        //Then
        coVerify {
            newsApi.fetchTeslaNews(
                q = any(),
                sortBy = any(),
                pageSize = any(),
                page = any(),
                apiKey = any()
            )
        }
    }
}