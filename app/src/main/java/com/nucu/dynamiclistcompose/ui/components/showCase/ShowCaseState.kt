package com.nucu.dynamiclistcompose.ui.components.showCase

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.nucu.dynamiclistcompose.models.tooltip.TooltipShowStrategy
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShowCaseTargets
import java.util.Stack

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
    strategy: TooltipShowStrategy = TooltipShowStrategy(),
    key: String,
    content: @Composable BoxScope.() -> Unit,
): Modifier = onGloballyPositioned { coordinates ->

    val target = ShowCaseTargets(
        index = index,
        coordinates = coordinates,
        style = style,
        content = content,
        tooltipShowStrategy = strategy,
        key = key
    )

    if (!state.targetsQueue.contains(target)) {
        state.targetsQueue.push(target)
        state.hasTarget = state.targetsQueue.isNotEmpty()
    }
}

class ShowCaseState internal constructor() {

    internal val targetsQueue = Stack<ShowCaseTargets>()

    var hasTarget by mutableStateOf(false)
        internal set

    val currentTarget: ShowCaseTargets? get() = if (targetsQueue.isNotEmpty()) targetsQueue.peek() else null
}
