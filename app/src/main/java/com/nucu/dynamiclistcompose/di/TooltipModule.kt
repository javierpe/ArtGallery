package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.impl.TooltipQueueImpl
import com.nucu.dynamiclistcompose.listeners.TooltipQueue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TooltipModule {

    @Binds
    @Singleton
    abstract fun bindTooltipQueue(
        impl: TooltipQueueImpl
    ): TooltipQueue
}