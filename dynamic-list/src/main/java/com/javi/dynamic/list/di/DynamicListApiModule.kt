package com.javi.dynamic.list.di

import com.javi.dynamic.list.data.dataSources.DynamicListRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DynamicListApiModule {

    @Singleton
    @Provides
    fun provideDynamicListService(retrofit: Retrofit): DynamicListRemote {
        return retrofit.create(DynamicListRemote::class.java)
    }
}
