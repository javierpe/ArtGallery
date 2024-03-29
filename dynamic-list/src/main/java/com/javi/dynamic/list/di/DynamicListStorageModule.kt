package com.javi.dynamic.list.di

import android.content.Context
import androidx.room.Room
import com.javi.dynamic.list.domain.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DynamicListStorageModule {

    @Provides
    fun provideStorage(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "dynamic-list-database"
    ).build()
}