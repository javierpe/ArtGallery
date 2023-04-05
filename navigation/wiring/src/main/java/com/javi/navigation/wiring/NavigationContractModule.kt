package com.javi.navigation.wiring

import com.javi.navigation.api.NavigationApi
import com.javi.navigation.impl.NavigationImpl
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
        impl: NavigationImpl
    ): NavigationApi
}