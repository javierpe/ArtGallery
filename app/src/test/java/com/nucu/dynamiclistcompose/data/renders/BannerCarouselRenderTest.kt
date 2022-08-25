package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import com.nucu.dynamiclistcompose.presentation.components.bannerCarousel.BannerCarouselModel
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
class BannerCarouselRenderTest {

    private lateinit var render: BannerCarouselRender

    private val resource by lazy {
        """
          {
            "banners": [
              {
                "url": "https://i.pinimg.com/474x/1f/73/25/1f7325bc264a816ab131d6d624fa792a.jpg"
              },
              {
                "url": "https://i.pinimg.com/474x/96/07/52/9607523b8c71cb4895a3f87b30421900.jpg"
              }
            ]
          }
        """
    }

    private val sourceOfTrueModel by lazy {
        BannerCarouselModel(
            banners = listOf(
                BannerModel(imageURL = "https://i.pinimg.com/474x/1f/73/25/1f7325bc264a816ab131d6d624fa792a.jpg"),
                BannerModel(imageURL = "https://i.pinimg.com/474x/96/07/52/9607523b8c71cb4895a3f87b30421900.jpg")
            )
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = BannerCarouselRender(
            Gson()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `resolve should convert JSON to BannerCarouselModel`() = runTest {
        val model = render.resolve(String(), JsonParser().parse(resource).asJsonObject)
        assert(model == sourceOfTrueModel)
    }
}