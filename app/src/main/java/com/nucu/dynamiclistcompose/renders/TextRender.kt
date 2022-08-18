package com.nucu.dynamiclistcompose.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.components.text.TextModel
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.renders.base.RenderType
import javax.inject.Inject

class TextRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<TextModel> {

    override val renders: List<RenderType> = listOf(RenderType.TEXT)

    override suspend fun resolve(render: String, resource: JsonObject?): TextModel {
        return gson.fromJson(resource, TextModel::class.java)
    }
}