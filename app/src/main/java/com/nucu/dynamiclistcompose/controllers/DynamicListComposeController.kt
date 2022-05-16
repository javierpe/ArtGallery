package com.nucu.dynamiclistcompose.controllers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.BlinkAnimation
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import com.nucu.dynamiclistcompose.viewModels.DynamicListComposeViewModel
import kotlinx.coroutines.delay
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
    fun ComposeComponent(
        viewModel: DynamicListComposeViewModel = hiltViewModel()
    ) {

        val scrollListState = rememberLazyListState()

        val coroutine = rememberCoroutineScope()

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
                        val action = (scrollActionState.value as ScrollAction.ScrollRender)
                        val render = action.renderType
                        val element = getMapComponents().firstOrNull {
                            it.render == render.value
                        }

                        element?.let {
                            getMapComponents().indexOf(element).let {
                                scrollListState.animateScrollToItem(it)
                                action.onScrolled?.let { onScrolled ->
                                    coroutine.launch {
                                        delay(1500)
                                        onScrolled.invoke()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            else -> { }
        }

        Box {
            LazyColumn(
                state = scrollListState,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                getMapComponents().forEach { component ->
                    val listener = listeners.firstOrNull { it.render.value == component.render }
                    delegates.filter { adapterFactory ->
                        adapterFactory.renders.any { renderType ->
                            renderType.value == component.render
                        }
                    }.forEach {
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

            //viewModel.GetTooltip()
        }
    }
}