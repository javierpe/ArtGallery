package com.nucu.dynamiclistcompose.di

import com.google.gson.Gson
import com.nucu.dynamiclistcompose.data.controllers.DefaultDynamicListController
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.api.TooltipPreferencesApi
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
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        tooltipPreferencesApi: TooltipPreferencesApi
    ): DefaultDynamicListController {
        return DefaultDynamicListController(
            delegates,
            emptySet(),
            defaultDispatcher,
            tooltipPreferencesApi
        )
    }

    @Provides
    fun provideGson() = Gson()
}