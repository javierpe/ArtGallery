package com.javi.dynamic.list.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.javi.dynamic.list.domain.database.converters.SkeletonConverter
import com.javi.dynamic.list.domain.database.skeletons.SkeletonsDao
import com.javi.dynamic.list.domain.database.skeletons.SkeletonsEntity

@Database(entities = [SkeletonsEntity::class], version = 1, exportSchema = false)
@TypeConverters(SkeletonConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun skeletonsDao(): SkeletonsDao
}