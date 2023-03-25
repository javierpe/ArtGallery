package com.javi.basket.wiring

import com.javi.basket.api.LocalBasketApi
import com.javi.basket.impl.LocalBasketImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalBasketModule {

    @Binds
    @Singleton
    abstract fun bindLocalBasketApi(
        impl: LocalBasketImpl
    ): LocalBasketApi
}