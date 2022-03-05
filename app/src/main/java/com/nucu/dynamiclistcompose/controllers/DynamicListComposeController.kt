package com.nucu.dynamiclistcompose.controllers

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListComponentAction
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.renders.base.RenderType

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
    fun DynamicListComposeComponent(
        requestModel: DynamicListRequestModel,
        dynamicListComponentAction: DynamicListComponentAction
    ) {
        getMapComponents().forEach { component ->
            val listener = listeners.firstOrNull { it.render.value == component.render }
            delegates.filter { it.renders.any { it.value == component.render } }.forEach {
                it.Create(
                    component = component,
                    storeType = requestModel.storeType,
                    source = requestModel.aSources.source,
                    listener = listener,
                    componentAction = dynamicListComponentAction
                )
            }
        }
    }
}