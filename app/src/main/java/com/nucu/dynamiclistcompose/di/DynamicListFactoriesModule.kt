package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.data.factories.BannerCarouselFactory
import com.nucu.dynamiclistcompose.data.factories.BannerFactory
import com.nucu.dynamiclistcompose.data.factories.CardsFactory
import com.nucu.dynamiclistcompose.data.factories.FacesFactory
import com.nucu.dynamiclistcompose.data.factories.FiltersFactory
import com.nucu.dynamiclistcompose.data.factories.MessageFactory
import com.nucu.dynamiclistcompose.data.factories.ProfileFactory
import com.nucu.dynamiclistcompose.data.factories.TextFactory
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
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
    ): DynamicListFactory

    @Binds
    @IntoSet
    abstract fun bindBannerFactory(
        factory: BannerFactory
    ): DynamicListFactory

    @Binds
    @IntoSet
    abstract fun bindMessageFactory(
        factory: MessageFactory
    ): DynamicListFactory

    @Binds
    @IntoSet
    abstract fun bindFiltersFactory(
        factory: FiltersFactory
    ): DynamicListFactory

    @Binds
    @IntoSet
    abstract fun bindTextFactory(
        factory: TextFactory
    ): DynamicListFactory

    @Binds
    @IntoSet
    abstract fun bindBannerCarouselFactory(
        factory: BannerCarouselFactory
    ): DynamicListFactory

    @Binds
    @IntoSet
    abstract fun bindFacesFactory(
        factory: FacesFactory
    ): DynamicListFactory

    @Binds
    @IntoSet
    abstract fun bindProfileFactory(
        factory: ProfileFactory
    ): DynamicListFactory
}