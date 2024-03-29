package com.javi.dynamic.list.domain.database.skeletons

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javi.render.processor.core.RenderType

@Entity
data class SkeletonsEntity(
    @PrimaryKey val context: String,
    val renders: List<RenderType>
)
