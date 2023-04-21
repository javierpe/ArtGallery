package com.javi.dynamic.list.data.controllers

import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.render.processor.core.RenderType
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DefaultDynamicListController @Inject constructor(
    override val delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>,
    override val defaultDispatcher: CoroutineDispatcher,
) : DynamicListComposeController() {

    override fun getMapComponents(): List<ComponentItemModel> = data

    override fun getMapSkeletons(): List<RenderType> = skeletons
}
