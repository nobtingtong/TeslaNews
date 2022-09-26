package com.scg.news.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.scg.news.model.NewsDependency
import com.scg.news.viewmodel.NewsViewModel
import com.scg.news.viewmodel.NewsViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@InstallIn(FragmentComponent::class)
@Module(includes = [NewsBindModule::class])
class FeatureModule {

    @Provides
    fun provideNewsObject(context: Context) = NewsDependency(context)

    @Provides
    fun provideNewsViewModel(fragment: Fragment, factory: NewsViewModelFactory) =
        ViewModelProvider(fragment, factory).get(NewsViewModel::class.java)

    @Provides
    fun provideCoroutineScope() =
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
}

@InstallIn(FragmentComponent::class)
@Module
abstract class NewsBindModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}
