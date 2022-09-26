package com.scg.news.di

import android.app.Application
import androidx.fragment.app.Fragment
import com.scg.core.di.CoreModuleDependencies
import com.scg.news.NewsFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [CoreModuleDependencies::class],
    modules = [FeatureModule::class]
)
interface NewsComponent {

    fun inject(fragment: NewsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreModuleDependencies: CoreModuleDependencies,
            @BindsInstance fragment: Fragment,
            @BindsInstance application: Application
        ): NewsComponent
    }
}
