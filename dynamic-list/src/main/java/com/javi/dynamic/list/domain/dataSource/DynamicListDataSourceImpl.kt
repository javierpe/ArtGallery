package com.javi.dynamic.list.domain.dataSource

import android.content.Context
import com.javi.core.networking.DYNAMIC_LIST_BASE
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.R
import com.javi.dynamic.list.data.dataSources.DynamicListDataSourceApi
import com.javi.dynamic.list.data.dataSources.DynamicListRemote
import com.javi.dynamic.list.data.extensions.tryFromJson
import com.javi.dynamic.list.data.models.DataContentModel
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DynamicListDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi,
    private val dynamicListRemote: DynamicListRemote
) : DynamicListDataSourceApi {

    override suspend fun getDataFromAsset(dynamicListRequestModel: DynamicListRequestModel): DataContentModel {
        val data = context.resources
            .openRawResource(getResponseByContext(dynamicListRequestModel))
            .bufferedReader()
            .use { it.readText() }

        return moshi.adapter(DataContentModel::class.java).tryFromJson(data)!!
    }

    override suspend fun getRemoteData(dynamicListRequestModel: DynamicListRequestModel): DataContentModel {
        return dynamicListRemote.getContent(DYNAMIC_LIST_BASE + getComplementaryUrl(dynamicListRequestModel))
    }

    private fun getResponseByContext(dynamicListRequestModel: DynamicListRequestModel): Int {
        return when (dynamicListRequestModel.contextType) {
            ContextType.HOME -> R.raw.response_home
            ContextType.CARDS -> getCards(dynamicListRequestModel.state)
            ContextType.PLACES -> R.raw.response_places
            else -> R.raw.response_home
        }
    }

    private fun getCards(state: HashMap<String, String>?): Int {
        return state?.let {
            when (it["id"].orEmpty()) {
                "0" -> R.raw.response_cards
                "1" -> R.raw.response_cards_1
                "2" -> R.raw.response_cards_2
                else -> { R.raw.response_cards }
            }
        } ?: R.raw.response_cards
    }

    private fun getComplementaryUrl(request: DynamicListRequestModel): String {
        return when (request.contextType) {
            ContextType.CARDS -> getCardsComplementaryUrl(request)
            else -> request.contextType.source
        }
    }

    private fun getCardsComplementaryUrl(request: DynamicListRequestModel): String {
        return "${request.contextType.source}${request.state["id"] ?: 0}"
    }
}
