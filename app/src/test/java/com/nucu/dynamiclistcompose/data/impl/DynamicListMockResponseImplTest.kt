package com.nucu.dynamiclistcompose.data.impl

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.data.models.ComponentModel
import com.nucu.dynamiclistcompose.data.models.DataContentModel
import com.nucu.dynamiclistcompose.domain.impl.DynamicListMockResponseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DynamicListMockResponseImplTest {

    @Mock
    lateinit var gson: Gson

    private val resourcesMock = mock<Resources> {
        on {
            openRawResource(eq(R.raw.response))
        } doAnswer {
            successJSON.byteInputStream()
        }
    }

    private val context = mock<Context> {
        on { resources } doReturn(resourcesMock)
    }

    private lateinit var dynamicListMockResponseImpl: DynamicListMockResponseImpl

    private val successJSON = """ {
            {
              "header": [
                {
                  "render": "filters",
                  "index": 0,
                  "resource": null
                }
              ],
              "body": [
                {
                  "render": "faces",
                  "index": 0,
                  "resource": null
                }
              ]
            }
             """

    private val successModel by lazy {
        DataContentModel(
            header = listOf(
                ComponentModel(
                    "filters",
                    0
                )
            ),
            body = listOf(
                ComponentModel(
                    "faces",
                    0
                )
            )
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        dynamicListMockResponseImpl = DynamicListMockResponseImpl(
            context,
            gson
        )

        whenever(context.resources).thenReturn(resourcesMock)
        whenever(gson.fromJson(successJSON, DataContentModel::class.java)).thenReturn(successModel)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `DataContentModel should be deserialize`() = runTest {
        val data = dynamicListMockResponseImpl.getJsonDataFromAsset()
        assert(data.body.isNotEmpty() && data.header.isNotEmpty())
    }
}