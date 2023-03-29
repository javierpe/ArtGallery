package com.javi.dynamic.list.data.models

import androidx.compose.runtime.MutableState
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.presentation.ui.base.DynamicListState

data class DynamicListRequestModel(
    val contextType: ContextType,
    val state: HashMap<String, String> = hashMapOf()
)