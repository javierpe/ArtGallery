package com.nucu.dynamiclistcompose.controllers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.nucu.dynamiclistcompose.animations.BlinkAnimation
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.api.TooltipPreferencesApi
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.models.DynamicListElement
import com.nucu.dynamiclistcompose.data.models.DynamicListShowCaseModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.DynamicListScreen
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Stack

abstract class DynamicListComposeController {

    abstract val delegates: MutableSet<@JvmSuppressWildcards DynamicListAdapterFactory>

    abstract val listeners: Set<@JvmSuppressWildcards DynamicListComponentListener>

    abstract val defaultDispatcher: CoroutineDispatcher

    abstract val tooltipPreferencesApi: TooltipPreferencesApi

    open fun getMapComponents(): List<ComponentItemModel> = data

    open fun getMapSkeletons(): List<RenderType> = skeletons

    private val _elements = MutableStateFlow<List<DynamicListElement>>(emptyList())
    private val elements: StateFlow<List<DynamicListElement>> = _elements

    private var showCaseSequence = Stack<DynamicListShowCaseModel>()

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
                val listener = listeners.firstOrNull {
                    it.render.value == component.render
                }

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
                        showCaseSequence.push(
                            DynamicListShowCaseModel(component.render, component.index)
                        )
                    }
                }

                DynamicListElement(
                    factory = adapter,
                    componentItemModel = component,
                    listener = listener
                )
            }
        }
    }

    @Composable
    fun DynamicListSkeletons() {
        BlinkAnimation {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
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

        val elements by elements.collectAsState()

        val listState = rememberLazyListState()

        DynamicListScreen(
            content = elements,
            listState = listState,
            widthSizeClass = widthSizeClass,
            onAction = onAction,
            showCaseState = showCaseState
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

        val showOnNextShowCase by showCaseState.currentIndex.collectAsState()

        val elements by elements.collectAsState()

        val coroutineScope = rememberCoroutineScope()

        if (sharedAction is ScrollAction.ScrollRender) {
            SideEffect {
                coroutineScope.launch {
                    val item = elements.firstOrNull { it.componentItemModel.render == sharedAction.renderType.value }

                    item?.let {
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

        if (showCaseSequence.isNotEmpty() && showOnNextShowCase == -1) {
            SideEffect {
                coroutineScope.launch {
                    delay(100)
                    val nextShowCase = showCaseSequence.pop()
                    showCaseState.setCurrentIndexFromDL(nextShowCase.index)
                    delay(500)
                    bodyListState.animateScrollToItem(nextShowCase.index)
                }
            }
        }
    }
}