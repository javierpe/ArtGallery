package com.nucu.dynamiclistcompose.ui.components.showCase

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.nucu.dynamiclistcompose.data.models.tooltip.ShowCaseStrategy
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShowCaseTargets
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Creates a [ShowCaseState] that is remembered across compositions.
 */
@Composable
fun rememberShowCaseState(): ShowCaseState {
    return remember {
        ShowCaseState()
    }
}

/**
 * Modifier that marks Compose UI element as a target for [ShowCase]
 */
fun Modifier.asShowCaseTarget(
    state: ShowCaseState,
    index: Int,
    style: ShowCaseStyle = ShowCaseStyle.Default,
    strategy: ShowCaseStrategy = ShowCaseStrategy(),
    key: String,
    content: @Composable BoxScope.() -> Unit,
): Modifier = onGloballyPositioned { coordinates ->

    val sendState by derivedStateOf {
        state.currentIndex.value == index
    }

    if (sendState) {
        state.send(
            ShowCaseTargets(
                index = index,
                coordinates = coordinates,
                style = style,
                content = content,
                tooltipShowStrategy = strategy,
                key = key,
                onNext = {
                    state.onNext()
                }
            )
        )
    }
}

class ShowCaseState internal constructor() {

    private val _current = MutableStateFlow<ShowCaseTargets?>(null)
    val current: StateFlow<ShowCaseTargets?> = _current

    private val _currentIndex = MutableStateFlow(-1)
    val currentIndex: StateFlow<Int> = _currentIndex

    fun send(target: ShowCaseTargets?) {
        _current.value = target
    }

    fun setCurrentIndexFromDL(index: Int) {
        _currentIndex.value = index
    }

    fun onNext() {
        _currentIndex.value = -1
        _current.value = null
    }
}
