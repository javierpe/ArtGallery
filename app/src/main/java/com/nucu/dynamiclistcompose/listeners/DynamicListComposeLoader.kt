package com.nucu.dynamiclistcompose.listeners

import com.nucu.dynamiclistcompose.controllers.DynamicListComposeLoader
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel

interface DynamicListComposeLoader {

    fun getAction(
        requestModel: DynamicListRequestModel,
    ): DynamicListComposeLoader
}