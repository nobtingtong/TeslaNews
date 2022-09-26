package com.scg.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scg.core.util.DateTimeUtils
import com.scg.core.util.convertToFlowViewState
import com.scg.core.viewstate.ViewState
import com.scg.data.model.remote.NewsData
import com.scg.domain.usecase.GetTeslaNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val getTeslaNewsUseCase: GetTeslaNewsUseCase,
    private val dateTimeUtils: DateTimeUtils
) : ViewModel() {

    private val _dataState = MutableLiveData<ViewState<NewsData>>()
    val dataState: LiveData<ViewState<NewsData>>
        get() = _dataState

    var currentPage = 0

    fun getTeslaNews() {
        currentPage += 1
        coroutineScope.launch {
            getTeslaNewsUseCase.getTeslaNews(
                date = dateTimeUtils.now(),
                page = currentPage
            ).convertToFlowViewState(Dispatchers.IO)
                .collect {
                    _dataState.value = it
                }
        }
    }
}

class NewsViewModelFactory @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val getTeslaNewsUseCase: GetTeslaNewsUseCase,
    private val dateTimeUtils: DateTimeUtils
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != NewsViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return NewsViewModel(
            coroutineScope,
            getTeslaNewsUseCase,
            dateTimeUtils
        ) as T
    }
}