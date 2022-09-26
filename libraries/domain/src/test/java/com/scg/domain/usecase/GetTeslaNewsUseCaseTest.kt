package com.scg.domain.usecase

import com.scg.data.model.remote.NewsData
import com.scg.data.repository.NewsRepository
import com.scg.domain.dispatcher.UseCaseDispatchers
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTeslaNewsUseCaseTest {
    @MockK
    lateinit var newsRepository: NewsRepository

    @MockK
    lateinit var useCaseDispatchers: UseCaseDispatchers

    lateinit var getTeslaNewsUseCase: GetTeslaNewsUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this, relaxed = true)
        getTeslaNewsUseCase = GetTeslaNewsUseCase(newsRepository, useCaseDispatchers)
    }

    @Test
    fun `test get tesla news data then verify repository function is call`() {
        //Given
        val page = 1
        val date = "2022-08-26"
        every {
            useCaseDispatchers.defaultDispatcher
        } returns Dispatchers.Default

        coEvery {
            newsRepository.getTeslaNews(date, page)
        } returns NewsData(
            articles = listOf(),
            status = "",
            totalResults = 10
        )

        //When
        runBlocking {
            getTeslaNewsUseCase.getTeslaNews(date, page).collect { }
        }

        //Then
        coVerify {
            newsRepository.getTeslaNews(date, page)
        }
    }
}