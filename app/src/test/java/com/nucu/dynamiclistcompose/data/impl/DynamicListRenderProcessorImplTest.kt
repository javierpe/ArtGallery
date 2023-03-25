package com.nucu.dynamiclistcompose.data.impl

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.javi.render.data.RenderType
import com.nucu.dynamiclistcompose.data.renders.BannerCarouselRender
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.domain.impl.DynamicListRenderProcessorImpl
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DynamicListRenderProcessorImplTest {

    private lateinit var render: BannerCarouselRender

    private lateinit var renders: MutableSet<DynamicListRender<*>>

    private lateinit var dynamicListRenderProcessorImpl: DynamicListRenderProcessorImpl

    private val json by lazy {
        JsonObject().apply { addProperty("items", "null") }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = BannerCarouselRender()
        renders = mutableSetOf(render)
        dynamicListRenderProcessorImpl = DynamicListRenderProcessorImpl(
            renders
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Render resource should match with some item in renders`() = runTest {
        assert(
            dynamicListRenderProcessorImpl.processResource(
                RenderType.TEXT.value,
                json
            ) != null
        )
    }
}