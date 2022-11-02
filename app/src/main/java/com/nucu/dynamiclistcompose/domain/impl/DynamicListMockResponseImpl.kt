package com.nucu.dynamiclistcompose.domain.impl

import android.content.Context
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.data.api.DynamicListMockResponseApi
import com.nucu.dynamiclistcompose.data.extensions.tryFromJson
import com.nucu.dynamiclistcompose.data.models.DataContentModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DynamicListMockResponseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
): DynamicListMockResponseApi {

    override suspend fun getJsonDataFromAsset(): DataContentModel {
        val data = context.resources
            .openRawResource(R.raw.response)
            .bufferedReader()
            .use { it.readText() }

        return moshi.adapter(DataContentModel::class.java).tryFromJson(data)!!
    }
}