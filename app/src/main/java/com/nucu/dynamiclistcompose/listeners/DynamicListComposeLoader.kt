package com.nucu.dynamiclistcompose.listeners

import com.nucu.dynamiclistcompose.controllers.DynamicListComposeAction
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel

interface DynamicListComposeLoader {

    fun getAction(
        requestModel: DynamicListRequestModel,
        dynamicListVersion: String? = null
    ): DynamicListComposeAction
}