package com.javi.cards.page.wiring

import com.javi.cards.page.api.CardsPageLoader
import com.javi.cards.page.impl.pageLoader.CardsPageLoaderImpl
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
        impl: CardsPageLoaderImpl
    ): CardsPageLoader
}