package com.javi.dynamic.list.di

import com.javi.dynamic.list.data.api.DynamicListUseCaseApi
import com.javi.dynamic.list.domain.useCases.DynamicListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DynamicListUseCasesModule {

    @Binds
    @Singleton
    abstract fun bindUseCase(
        impl: DynamicListUseCaseImpl
    ): DynamicListUseCaseApi
}