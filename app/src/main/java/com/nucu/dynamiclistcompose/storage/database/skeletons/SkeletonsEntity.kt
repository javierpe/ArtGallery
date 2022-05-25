package com.nucu.dynamiclistcompose.storage.database.skeletons

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nucu.dynamiclistcompose.renders.base.RenderType

@Entity
data class SkeletonsEntity(
    @PrimaryKey val context: String,
    val renders: List<RenderType>
)