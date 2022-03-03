package com.nucu.dynamiclistcompose.adapters

import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface DynamicListAdapterFactory {

    val renders: List<RenderType>

    @Composable
    fun Create(
        component: ComponentItemModel,
        storeType: String?,
        source: String?,
        listener: DynamicListComponentListener?
    )
}