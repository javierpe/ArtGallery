package com.nucu.dynamiclistcompose.models

data class DynamicListContainer(
    val headers: List<ComponentItemModel>,
    val bodies: List<ComponentItemModel>,
    val footers: List<ComponentItemModel>
)
