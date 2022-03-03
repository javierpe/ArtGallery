package com.nucu.dynamiclistcompose.hi

import com.nucu.dynamiclistcompose.controllers.DynamicListController
import com.nucu.dynamiclistcompose.impl.DynamicListControllerImpl
import com.nucu.dynamiclistcompose.listeners.DynamicListComposeLoader
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
    ): DynamicListController
}