package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListRequestModel

@Composable
fun rememberDynamicListRequestState(
    requestModelCalls: @DisallowComposableCalls () -> DynamicListRequestModel
): DynamicListState {
    val request = requestModelCalls.invoke()
    return remember(request) {
        DynamicListState(request)
    }
}

@Stable
class DynamicListState internal constructor(
    dynamicListRequestModel: DynamicListRequestModel
) {
    val requestModel = mutableStateOf(dynamicListRequestModel)
}

sealed class DynamicListStateAction {

    object OnStartLoading : DynamicListStateAction()

    class OnHeaderItemsLoaded(
        val components: List<ComponentItemModel>
    ) : DynamicListStateAction()
}