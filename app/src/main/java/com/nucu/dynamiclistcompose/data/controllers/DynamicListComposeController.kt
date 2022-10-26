package com.nucu.dynamiclistcompose.data.controllers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.render.processor.RenderType
import com.nucu.dynamiclistcompose.data.actions.ScrollAction
import com.nucu.dynamiclistcompose.data.api.TooltipPreferencesApi
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.models.DynamicListElement
import com.nucu.dynamiclistcompose.data.models.DynamicListShowCaseModel
import com.nucu.dynamiclistcompose.presentation.ui.animations.BlinkAnimation
import com.nucu.dynamiclistcompose.presentation.ui.base.DynamicListScreen
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@OptIn(ExperimentalLifecycleComposeApi::class)
abstract class DynamicListComposeController {

    abstract val delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>

    abstract val defaultDispatcher: CoroutineDispatcher

    abstract val tooltipPreferencesApi: TooltipPreferencesApi

    open fun getMapComponents(): List<ComponentItemModel> = data

    open fun getMapSkeletons(): List<RenderType> = skeletons

    private val _elements = MutableStateFlow<List<DynamicListElement>>(emptyList())
    private val elements: StateFlow<List<DynamicListElement>> = _elements.asStateFlow()

    private var showCaseFinished = false

    private var showCaseSequence: Queue<DynamicListShowCaseModel> = LinkedList()

    var data: List<ComponentItemModel> = listOf()

    var skeletons: List<RenderType> = listOf()

    suspend fun dispatch(components: List<ComponentItemModel>) {
        data = components
        transform()
    }

    open fun dispatchSkeletons(renderTypes: List<RenderType>) {
        skeletons = renderTypes
    }

    private suspend fun transform() {
        withContext(defaultDispatcher) {
            _elements.value = getMapComponents().map { component ->
                val adapter = delegates.firstOrNull { adapterFactory ->
                    adapterFactory.renders.any { renderType ->
                        renderType.value == component.render
                    }
                }

                if (
                    adapter?.hasShowCaseConfigured == true &&
                    showCaseSequence.none { it.render == component.render }
                ) {
                    val alreadyShowed = tooltipPreferencesApi.getState(
                        booleanPreferencesKey(component.render),
                        false
                    ).first()

                    if (alreadyShowed.not()) {
                        // Add to sequence
                        showCaseSequence.add(
                            DynamicListShowCaseModel(component.render, component.index)
                        )
                    }
                }

                DynamicListElement(
                    factory = adapter,
                    componentItemModel = component
                )
            }

            showCaseSequence.add(
                DynamicListShowCaseModel("", 0, true)
            )
        }
    }

    @Composable
    fun DynamicListSkeletons() {
        BlinkAnimation {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                getMapSkeletons().forEach { render ->
                    delegates.filter { adapterFactory ->
                        adapterFactory.renders.any { renderType ->
                            renderType.value == render.value
                        }
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
        widthSizeClass: WindowWidthSizeClass,
        showCaseState: ShowCaseState,
        onAction: (ScrollAction) -> Unit
    ) {

        val elements by elements.collectAsStateWithLifecycle()

        val listState = rememberLazyListState()

        DynamicListScreen(
            content = elements,
            listState = listState,
            widthSizeClass = widthSizeClass,
            onAction = onAction,
            showCaseState = showCaseState,
            withVerticalPadding = false
        )
    }

    @Composable
    fun ComposeBody(
        widthSizeClass: WindowWidthSizeClass,
        sharedAction: ScrollAction? = null,
        showCaseState: ShowCaseState,
        bodyListState: LazyListState,
        onAction: (ScrollAction) -> Unit
    ) {

        val showOnNextShowCase by showCaseState.currentIndex.collectAsStateWithLifecycle()

        val elements by elements.collectAsStateWithLifecycle()

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
            content = elements,
            listState = bodyListState,
            widthSizeClass = widthSizeClass,
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