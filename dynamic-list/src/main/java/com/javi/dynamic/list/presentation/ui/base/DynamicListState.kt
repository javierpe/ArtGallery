package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListRequestModel

@Composable
fun rememberDynamicListRequestState(
    requestModelCalls: @DisallowComposableCalls () -> DynamicListRequestModel
): MutableState<DynamicListRequestModel> {
    val request = requestModelCalls.invoke()
    return rememberSaveable(request) {
        mutableStateOf(request)
    }
}

sealed class DynamicListListener {

    object OnStartLoading : DynamicListListener()

    class OnHeaderItemsLoaded(
        val components: List<ComponentItemModel>
    ) : DynamicListListener()
}