package com.nucu.dynamiclistcompose.domain.impl

import android.content.Context
import com.google.gson.Gson
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.data.api.DynamicListMockResponseApi
import com.nucu.dynamiclistcompose.data.models.DataContentModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DynamicListMockResponseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
): DynamicListMockResponseApi {

    override suspend fun getJsonDataFromAsset(): DataContentModel {
        val data = context.resources
            .openRawResource(R.raw.response)
            .bufferedReader()
            .use { it.readText() }

        return gson.fromJson(data, DataContentModel::class.java)
    }
}