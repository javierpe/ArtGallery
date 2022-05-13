package com.nucu.dynamiclistcompose.impl

import com.nucu.dynamiclistcompose.controllers.DynamicListController
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListAction
import com.nucu.dynamiclistcompose.models.DynamicListContainer
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DynamicListControllerImpl @Inject constructor() : DynamicListController {

    override suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListAction> = flow {

        // TODO: Load at this point some mocked data (skeleton) while request is running.
        val skeletons = listOf(
            RenderType.TOBACCO_PREFERENCE,
            RenderType.BANNER_IMAGE,
            RenderType.HEADER,
            RenderType.ONE_CLICK_REORDER
        )

        if (skeletons.isEmpty()) {
            emit(DynamicListAction.LoadingAction)
        } else {
            emit(DynamicListAction.SkeletonAction(skeletons))
        }

        // Emulate response delay
        delay(2500)

        val container = withContext(Dispatchers.IO) {
            // Hardcoded data :D

            val header = listOf(
                ComponentItemModel(
                    render = RenderType.FILTERS.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 0,
                    uniqueId = ""
                )
            )

            val bodies = listOf(

                ComponentItemModel(
                    render = RenderType.TOBACCO_PREFERENCE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 0,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.TOBACCO_PREFERENCE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 1,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.ONE_CLICK_REORDER.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 2,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.TOBACCO_PREFERENCE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 3,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.TOBACCO_PREFERENCE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 4,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.ONE_CLICK_REORDER.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 5,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.TOBACCO_PREFERENCE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 6,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.BANNER_IMAGE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 7,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.ONE_CLICK_REORDER.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 8,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.TOBACCO_PREFERENCE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 9,
                    uniqueId = ""
                ),
                ComponentItemModel(
                    render = RenderType.BANNER_IMAGE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 10,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.TOBACCO_PREFERENCE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 11,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.ONE_CLICK_REORDER.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 12,
                    uniqueId = ""
                ),
                ComponentItemModel(
                    render = RenderType.BANNER_IMAGE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 13,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.TOBACCO_PREFERENCE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 14,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.HEADER.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 15,
                    uniqueId = ""
                )
            )

            // Response...
            DynamicListContainer(
                headers = header,
                bodies = bodies,
                footers = emptyList()
            )
        }

        emit(DynamicListAction.SuccessAction(container))

    }
}