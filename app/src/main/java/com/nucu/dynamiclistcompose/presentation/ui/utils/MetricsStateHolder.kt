package com.nucu.dynamiclistcompose.presentation.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.metrics.performance.PerformanceMetricsState

@Composable
fun rememberMetricsStateHolder(): PerformanceMetricsState.Holder {
    val view = LocalView.current
    return remember(view) { PerformanceMetricsState.getHolderForHierarchy(view) }
}