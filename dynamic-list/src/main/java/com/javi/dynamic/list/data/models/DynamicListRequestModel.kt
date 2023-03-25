package com.javi.dynamic.list.data.models

import com.javi.data.enums.ContextType

data class DynamicListRequestModel(
    val contextType: ContextType,
    val state: HashMap<String, String>? = null
)