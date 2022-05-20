package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.api.DynamicListControllerApi
import com.nucu.dynamiclistcompose.impl.DynamicListControllerImpl
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
}