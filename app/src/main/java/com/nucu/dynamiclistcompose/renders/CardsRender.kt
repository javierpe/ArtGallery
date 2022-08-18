package com.nucu.dynamiclistcompose.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.components.card.CardsModel
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.renders.base.RenderType
import javax.inject.Inject

class CardsRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<CardsModel> {

    override val renders: List<RenderType> = listOf(RenderType.CARDS)

    override suspend fun resolve(render: String, resource: JsonObject?): CardsModel {
        return gson.fromJson(resource, CardsModel::class.java)
    }
}