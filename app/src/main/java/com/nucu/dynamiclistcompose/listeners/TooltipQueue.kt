package com.nucu.dynamiclistcompose.listeners

import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import kotlinx.coroutines.flow.StateFlow

interface TooltipQueue {

    val onFinished: StateFlow<Boolean>

    fun add(tooltipAction: ScrollAction.ScrollWithTooltip)

    fun hasPendingTransactions(): Boolean

    fun clear()

    @Composable
    fun Tooltip()
}