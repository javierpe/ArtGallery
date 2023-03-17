package com.nucu.dynamiclistcompose.data.models

data class DynamicListRequestModel(
    val contextType: ContextType,
    val state: HashMap<String, String>? = null
)