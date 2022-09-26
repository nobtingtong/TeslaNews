package com.scg.domain.usecase

import com.scg.data.model.remote.NewsData
import com.scg.data.repository.NewsRepository
import com.scg.domain.dispatcher.UseCaseDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTeslaNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    private val useCaseDispatchers: UseCaseDispatchers
) {

    fun getTeslaNews(date: String, page: Int): Flow<NewsData> {
        return flow {
            emit(newsRepository.getTeslaNews(date = date, page = page))
        }.flowOn(useCaseDispatchers.defaultDispatcher)
    }
}
