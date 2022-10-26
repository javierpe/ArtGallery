package com.nucu.dynamiclistcompose.data.impl

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.javi.render.processor.data.enums.RenderType
import com.nucu.dynamiclistcompose.domain.impl.DynamicListRenderProcessorImpl
import com.nucu.dynamiclistcompose.presentation.components.text.TextModel
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
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DynamicListRenderProcessorImplTest {

    @Mock
    lateinit var gson: Gson

    private lateinit var render: TextRender

    private lateinit var renders: MutableSet<DynamicListRender<*>>

    private lateinit var dynamicListRenderProcessorImpl: DynamicListRenderProcessorImpl

    private val json by lazy {
        JsonObject().apply { addProperty("items", "null") }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = TextRender(gson)
        renders = mutableSetOf(render)
        dynamicListRenderProcessorImpl = DynamicListRenderProcessorImpl(
            renders
        )

        whenever(gson.fromJson(json, TextModel::class.java)).thenReturn(TextModel(String()))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Render resource should match with some item in renders`() = runTest {
        assert(
            dynamicListRenderProcessorImpl.getResourceByRender(
                RenderType.TEXT.value,
                json
            ) != null
        )
    }
}