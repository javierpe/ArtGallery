package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.factories.BannerCarouselFactory
import com.nucu.dynamiclistcompose.factories.BannerFactory
import com.nucu.dynamiclistcompose.factories.FiltersFactory
import com.nucu.dynamiclistcompose.factories.TextFactory
import com.nucu.dynamiclistcompose.factories.CardsFactory
import com.nucu.dynamiclistcompose.factories.MessageFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class DynamicListFactoriesModule {

    @Binds
    @IntoSet
    abstract fun bindCardsFactory(
        factory: CardsFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindBannerFactory(
        factory: BannerFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindMessageFactory(
        factory: MessageFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindFiltersFactory(
        factory: FiltersFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindTextFactory(
        factory: TextFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindBannerCarousel(
        factory: BannerCarouselFactory
    ): DynamicListAdapterFactory
}