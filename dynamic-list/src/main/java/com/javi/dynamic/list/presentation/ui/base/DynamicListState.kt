package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.javi.data.enums.ContextType
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

fun MutableState<DynamicListRequestModel>.update(
    context: ContextType? = null,
    state: HashMap<String, String> = hashMapOf()
) {
    val newState = state.takeIf { it.isNotEmpty() }?.let {
        this.value.state.apply {
            putAll(it)
        }
    } ?: this.value.state

    val newContextType = context ?: this.value.contextType

    this.value = this.value.copy(
        contextType = newContextType,
        state = newState
    )
}

sealed class DynamicListStateListener {

    object OnStartLoading : DynamicListStateListener()

    object OnContextLoaded : DynamicListStateListener()

    class OnHeaderItemsLoaded(
        val components: List<ComponentItemModel>
    ) : DynamicListStateListener()

    class OnBodyItemsLoaded(
        val components: List<ComponentItemModel>
    ) : DynamicListStateListener()
}
