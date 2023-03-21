package com.nucu.dynamiclistcompose.data.controllers

import com.javi.api.LocalBasketApi
import com.nucu.dynamiclistcompose.data.api.TooltipPreferencesApi
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.javi.render.processor.data.enums.RenderType
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DefaultDynamicListController @Inject constructor(
    override val delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>,
    override val defaultDispatcher: CoroutineDispatcher,
    override val tooltipPreferencesApi: TooltipPreferencesApi,
): DynamicListComposeController() {

    override fun getMapComponents(): List<ComponentItemModel> = data

    override fun getMapSkeletons(): List<RenderType> = skeletons
}