package com.nucu.dynamiclistcompose.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.components.filters.FiltersModel
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.renders.base.RenderType
import javax.inject.Inject

class FiltersRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<FiltersModel> {

    override val renders: List<RenderType> = listOf(RenderType.FILTERS)

    override suspend fun resolve(render: String, resource: JsonObject?): FiltersModel {
        return gson.fromJson(resource, FiltersModel::class.java)
    }
}