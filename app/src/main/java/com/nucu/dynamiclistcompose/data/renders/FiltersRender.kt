package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.presentation.components.filters.FiltersModel
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import javax.inject.Inject

class FiltersRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<FiltersModel> {

    override val renders: List<RenderType> = listOf(RenderType.FILTERS)

    override suspend fun resolve(render: String, resource: JsonObject?): FiltersModel {
        return gson.fromJson(resource, FiltersModel::class.java)
    }
}