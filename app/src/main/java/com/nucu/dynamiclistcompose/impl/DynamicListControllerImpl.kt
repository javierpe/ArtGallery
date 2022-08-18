package com.nucu.dynamiclistcompose.impl

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.api.DynamicListControllerApi
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.actions.DynamicListAction
import com.nucu.dynamiclistcompose.data.models.DataContentModel
import com.nucu.dynamiclistcompose.data.models.DynamicListContainer
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val DEFAULT_DELAY: Long = 3000

class DynamicListControllerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val renders: MutableSet<@JvmSuppressWildcards DynamicListRender<*>>,
    private val gson: Gson
) : DynamicListControllerApi {

    override suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListAction> = flow {

        // Emulate response delay
        delay(DEFAULT_DELAY)

        val componentModel = gson.fromJson(getJsonDataFromAsset(), DataContentModel::class.java)

        val header = componentModel.header.mapNotNull { component ->
            getResourceByRender(component.render, component.resource)?.let {
                ComponentItemModel(
                    render = component.render,
                    index = component.index,
                    resource = it
                )
            }
        }

        val body = componentModel.body.mapNotNull { component ->
            getResourceByRender(component.render, component.resource)?.let {
                ComponentItemModel(
                    render = component.render,
                    index = component.index,
                    resource = it
                )
            }
        }

        // Response...
        val container = DynamicListContainer(
            headers = header,
            bodies = body
        )

        emit(DynamicListAction.SuccessAction(container))

    }

    private suspend fun getResourceByRender(render: String, resource: JsonObject?): Any? {
        return renders.firstOrNull() {
            it.renders.any { renderValue -> renderValue.value == render }
        }?.resolve(render, resource)
    }

    private fun getJsonDataFromAsset(): String {
        return context.resources
            .openRawResource(R.raw.response)
            .bufferedReader()
            .use { it.readText() }
    }
}