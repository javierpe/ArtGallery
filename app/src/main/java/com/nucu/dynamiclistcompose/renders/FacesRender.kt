package com.nucu.dynamiclistcompose.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.components.faces.FacesModel
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.renders.base.RenderType
import javax.inject.Inject

class FacesRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<FacesModel> {

    override val renders: List<RenderType> = listOf(RenderType.FACES)

    override suspend fun resolve(render: String, resource: JsonObject?): FacesModel {
        return gson.fromJson(resource, FacesModel::class.java)
    }
}