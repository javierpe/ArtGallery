package com.nucu.dynamiclistcompose.data.models

import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.listeners.DynamicListComponentListener

data class DynamicListElement(
    val factory: DynamicListFactory?,
    val componentItemModel: ComponentItemModel
)