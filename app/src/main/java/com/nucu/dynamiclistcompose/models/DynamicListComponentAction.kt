package com.nucu.dynamiclistcompose.models

import com.nucu.dynamiclistcompose.renders.base.RenderType

interface DynamicListComponentAction {
    fun moveToFirstRender(renderType: RenderType)
    fun addToolTip(message: String)
}