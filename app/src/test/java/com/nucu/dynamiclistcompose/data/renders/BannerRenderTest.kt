package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.javi.render.processor.data.enums.RenderType
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerInfo
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BannerRenderTest {

    private lateinit var render: BannerRender

    private val resource by lazy {
        """
          {
            "url": "https://i.pinimg.com/474x/30/9b/5b/309b5b18d162cec61be5403008a0f704.jpg",
           "info": {
              "title": "John",
              "description": "Italy"
            }
          }
        """
    }

    private val sourceOfTrueModel by lazy {
        BannerModel(
            imageURL = "https://i.pinimg.com/474x/30/9b/5b/309b5b18d162cec61be5403008a0f704.jpg",
            bannerInfo = BannerInfo(title = "John", description = "Italy")
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = BannerRender(
            Gson()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `resolve should convert JSON to BannerModel`() = runTest {
        val model = render.resolve(String(), JsonParser().parse(resource).asJsonObject)
        assert(model == sourceOfTrueModel)
    }

    @Test
    fun `Render name should be BANNER`() = runTest {
        assert(render.renders.contains(RenderType.BANNER))
    }
}