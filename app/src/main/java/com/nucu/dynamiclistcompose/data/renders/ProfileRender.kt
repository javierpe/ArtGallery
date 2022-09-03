package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.components.profile.ProfileModel
import javax.inject.Inject

class ProfileRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<ProfileModel> {

    override val renders: List<RenderType> = listOf(RenderType.PROFILE)

    override suspend fun resolve(render: String, resource: JsonObject?): ProfileModel {
        return gson.fromJson(resource, ProfileModel::class.java)
    }
}