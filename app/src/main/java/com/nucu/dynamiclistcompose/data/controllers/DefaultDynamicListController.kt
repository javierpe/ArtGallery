package com.nucu.dynamiclistcompose.data.controllers

import com.javi.render.data.RenderType
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DefaultDynamicListController @Inject constructor(
    override val delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>,
    override val defaultDispatcher: CoroutineDispatcher,
): DynamicListComposeController() {

    override fun getMapComponents(): List<ComponentItemModel> = data

    override fun getMapSkeletons(): List<RenderType> = skeletons
}