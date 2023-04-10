package com.javi.cards.page.wiring

import com.javi.cards.page.api.GetCardsPageUseCase
import com.javi.cards.page.impl.pageLoader.GetCardsPageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CardsPageLoaderModule {

    @Binds
    @Singleton
    abstract fun bindLoader(
        impl: GetCardsPageUseCaseImpl
    ): GetCardsPageUseCase
}