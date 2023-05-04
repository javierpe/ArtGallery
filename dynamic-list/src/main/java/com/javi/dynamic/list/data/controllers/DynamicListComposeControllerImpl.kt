package com.javi.dynamic.list.data.controllers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.design.system.animations.BlinkAnimation
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.javi.dynamic.list.data.actions.ScrollAction
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.domain.models.DynamicListShowCaseModel
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.dynamic.list.presentation.ui.base.DynamicListScreen
import com.javi.render.processor.core.RenderType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue

abstract class DynamicListComposeControllerImpl {

    abstract val delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>

    private var showCaseFinished = false

    private var showCaseSequence: Queue<DynamicListShowCaseModel> = LinkedList()

    private var skeletons: List<RenderType> = listOf()

    fun dispatchShowCaseSequence(sequence: Queue<DynamicListShowCaseModel>) {
        showCaseSequence = sequence
    }

    @Composable
    fun ComposeSkeletons(
        skeletonRenderList: List<RenderType>
    ) {
        BlinkAnimation {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                skeletonRenderList.forEach { render ->
                    delegates.filter { adapterFactory ->
                        adapterFactory.render.value == render.value
                    }.forEach {
                        it.CreateSkeleton()
                    }
                }
            }
        }

        skeletons = emptyList()
    }

    @Composable
    fun ComposeHeader(
        modifier: Modifier,
        elements: List<DynamicListElement>,
        dynamicListObject: DynamicListObject,
        showCaseState: ShowCaseState,
        onAction: (ScrollAction) -> Unit
    ) {
        val listState = rememberLazyListState()

        DynamicListScreen(
            modifier = modifier,
            content = elements,
            listState = listState,
            dynamicListObject = dynamicListObject,
            onAction = onAction,
            showCaseState = showCaseState,
            withVerticalPadding = false
        )
    }

    @Suppress("LongParameterList")
    @Composable
    fun ComposeBody(
        modifier: Modifier,
        elements: List<DynamicListElement>,
        dynamicListObject: DynamicListObject,
        sharedAction: ScrollAction? = null,
        showCaseState: ShowCaseState,
        bodyListState: LazyListState,
        onAction: (ScrollAction) -> Unit
    ) {
        val showOnNextShowCase by showCaseState.currentIndex.collectAsStateWithLifecycle()

        val coroutineScope = rememberCoroutineScope()

        if (sharedAction is ScrollAction.ScrollRender) {
            SideEffect {
                coroutineScope.launch {
                    elements.firstOrNull {
                        it.componentItemModel.render == sharedAction.renderType.value
                    }?.let {
                        bodyListState.animateScrollToItem(
                            elements.indexOf(it)
                        )
                    }
                }
            }
        } else if (sharedAction is ScrollAction.ScrollIndex) {
            SideEffect {
                coroutineScope.launch {
                    bodyListState.animateScrollToItem(sharedAction.index)
                }
            }
        }

        DynamicListScreen(
            modifier = modifier,
            content = elements,
            listState = bodyListState,
            dynamicListObject = dynamicListObject,
            showCaseState = showCaseState,
            onAction = onAction,
        )

        if (showCaseSequence.isNotEmpty() && showOnNextShowCase == -1 && showCaseFinished.not()) {
            SideEffect {
                coroutineScope.launch {
                    delay(SHOW_CASE_START_DELAY)
                    showCaseSequence.poll()?.let {
                        if (it.isFlagElement) {
                            // Go to top when show case queue is finished
                            showCaseSequence.poll()
                            coroutineScope.launch {
                                bodyListState.animateScrollToItem(0)
                            }
                            showCaseFinished = true
                        } else {
                            // Show next showCase target
                            showCaseState.setCurrentIndexFromDL(it.index)
                            delay(SHOW_CASE_END_DELAY)
                            bodyListState.animateScrollToItem(it.index)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val SHOW_CASE_START_DELAY: Long = 100
        private const val SHOW_CASE_END_DELAY: Long = 500
    }
}
