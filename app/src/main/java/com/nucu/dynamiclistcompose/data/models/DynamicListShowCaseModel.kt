package com.nucu.dynamiclistcompose.data.models

data class DynamicListShowCaseModel(
    val render: String,
    val index: Int,
    val isFlagElement: Boolean = false
)