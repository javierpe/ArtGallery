package com.javi.basket.wiring

import com.javi.basket.api.AddProductToBasketUseCase
import com.javi.basket.api.BasketUpdatesUseCase
import com.javi.basket.api.DecrementProductOnBasketUseCase
import com.javi.basket.impl.AddProductToBasketUseCaseImpl
import com.javi.basket.impl.BasketUpdatesUseCaseImpl
import com.javi.basket.impl.DecrementProductOnBasketUseCaseImpl
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
    abstract fun bindBasketUpdatesUseCase(
        impl: BasketUpdatesUseCaseImpl
    ): BasketUpdatesUseCase

    @Binds
    @Singleton
    abstract fun bindAddProductToBasketUseCase(
        impl: AddProductToBasketUseCaseImpl
    ): AddProductToBasketUseCase

    @Binds
    @Singleton
    abstract fun bindDecrementProductOnBasketUseCase(
        impl: DecrementProductOnBasketUseCaseImpl
    ): DecrementProductOnBasketUseCase
}
