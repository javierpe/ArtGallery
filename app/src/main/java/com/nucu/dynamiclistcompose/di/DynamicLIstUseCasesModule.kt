package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.controllers.DynamicListController
import com.nucu.dynamiclistcompose.useCases.DynamicListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DynamicLIstUseCasesModule {

    @Provides
    @Singleton
    fun provideDynamicListUseCase(
        controller: DynamicListController
    ): DynamicListUseCase = DynamicListUseCase(controller)
}