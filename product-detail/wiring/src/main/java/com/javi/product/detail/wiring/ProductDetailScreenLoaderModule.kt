package com.javi.product.detail.wiring

import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.product.detail.impl.pageLoader.GetProductDetailScreenUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductDetailScreenLoaderModule {

    @Binds
    @Singleton
    abstract fun bindLoader(
        impl: GetProductDetailScreenUseCaseImpl
    ): GetProductDetailPageUseCase
}