package com.nucu.dynamiclistcompose.impl

import com.nucu.dynamiclistcompose.controllers.DynamicListComposeAction
import com.nucu.dynamiclistcompose.listeners.DynamicListComposeLoader
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.ui.base.DynamicListCompose
import javax.inject.Inject

class DynamicListComposeLoaderImpl @Inject constructor() : DynamicListComposeLoader {

    override fun getAction(
        requestModel: DynamicListRequestModel,
        dynamicListVersion: String?
    ): DynamicListComposeAction {
        return DynamicListCompose().apply {
            setRequestModel(requestModel)
        }
    }
}