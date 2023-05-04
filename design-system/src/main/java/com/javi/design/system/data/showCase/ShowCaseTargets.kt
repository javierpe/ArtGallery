package com.javi.design.system.data.showCase

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.LayoutCoordinates
import com.javi.design.system.molecules.showCase.ShowCaseStyle

data class ShowCaseTargets(
    val index: Int,
    val coordinates: LayoutCoordinates,
    val style: ShowCaseStyle = ShowCaseStyle.Default,
    val tooltipShowStrategy: ShowCaseStrategy = ShowCaseStrategy(),
    val key: String,
    val onNext: () -> Unit,
    val content: @Composable BoxScope.() -> Unit
)
