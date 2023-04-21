package com.javi.dynamic.list.data.models

import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory

data class DynamicListElement(
    val factory: DynamicListFactory?,
    val componentItemModel: ComponentItemModel
)