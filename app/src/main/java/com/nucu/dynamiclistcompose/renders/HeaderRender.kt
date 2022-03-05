package com.nucu.dynamiclistcompose.renders

import com.nucu.dynamiclistcompose.components.header.HeaderModel
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender

class HeaderRender : DynamicListRender<HeaderModel> {

    override suspend fun resolve(render: String, resource: String?, source: String): HeaderModel {
        return HeaderModel("Dynamic List Compose")
    }
}