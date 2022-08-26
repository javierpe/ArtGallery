package com.nucu.dynamiclistcompose.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nucu.dynamiclistcompose.domain.database.converters.SkeletonConverter
import com.nucu.dynamiclistcompose.domain.database.skeletons.SkeletonsDao
import com.nucu.dynamiclistcompose.domain.database.skeletons.SkeletonsEntity

@Database(entities = [SkeletonsEntity::class], version = 1)
@TypeConverters(SkeletonConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun skeletonsDao(): SkeletonsDao
}