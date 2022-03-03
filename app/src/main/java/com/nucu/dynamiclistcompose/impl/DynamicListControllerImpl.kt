package com.nucu.dynamiclistcompose.impl

import com.nucu.dynamiclistcompose.controllers.DynamicListController
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListContainer
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DynamicListControllerImpl @Inject constructor() : DynamicListController {

    override suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel
    ): DynamicListContainer = withContext(Dispatchers.IO) {
        val bodies = listOf(

            ComponentItemModel(
                render = RenderType.ONE_CLICK_REORDER.value,
                resource = "",
                name = "",
                resolver = "",
                index = 0,
                uniqueId = ""
            ),

            ComponentItemModel(
                render = RenderType.BANNER_IMAGE.value,
                resource = "",
                name = "",
                resolver = "",
                index = 0,
                uniqueId = ""
            )
        )

        DynamicListContainer(
            headers = emptyList(),
            bodies = bodies,
            footers = emptyList()
        )
    }
}