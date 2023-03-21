package com.javi.wiring

import com.javi.api.LocalBasketApi
import com.javi.impl.LocalBasketImpl
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