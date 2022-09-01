package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.data.renders.BannerCarouselRender
import com.nucu.dynamiclistcompose.data.renders.BannerRender
import com.nucu.dynamiclistcompose.data.renders.CardsRender
import com.nucu.dynamiclistcompose.data.renders.FacesRender
import com.nucu.dynamiclistcompose.data.renders.FiltersRender
import com.nucu.dynamiclistcompose.data.renders.MessageRender
import com.nucu.dynamiclistcompose.data.renders.ProfileRender
import com.nucu.dynamiclistcompose.data.renders.TextRender
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
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

    @Binds
    @IntoSet
    abstract fun bindFacesRender(
        render: FacesRender
    ): DynamicListRender<*>

    @Binds
    @IntoSet
    abstract fun bindProfileRender(
        render: ProfileRender
    ): DynamicListRender<*>
}