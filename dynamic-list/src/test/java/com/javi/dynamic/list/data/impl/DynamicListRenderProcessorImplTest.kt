package com.javi.dynamic.list.data.impl

import com.javi.dynamic.list.data.renders.BannerCarouselRender
import com.javi.dynamic.list.data.renders.base.DynamicListRender
import com.javi.dynamic.list.domain.impl.DynamicListRenderProcessorImpl
import com.javi.render.processor.core.RenderType
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
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DynamicListRenderProcessorImplTest {

    private lateinit var render: BannerCarouselRender

    private lateinit var renders: MutableSet<DynamicListRender<*>>

    private lateinit var dynamicListRenderProcessorImpl: DynamicListRenderProcessorImpl

    private val json by lazy {
        "\"banners\": [\n" +
            "          {\n" +
            "            \"product\": {\n" +
            "              \"id\": 50,\n" +
            "              \"quantity\": 0,\n" +
            "              \"image_url\": a" +
            "            },\n" +
            "            \"info\": {\n" +
            "              \"title\": \"John\",\n" +
            "              \"description\": \"Italy\"\n" +
            "            }\n" +
            "          }]"
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
