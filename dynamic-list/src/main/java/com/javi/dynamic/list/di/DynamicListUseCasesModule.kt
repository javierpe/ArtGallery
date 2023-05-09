package com.javi.dynamic.list.di

import com.javi.dynamic.list.domain.api.useCases.GetBasketTransformUseCase
import com.javi.dynamic.list.domain.api.useCases.GetDynamicListUseCase
import com.javi.dynamic.list.domain.api.useCases.GetSkeletonsByContextUseCase
import com.javi.dynamic.list.domain.api.useCases.GetTooltipSequenceUseCase
import com.javi.dynamic.list.domain.api.useCases.RenderMapperProcessorUseCase
import com.javi.dynamic.list.domain.api.useCases.SaveSkeletonsUseCase
import com.javi.dynamic.list.domain.extensions.GetBasketTransformUseCaseImpl
import com.javi.dynamic.list.domain.useCases.DynamicListUseCaseImpl
import com.javi.dynamic.list.domain.useCases.GetSkeletonsByContextUseCaseImpl
import com.javi.dynamic.list.domain.useCases.GetTooltipSequenceUseCaseImpl
import com.javi.dynamic.list.domain.useCases.RenderMapperProcessorUseCaseImpl
import com.javi.dynamic.list.domain.useCases.SaveSkeletonsUseCaseImpl
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
    ): GetDynamicListUseCase

    @Binds
    @Singleton
    abstract fun bindGetDynamicListDataUseCase(
        impl: GetTooltipSequenceUseCaseImpl
    ): GetTooltipSequenceUseCase

    @Binds
    @Singleton
    abstract fun bindGetSkeletonsByContextUseCase(
        impl: GetSkeletonsByContextUseCaseImpl
    ): GetSkeletonsByContextUseCase

    @Binds
    @Singleton
    abstract fun bindSaveSkeletonsUseCase(
        impl: SaveSkeletonsUseCaseImpl
    ): SaveSkeletonsUseCase

    @Binds
    @Singleton
    abstract fun bindDynamicListRenderProcessorUseCase(
        impl: RenderMapperProcessorUseCaseImpl
    ): RenderMapperProcessorUseCase

    @Binds
    @Singleton
    abstract fun bindGetBasketTransformUseCase(
        impl: GetBasketTransformUseCaseImpl
    ): GetBasketTransformUseCase
}
