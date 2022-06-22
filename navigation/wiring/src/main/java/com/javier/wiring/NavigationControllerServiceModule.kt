package com.javier.wiring

import com.javier.api.NavigationController
import com.javier.impl.NavigationControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationControllerServiceModule {

    @Binds
    @Singleton
    abstract fun bindNavigationControllerService(
        impl: NavigationControllerImpl
    ): NavigationController
}