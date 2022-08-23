package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.presentation.components.faces.FacesModel
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import javax.inject.Inject

class FacesRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<FacesModel> {

    override val renders: List<RenderType> = listOf(RenderType.FACES)

    override suspend fun resolve(render: String, resource: JsonObject?): FacesModel {
        return gson.fromJson(resource, FacesModel::class.java)
    }
}