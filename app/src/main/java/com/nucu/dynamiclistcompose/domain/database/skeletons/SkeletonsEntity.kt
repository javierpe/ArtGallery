package com.nucu.dynamiclistcompose.domain.database.skeletons

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javi.render.data.RenderType

@Entity
data class SkeletonsEntity(
    @PrimaryKey val context: String,
    val renders: List<RenderType>
)
