package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.presentation.components.message.MessageModel
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import javax.inject.Inject

class MessageRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<MessageModel> {

    override val renders: List<RenderType> = listOf(RenderType.MESSAGE)

    override suspend fun resolve(render: String, resource: JsonObject?): MessageModel {
        return gson.fromJson(resource, MessageModel::class.java)
    }
}