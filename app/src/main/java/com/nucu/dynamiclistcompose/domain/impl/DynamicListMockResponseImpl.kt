package com.nucu.dynamiclistcompose.domain.impl

import android.content.Context
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.data.api.DynamicListMockResponseApi
import com.nucu.dynamiclistcompose.data.extensions.tryFromJson
import com.nucu.dynamiclistcompose.data.models.ContextType
import com.nucu.dynamiclistcompose.data.models.DataContentModel
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DynamicListMockResponseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
): DynamicListMockResponseApi {

    override suspend fun getDataFromAsset(dynamicListRequestModel: DynamicListRequestModel): DataContentModel {
        val data = context.resources
            .openRawResource(getResponseByContext(dynamicListRequestModel))
            .bufferedReader()
            .use { it.readText() }

        return moshi.adapter(DataContentModel::class.java).tryFromJson(data)!!
    }

    private fun getResponseByContext(dynamicListRequestModel: DynamicListRequestModel): Int {
        return when(dynamicListRequestModel.contextType) {
            ContextType.HOME -> R.raw.response_home
            ContextType.CARDS -> getCards(dynamicListRequestModel.state)
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
}