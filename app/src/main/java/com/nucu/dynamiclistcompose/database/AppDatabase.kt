package com.nucu.dynamiclistcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nucu.dynamiclistcompose.database.converters.SkeletonConverter
import com.nucu.dynamiclistcompose.database.skeletons.SkeletonsDao
import com.nucu.dynamiclistcompose.database.skeletons.SkeletonsEntity

@Database(entities = [SkeletonsEntity::class], version = 1)
@TypeConverters(SkeletonConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun skeletonsDao(): SkeletonsDao
}