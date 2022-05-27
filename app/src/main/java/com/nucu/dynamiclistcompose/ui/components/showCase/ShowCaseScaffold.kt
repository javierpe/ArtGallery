package com.nucu.dynamiclistcompose.ui.components.showCase

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.nucu.dynamiclistcompose.models.tooltip.TooltipShowStrategy

@Composable
fun IntroShowCaseScaffold(
    showIntroShowCase: Boolean,
    modifier: Modifier = Modifier,
    state: ShowCaseState = rememberShowCaseState(),
    content: @Composable ShowCaseScope.() -> Unit,
) {
    val scope = remember(state) {
        ShowCaseScope(state)
    }

    Box(modifier) {

        scope.content()

        if (showIntroShowCase) {
            ShowCase(
                state = state
            )
        }
    }
}

class ShowCaseScope(
    private val state: ShowCaseState,
) {

    /**
     * Modifier that marks Compose UI element as a target for [ShowCase]
     */
    fun Modifier.asShowCaseTarget(
        index: Int,
        style: ShowCaseStyle = ShowCaseStyle.Default,
        strategy: TooltipShowStrategy = TooltipShowStrategy(),
        key: String,
        content: @Composable BoxScope.() -> Unit,
    ): Modifier = asShowCaseTarget(
        state = state,
        index = index,
        style = style,
        content = content,
        strategy = strategy,
        key = key
    )
}
