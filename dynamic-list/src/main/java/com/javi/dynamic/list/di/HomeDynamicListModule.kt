package com.javi.dynamic.list.di

import com.javi.dynamic.list.data.controllers.DefaultDynamicListComposeController
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HomeDynamicListModule {

    @Provides
    fun provideDefaultAdapterController(
        delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>,
    ): DefaultDynamicListComposeController {
        return DefaultDynamicListComposeController(
            delegates,
        )
    }
}