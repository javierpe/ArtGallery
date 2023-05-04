package com.javi.dynamic.list.di

import com.javi.dynamic.list.data.repositories.DynamicListRepository
import com.javi.dynamic.list.data.dataSources.DynamicListDataSourceApi
import com.javi.dynamic.list.data.useCases.DynamicListRenderProcessorApi
import com.javi.dynamic.list.domain.repository.DynamicListRepositoryImpl
import com.javi.dynamic.list.domain.dataSource.DynamicListDataSourceImpl
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
        impl: DynamicListRepositoryImpl
    ): DynamicListRepository

    @Binds
    @Singleton
    abstract fun bindDynamicListMockResponse(
        impl: DynamicListDataSourceImpl
    ): DynamicListDataSourceApi

    @Binds
    @Singleton
    abstract fun bindDynamicListRenderProcessor(
        impl: DynamicListRenderProcessorImpl
    ): DynamicListRenderProcessorApi
}