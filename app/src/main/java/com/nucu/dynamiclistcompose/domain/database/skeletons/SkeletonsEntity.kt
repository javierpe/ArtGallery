package com.nucu.dynamiclistcompose.domain.database.skeletons

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nucu.dynamiclistcompose.data.renders.base.RenderType

@Entity
data class SkeletonsEntity(
    @PrimaryKey val context: String,
    val renders: List<RenderType>
)