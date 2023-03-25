package com.javi.wiring

import com.javi.api.TooltipPreferencesApi
import com.javi.impl.TooltipPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    @Singleton
    abstract fun bindPreferencesService(
        impl: TooltipPreferencesImpl
    ): TooltipPreferencesApi
}