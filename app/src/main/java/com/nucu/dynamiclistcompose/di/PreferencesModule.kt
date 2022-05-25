package com.nucu.dynamiclistcompose.di

import com.nucu.dynamiclistcompose.api.TooltipPreferencesApi
import com.nucu.dynamiclistcompose.impl.TooltipPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindPreferencesService(
        impl: TooltipPreferencesImpl
    ): TooltipPreferencesApi
}