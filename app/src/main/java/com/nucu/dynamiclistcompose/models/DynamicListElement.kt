package com.nucu.dynamiclistcompose.models

import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener

data class DynamicListElement(
    val factory: DynamicListAdapterFactory?,
    val componentItemModel: ComponentItemModel,
    val listener: DynamicListComponentListener?
)