package com.scg.core.di

import com.scg.core.CoreDependency
import com.scg.core.util.DateTimeUtils
import com.scg.domain.dispatcher.UseCaseDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@InstallIn(SingletonComponent::class)
@Module(includes = [DataModule::class])
class CoreModule {

    @Singleton
    @Provides
    fun provideDateTimeUtils() = DateTimeUtils()

    @Singleton
    @Provides
    fun provideCoreDependency() = CoreDependency()

    @Singleton
    @Provides
    fun provideCoroutineScope() = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    @Singleton
    @Provides
    fun provideUseCaseDispatchers(): UseCaseDispatchers {
        return UseCaseDispatchers(Dispatchers.IO, Dispatchers.Default, Dispatchers.Main)
    }
}
