package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.factories.BannerFactory
import com.nucu.dynamiclistcompose.factories.FiltersFactory
import com.nucu.dynamiclistcompose.factories.HeaderFactory
import com.nucu.dynamiclistcompose.factories.OneClickReorderFactory
import com.nucu.dynamiclistcompose.factories.TobaccoFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class DynamicListFactoriesModule {

    @Binds
    @IntoSet
    abstract fun bindOneClickReorderFactory(
        factory: OneClickReorderFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindBannerFactory(
        factory: BannerFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindTobaccoFactory(
        factory: TobaccoFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindFiltersFactory(
        factory: FiltersFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindHeaderFactory(
        factory: HeaderFactory
    ): DynamicListAdapterFactory
}