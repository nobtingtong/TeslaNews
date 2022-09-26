package com.scg.news.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.scg.core.util.DateTimeUtils
import com.scg.core.util.convertToFlowViewState
import com.scg.core.viewstate.Status
import com.scg.core.viewstate.ViewState
import com.scg.data.model.remote.NewsData
import com.scg.domain.usecase.GetTeslaNewsUseCase
import com.scg.test_utils.rule.TestCoroutineRule
import com.scg.test_utils.util.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


class NewsViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getTeslaNewsUseCase: GetTeslaNewsUseCase

    @MockK
    lateinit var dateTimeUtils: DateTimeUtils

    lateinit var newsViewModel: NewsViewModel

    @Before
    fun init() {
        MockKAnnotations.init(this, relaxed = true)
        newsViewModel =
            NewsViewModel(testCoroutineRule.testCoroutineScope, getTeslaNewsUseCase, dateTimeUtils)
    }

    @Test
    fun `test get tesla  then verify is call function in use case and check is success`() {
        //Given
        val page = 1
        val date = "2022-08-26"
        val flowSuccessResponse = flow {
            emit(
                ViewState(
                    status = Status.SUCCESS,
                    data = NewsData(
                        articles = listOf(),
                        status = "",
                        totalResults = 10
                    ), error = null
                )
            )
        }

        coEvery {
            getTeslaNewsUseCase.getTeslaNews(date, page).convertToFlowViewState(Dispatchers.IO)
        } returns flowSuccessResponse
        every {
            dateTimeUtils.now()
        } returns date

        //When
        newsViewModel.getTeslaNews()

        //Then
        assert(newsViewModel.currentPage == 1)
        coVerify {
            getTeslaNewsUseCase.getTeslaNews(date, page).convertToFlowViewState(Dispatchers.IO)
                .collect {}
        }
        runBlocking {
            val dataState = newsViewModel.dataState.getOrAwaitValue()
            assert(dataState.status == Status.SUCCESS)
            assert(dataState.error == null)
            assert(dataState.isSuccess())
            assert(dataState.data is NewsData)
        }
    }

    @Test
    fun `test get tesla  then verify is call function in use case and check is Error`() {
        //Given
        val page = 1
        val date = "2022-08-26"
        coEvery {
            getTeslaNewsUseCase.getTeslaNews(date, page)
        } returns flow {
            throw IOException()
        }
        every {
            dateTimeUtils.now()
        }returns date

        //When
        newsViewModel.getTeslaNews()

        //Then
        assert(newsViewModel.currentPage == 1)
        coVerify {
            getTeslaNewsUseCase.getTeslaNews(date, page).convertToFlowViewState(Dispatchers.IO)
        }
        runBlocking {
            newsViewModel.dataState.getOrAwaitValue(time = 5).also { dataState ->
                assert(dataState.status == Status.ERROR)
                assert(dataState.error != null)
                assert(!dataState.isSuccess())
                assert(dataState.data == null)
            }
        }
    }
}