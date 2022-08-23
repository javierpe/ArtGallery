package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import javax.inject.Inject

class BannerRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<BannerModel> {

    override val renders: List<RenderType> = listOf(RenderType.BANNER)

    override suspend fun resolve(render: String, resource: JsonObject?): BannerModel {
        return gson.fromJson(resource, BannerModel::class.java)
    }
}