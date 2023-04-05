package com.javi.home.wiring

import com.javi.home.api.HomePageLoader
import com.javi.home.impl.pageLoader.HomePageLoaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomePageLoaderModule {

    @Binds
    @Singleton
    abstract fun bindLoader(
        impl: HomePageLoaderImpl
    ): HomePageLoader
}