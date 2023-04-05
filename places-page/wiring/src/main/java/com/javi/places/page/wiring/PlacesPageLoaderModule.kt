package com.javi.places.page.wiring

import com.javi.places.page.api.PlacesPageLoader
import com.javi.places.page.impl.pageLoader.PlacesPageLoaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlacesPageLoaderModule {

    @Binds
    @Singleton
    abstract fun bindLoader(
        impl: PlacesPageLoaderImpl
    ): PlacesPageLoader
}