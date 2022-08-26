package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.data.api.DynamicListControllerApi
import com.nucu.dynamiclistcompose.data.api.DynamicListMockResponseApi
import com.nucu.dynamiclistcompose.data.api.DynamicListRenderProcessorApi
import com.nucu.dynamiclistcompose.domain.impl.DynamicListControllerImpl
import com.nucu.dynamiclistcompose.domain.impl.DynamicListMockResponseImpl
import com.nucu.dynamiclistcompose.domain.impl.DynamicListRenderProcessorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DynamicListWiringModule {

    @Binds
    @Singleton
    abstract fun bindDynamicListController(
        impl: DynamicListControllerImpl
    ): DynamicListControllerApi

    @Binds
    @Singleton
    abstract fun bindDynamicListMockResponse(
        impl: DynamicListMockResponseImpl
    ): DynamicListMockResponseApi

    @Binds
    @Singleton
    abstract fun bindDynamicListRenderProcessor(
        impl: DynamicListRenderProcessorImpl
    ): DynamicListRenderProcessorApi
}