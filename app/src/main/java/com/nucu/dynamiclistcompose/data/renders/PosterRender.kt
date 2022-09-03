package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.components.poster.PosterModel
import javax.inject.Inject

class PosterRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<PosterModel> {

    override val renders: List<RenderType> = listOf(RenderType.POSTER)

    override suspend fun resolve(render: String, resource: JsonObject?): PosterModel {
        return gson.fromJson(resource, PosterModel::class.java)
    }
}