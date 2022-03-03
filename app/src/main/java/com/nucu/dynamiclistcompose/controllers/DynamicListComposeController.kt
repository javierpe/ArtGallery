package com.nucu.dynamiclistcompose.controllers

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import javax.inject.Inject

abstract class DynamicListComposeController {

    abstract val delegates: MutableSet<@JvmSuppressWildcards DynamicListAdapterFactory>

    abstract val listeners: Set<@JvmSuppressWildcards DynamicListComponentListener>

    open fun getMapComponents(): List<ComponentItemModel> = data

    var data: List<ComponentItemModel> = listOf()

    open fun dispatch(components: List<ComponentItemModel>) {
        data = components
    }

    @Composable
    fun DynamicListComposeComponent(
        requestModel: DynamicListRequestModel
    ) {
        LazyColumn {
            getMapComponents().forEach { component ->
                val listener = listeners.firstOrNull { it.render.value == component.render }
                delegates.filter { it.renders.any { it.value == component.render } }.forEach {
                    item {
                        it.Create(
                            component = component,
                            storeType = requestModel.storeType,
                            source = requestModel.aSources.source,
                            listener = listener
                        )
                    }
                }
            }
        }
    }
}