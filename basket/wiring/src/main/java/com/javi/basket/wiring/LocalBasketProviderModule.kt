package com.javi.basket.wiring

import com.javi.basket.impl.BasketUpdater
import com.javi.basket.impl.di.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalBasketProviderModule {

    @IODispatcher
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideLocalBasket(
        @IODispatcher coroutineDispatcher: CoroutineDispatcher
    ) = BasketUpdater(coroutineDispatcher)
}
