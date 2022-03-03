package com.nucu.dynamiclistcompose.renders

import com.nucu.dynamiclistcompose.components.banner.BannerModel
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender

class BannerRender : DynamicListRender<BannerModel> {

    override suspend fun resolve(render: String, resource: String?, source: String): BannerModel {
        return BannerModel("Hola")
    }
}