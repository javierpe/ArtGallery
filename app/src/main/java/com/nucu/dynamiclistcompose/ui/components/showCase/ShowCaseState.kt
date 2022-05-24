package com.nucu.dynamiclistcompose.ui.components.showCase

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShowCaseTargets

/**
 * Creates a [ShowCaseState] that is remembered across compositions.
 *
 * Changes to the provided values for [initialIndex] will **not** result in the state being
 * recreated or changed in any way if it has already
 * been created.
 *
 * @param initialIndex the initial value for [ShowCaseState.currentTargetIndex]
 */
@Composable
fun rememberShowCaseState(
    initialIndex: Int = 0,
): ShowCaseState {
    return remember {
        ShowCaseState(
            initialIndex = initialIndex,
        )
    }
}

/**
 * Modifier that marks Compose UI element as a target for [ShowCase]
 */
fun Modifier.asShowCaseTarget(
    state: ShowCaseState,
    index: Int,
    style: ShowCaseStyle = ShowCaseStyle.Default,
    content: @Composable BoxScope.() -> Unit,
): Modifier = onGloballyPositioned { coordinates ->
    state.targets[index] = ShowCaseTargets(
        index = index,
        coordinates = coordinates,
        style = style,
        content = content
    )
}

class ShowCaseState internal constructor(
    initialIndex: Int,
) {

    internal var targets = mutableStateMapOf<Int, ShowCaseTargets>()

    var currentTargetIndex by mutableStateOf(initialIndex)
        internal set

    val currentTarget: ShowCaseTargets?
        get() = targets[currentTargetIndex]
}
