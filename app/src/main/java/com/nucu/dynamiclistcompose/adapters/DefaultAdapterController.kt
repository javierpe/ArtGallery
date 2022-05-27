package com.nucu.dynamiclistcompose.adapters

import com.nucu.dynamiclistcompose.api.TooltipPreferencesApi
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DefaultAdapterController @Inject constructor(
    override val delegates: MutableSet<@JvmSuppressWildcards DynamicListAdapterFactory>,
    override val listeners: Set<@JvmSuppressWildcards DynamicListComponentListener>,
    override val defaultDispatcher: CoroutineDispatcher,
    override val tooltipPreferencesApi: TooltipPreferencesApi,
): DynamicListComposeController() {

    override fun getMapComponents(): List<ComponentItemModel> = data

    override fun getMapSkeletons(): List<RenderType> = skeletons
}