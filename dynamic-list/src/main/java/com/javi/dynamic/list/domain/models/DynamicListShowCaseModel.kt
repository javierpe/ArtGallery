package com.javi.dynamic.list.domain.models

data class DynamicListShowCaseModel(
    val render: String,
    val index: Int,
    val isFlagElement: Boolean = false
)
