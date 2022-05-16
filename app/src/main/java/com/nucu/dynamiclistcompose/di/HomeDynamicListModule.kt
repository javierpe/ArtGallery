package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.adapters.DefaultAdapterController
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HomeDynamicListModule {

    @Provides
    fun provideDefaultAdapterController(
        delegates: MutableSet<@JvmSuppressWildcards DynamicListAdapterFactory>,
    ): DefaultAdapterController {
        return DefaultAdapterController(delegates, emptySet())
    }
}