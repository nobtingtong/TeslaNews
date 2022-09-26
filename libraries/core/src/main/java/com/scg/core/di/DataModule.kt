package com.scg.core.di

import com.scg.data.di.NetworkModule
import com.scg.data.repository.NewsRepository
import com.scg.data.repository.NewsRepositoryImpl
import com.scg.data.source.NewsRemoteDataSource
import com.scg.data.source.NewsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        NetworkModule::class
    ]
)
/**
 * This dagger module binds concrete implementations to interface required by
 * **data** and **domain** structure modules.
 *
 * For instance
 * ```
 * class LoginRepositoryImpl @Inject constructor(
 *      private val remoteDataSource: RemoteDataSource,
 *      private val localDataSource: LocalDataSource,
 *      private val mapper: Mapper
 *  ) : Repository {
 *
 * ```
 *
 * requires interfaces instead of concrete implementations.
 */
interface DataModule {

    @Singleton
    @Binds
    fun bindNewsRemoteDataSource(newsRemoteDataSourceImpl: NewsRemoteDataSourceImpl): NewsRemoteDataSource

    @Singleton
    @Binds
    fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}
