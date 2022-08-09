package com.nucu.dynamiclistcompose.impl

import com.nucu.dynamiclistcompose.api.DynamicListControllerApi
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.actions.DynamicListAction
import com.nucu.dynamiclistcompose.di.IODispatcher
import com.nucu.dynamiclistcompose.data.models.DynamicListContainer
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DynamicListControllerImpl @Inject constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher
) : DynamicListControllerApi {

    override suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListAction> = flow {

        // Emulate response delay
        delay(3000)

        val container = withContext(ioDispatcher) {
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
                    render = RenderType.HEADER.value,
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
                    render = RenderType.BANNER_IMAGE.value,
                    resource = "",
                    name = "",
                    resolver = "",
                    index = 14,
                    uniqueId = ""
                ),

                ComponentItemModel(
                    render = RenderType.TOBACCO_PREFERENCE.value,
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
                bodies = bodies
            )
        }

        emit(DynamicListAction.SuccessAction(container))

    }
}