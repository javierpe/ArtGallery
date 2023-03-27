package com.javi.navigation.wiring

import com.javi.navigation.api.NavigationContractApi
import com.javi.navigation.api.NavigationDestinationsApi
import com.javi.navigation.impl.NavigationContractImpl
import com.javi.navigation.impl.NavigationDestinationsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationContractModule {

    @Binds
    @Singleton
    abstract fun bindNavigationContractApi(
        impl: NavigationContractImpl
    ): NavigationContractApi

    @Binds
    @Singleton
    abstract fun bindNavigationDestinations(
        impl: NavigationDestinationsImpl
    ): NavigationDestinationsApi
}