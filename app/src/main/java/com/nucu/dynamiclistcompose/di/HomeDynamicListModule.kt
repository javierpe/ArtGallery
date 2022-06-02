package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.adapters.DefaultAdapterController
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.api.TooltipPreferencesApi
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
        delegates: MutableSet<@JvmSuppressWildcards DynamicListAdapterFactory>,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        tooltipPreferencesApi: TooltipPreferencesApi
    ): DefaultAdapterController {
        return DefaultAdapterController(
            delegates,
            emptySet(),
            defaultDispatcher,
            tooltipPreferencesApi
        )
    }
}