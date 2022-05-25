package com.nucu.dynamiclistcompose.ui.components.showCase.models

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.LayoutCoordinates
import com.nucu.dynamiclistcompose.models.tooltip.TooltipShowStrategy
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseStyle

data class ShowCaseTargets(
    val index: Int,
    val coordinates: LayoutCoordinates,
    val style: ShowCaseStyle = ShowCaseStyle.Default,
    val tooltipShowStrategy: TooltipShowStrategy = TooltipShowStrategy(),
    val key: String,
    val content: @Composable BoxScope.() -> Unit
)
