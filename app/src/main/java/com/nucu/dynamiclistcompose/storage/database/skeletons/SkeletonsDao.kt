package com.nucu.dynamiclistcompose.storage.database.skeletons

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class SkeletonsDao {

    @Query("SELECT * FROM SkeletonsEntity WHERE context = :context")
    abstract fun provideSkeletonsByContext(context: String): SkeletonsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveSkeletonsByContext(skeletonsEntity: SkeletonsEntity)
}