package com.javi.dynamic.list.di

import com.javi.dynamic.list.data.api.DynamicListControllerApi
import com.javi.dynamic.list.data.api.DynamicListMockResponseApi
import com.javi.dynamic.list.data.api.DynamicListRenderProcessorApi
import com.javi.dynamic.list.domain.impl.DynamicListControllerImpl
import com.javi.dynamic.list.domain.impl.DynamicListMockResponseImpl
import com.javi.dynamic.list.domain.impl.DynamicListRenderProcessorImpl
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