package com.nucu.dynamiclistcompose.controllers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import com.nucu.dynamiclistcompose.ui.components.showCase.DarkSkinComponent
import com.nucu.dynamiclistcompose.viewModels.DynamicListComposeViewModel
import kotlinx.coroutines.launch

abstract class DynamicListComposeController {

    abstract val delegates: MutableSet<@JvmSuppressWildcards DynamicListAdapterFactory>

    abstract val listeners: Set<@JvmSuppressWildcards DynamicListComponentListener>

    open fun getMapComponents(): List<ComponentItemModel> = data

    open fun getMapSkeletons(): List<RenderType> = skeletons

    var data: List<ComponentItemModel> = listOf()
    var skeletons: List<RenderType> = listOf()

    open fun dispatch(components: List<ComponentItemModel>) {
        data = components
    }

    open fun dispatchSkeletons(renderTypes: List<RenderType>) {
        skeletons = renderTypes
    }

    @Composable
    fun DynamicListSkeletons() {
        getMapSkeletons().forEach { render ->
            delegates.filter { it.renders.any { it.value == render.value } }.forEach {
                it.CreateSkeleton()
            }
        }
        skeletons = emptyList()
    }

    @Composable
    fun ComposeComponent(
        viewModel: DynamicListComposeViewModel = hiltViewModel()
    ) {

        val scrollListState = rememberLazyListState()

        val coroutine = rememberCoroutineScope()

        val coordinates = remember {
            mutableStateOf<LayoutCoordinates?>(null)
        }

        val shapeRadius = remember {
            mutableStateOf(10.dp)
        }

        val scrollActionState = viewModel.scrollAction.collectAsState()

        when (scrollActionState.value) {
            is ScrollAction.ScrollIndex -> {
                SideEffect {
                    coroutine.launch {
                        scrollListState.animateScrollToItem((scrollActionState.value as ScrollAction.ScrollIndex).index)
                    }
                }
            }

            is ScrollAction.ScrollRender -> {
                SideEffect {
                    coroutine.launch {
                        val render = (scrollActionState.value as ScrollAction.ScrollRender).renderType
                        val element = getMapComponents().firstOrNull() {
                            it.render == render.value
                        }

                        element?.let {
                            getMapComponents().indexOf(element).let {
                                scrollListState.animateScrollToItem(it)
                            }
                        }
                    }
                }
            }

            is ScrollAction.ScrollWithTooltip -> {
                SideEffect {
                    coroutine.launch {
                        val scrollAction = (scrollActionState.value as ScrollAction.ScrollWithTooltip)
                        val element = getMapComponents().firstOrNull() {
                            it.render == scrollAction.renderType.value
                        }

                        element?.let {
                            getMapComponents().indexOf(element).let {
                                scrollListState.animateScrollToItem(it)

                                shapeRadius.value = scrollAction.shapeRadius
                                coordinates.value = scrollAction.coordinates
                            }
                        }
                    }
                }
            }

            else -> { }
        }

        Box {
            if (coordinates.value != null) {
                DarkSkinComponent(
                    layoutCoordinates = coordinates.value!!,
                    shapeRadius.value
                )
            }

            LazyColumn(
                state = scrollListState
            ) {
                getMapComponents().forEach { component ->
                    val listener = listeners.firstOrNull { it.render.value == component.render }
                    delegates.filter { it.renders.any { it.value == component.render } }.forEach {
                        item {
                            it.CreateComponent(
                                component = component,
                                listener = listener,
                                componentAction = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}