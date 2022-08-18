package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.renders.BannerCarouselRender
import com.nucu.dynamiclistcompose.renders.BannerRender
import com.nucu.dynamiclistcompose.renders.FiltersRender
import com.nucu.dynamiclistcompose.renders.TextRender
import com.nucu.dynamiclistcompose.renders.CardsRender
import com.nucu.dynamiclistcompose.renders.MessageRender
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class DynamicListRendersModule {

    @Binds
    @IntoSet
    abstract fun bindCardsRender(
        render: CardsRender
    ): DynamicListRender<*>

    @Binds
    @IntoSet
    abstract fun bindBannerRender(
        render: BannerRender
    ): DynamicListRender<*>

    @Binds
    @IntoSet
    abstract fun bindMessageRender(
        render: MessageRender
    ): DynamicListRender<*>

    @Binds
    @IntoSet
    abstract fun bindTextRender(
        render: TextRender
    ): DynamicListRender<*>

    @Binds
    @IntoSet
    abstract fun bindFiltersRender(
        render: FiltersRender
    ): DynamicListRender<*>

    @Binds
    @IntoSet
    abstract fun bindBannerCarouselRender(
        render: BannerCarouselRender
    ): DynamicListRender<*>
}