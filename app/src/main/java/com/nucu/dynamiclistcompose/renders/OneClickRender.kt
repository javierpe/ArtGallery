package com.nucu.dynamiclistcompose.renders

import com.nucu.dynamiclistcompose.components.oneclick.OneClickModel
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender

class OneClickRender : DynamicListRender<OneClickModel> {

    override suspend fun resolve(render: String, resource: String?, source: String): OneClickModel {
        return OneClickModel("Hola")
    }
}