package com.nucu.dynamiclistcompose.adapters

import com.nucu.dynamiclistcompose.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import javax.inject.Inject

class DefaultAdapterController @Inject constructor(
    override val delegates: MutableSet<@JvmSuppressWildcards DynamicListAdapterFactory>,
    override val listeners: Set<@JvmSuppressWildcards DynamicListComponentListener>,
): DynamicListComposeController() {

    override fun getMapComponents(): List<ComponentItemModel> = data
}