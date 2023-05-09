package com.javi.dynamic.list.data.models

data class ComponentItemModel(
    val render: String,
    val index: Int,
    val resource: Any,
    val isChanged: Boolean = false
)