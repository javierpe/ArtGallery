package com.nucu.dynamiclistcompose.controllers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.BlinkAnimation
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListElement
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.DynamicListScreen
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class DynamicListComposeController {

    abstract val delegates: MutableSet<@JvmSuppressWildcards DynamicListAdapterFactory>

    abstract val listeners: Set<@JvmSuppressWildcards DynamicListComponentListener>

    open fun getMapComponents(): List<ComponentItemModel> = data

    private val dataElements = MutableStateFlow<List<DynamicListElement>>(emptyList())
    private val elements: StateFlow<List<DynamicListElement>> = dataElements

    open fun getMapSkeletons(): List<RenderType> = skeletons

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
        withContext(Dispatchers.Default) {
            dataElements.value = getMapComponents().map { component ->
                val listener = listeners.firstOrNull {
                    it.render.value == component.render
                }

                val adapter = delegates.firstOrNull { adapterFactory ->
                    adapterFactory.renders.any { renderType ->
                        renderType.value == component.render
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
        onAction: (ScrollAction) -> Unit
    ) {

        val elements by elements.collectAsState()

        val listState = rememberLazyListState()

        DynamicListScreen(
            content = elements,
            listState = listState,
            onAction = onAction
        )
    }

    @Composable
    fun ComposeBody(
        sharedAction: ScrollAction? = null
    ) {

        val elements by elements.collectAsState()

        val coroutineScope = rememberCoroutineScope()

        val listState = rememberLazyListState()

        if (sharedAction is ScrollAction.ScrollRender) {
            SideEffect {
                coroutineScope.launch {
                    val item = elements.firstOrNull { it.componentItemModel.render == sharedAction.renderType.value }

                    item?.let {
                        listState.animateScrollToItem(
                            elements.indexOf(it)
                        )
                    }
                }
            }
        } else if (sharedAction is ScrollAction.ScrollIndex) {
            SideEffect {
                coroutineScope.launch {
                    listState.animateScrollToItem(sharedAction.index)
                }
            }
        }

        DynamicListScreen(
            elements,
            listState
        )
    }

    private fun LazyListState.isToBottom(listSize: Int): Boolean {
        return (listSize - 1) - firstVisibleItemIndex == layoutInfo.visibleItemsInfo.size - 1
    }
}