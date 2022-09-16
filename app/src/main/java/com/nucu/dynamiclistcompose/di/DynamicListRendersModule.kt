package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.data.renders.BannerCarouselRender
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
    abstract fun bindBannerCarouselRender(
        render: BannerCarouselRender
    ): DynamicListRender<*>
}