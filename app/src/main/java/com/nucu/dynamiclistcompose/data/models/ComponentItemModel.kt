package com.nucu.dynamiclistcompose.data.models

data class ComponentItemModel(
    val name: String?,
    val render: String,
    val resolver: String?,
    val resource: Any,
    val context: String? = null,
    val nextContext: String? = null,
    val paginationResolver: String? = null,
    val state: HashMap<String, String>? = null,
    val index: Int,
    val uniqueId: String
)