package com.javi.dynamic.list.di

import com.javi.dynamic.list.data.controllers.DefaultDynamicListController
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object HomeDynamicListModule {

    @Provides
    fun provideDefaultAdapterController(
        delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): DefaultDynamicListController {
        return DefaultDynamicListController(
            delegates,
            defaultDispatcher,
        )
    }
}