package com.scg.core.di

import com.scg.core.CoreDependency
import com.scg.core.util.DateTimeUtils
import com.scg.domain.usecase.GetTeslaNewsUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This component is required for adding dependencies to Dynamic Feature Modules by
 * adding [CoreModule] as dependent component
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoreModuleDependencies {

    /*
       Provision methods to provide dependencies to components that depend on this component
     */
    fun getTeslaNewsUseCase(): GetTeslaNewsUseCase
    fun coreDependency(): CoreDependency
    fun dateTimeUtils(): DateTimeUtils
}
