package com.nucu.dynamiclistcompose.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nucu.dynamiclistcompose.listeners.TooltipQueue
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import com.nucu.dynamiclistcompose.ui.components.showCase.DarkSkinComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class TooltipQueueImpl @Inject constructor(

): ViewModel(), TooltipQueue {

    private val queue = Stack<ScrollAction.ScrollWithTooltip>()
    private var currentTooltip: ScrollAction.ScrollWithTooltip? = null

    private val _onFinished = MutableStateFlow(false)
    override val onFinished: StateFlow<Boolean> = _onFinished

    private val _tooltipCallback = MutableStateFlow<(() -> Unit)?>(null)
    private val tooltipCallback: StateFlow<(() -> Unit)?> = _tooltipCallback

    private val _show = MutableStateFlow(false)
    private val show: StateFlow<Boolean> = _show

    override fun add(tooltipAction: ScrollAction.ScrollWithTooltip) {
        if (currentTooltip == null) {
            currentTooltip = tooltipAction
            executeCurrentTooltip()
        } else {
            queue.push(tooltipAction)
        }
    }

    override fun hasPendingTransactions(): Boolean {
        return queue.isNotEmpty()
    }

    override fun clear() {
        queue.clear()
        currentTooltip = null
    }

    private fun show() {
        viewModelScope.launch {
            delay(DEFAULT_SHOW_DELAY.toLong())
            _show.value = true
        }
    }

    private fun hide() {
        _show.value = false
    }

    private fun executeCurrentTooltip() {
        currentTooltip?.let {
            val tooltipShowStrategy = it.tooltipShowStrategy

            if (tooltipShowStrategy.firstToHappen == true) {
                showWhenFirstToHappen()
            } else {
                if (tooltipShowStrategy.showUntilUserInteraction == true) {
                    showUntilUserInteraction()
                } else {
                    showUntilExpiration()
                }
            }
        }
    }

    private fun showWhenFirstToHappen() {
        currentTooltip?.let {

            var isDelayFinished = false

            // Callback to finish
            _tooltipCallback.value = {
                if (isDelayFinished.not()) {
                    _show.value = false
                    executeNext()
                }
            }

            // Show tooltip
            show()

            viewModelScope.launch {
                // Delay time to finish
                delay((it.tooltipShowStrategy.expirationTime + DEFAULT_EXTRA_DURATION).toLong())

                // Hide tooltip
                hide()

                // Set finished to prevent execute next again
                isDelayFinished = true

                // Execute next tooltip
                executeNext()
            }
        }
    }

    private fun showUntilExpiration() {
        currentTooltip?.let {
            // Show tooltip
            show()

            viewModelScope.launch {
                // Delay time to finish
                delay((it.tooltipShowStrategy.expirationTime + DEFAULT_EXTRA_DURATION).toLong())

                // Hide tooltip
                hide()

                // Execute next tooltip
                executeNext()
            }
        }
    }

    private fun showUntilUserInteraction() {
        // Show tooltip
        show()

        // Callback to finish
        _tooltipCallback.value = {

            // Hide tooltip
            hide()

            // Execute next
            executeNext()
        }
    }

    private fun executeNext() {
        if (hasPendingTransactions()) {
            currentTooltip = queue.pop()
            viewModelScope.launch {
                executeCurrentTooltip()
            }
        } else {
            _onFinished.value = true
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Tooltip() {
        val showState = show.collectAsState()

        if (showState.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { tooltipCallback.value?.invoke() }
            ) {
                currentTooltip?.let {
                    DarkSkinComponent(
                        layoutCoordinates = it.coordinates,
                        it.shapeRadius,
                        it.message
                    )
                }
            }
        }
    }

    companion object {
        const val DEFAULT_EXTRA_DURATION = 100
        const val DEFAULT_SHOW_DELAY = 500
    }
}