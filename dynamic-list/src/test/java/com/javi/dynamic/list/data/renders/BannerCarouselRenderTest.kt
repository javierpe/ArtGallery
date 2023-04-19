package com.javi.dynamic.list.data.renders

import com.javi.data.ProductImageModel
import com.javi.dynamic.list.presentation.components.banner.BannerInfo
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.dynamic.list.presentation.components.bannerCarousel.BannerCarouselModel
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

@OptIn(ExperimentalCoroutinesApi::class)
class BannerCarouselRenderTest {

    private lateinit var render: BannerCarouselRender

    private val sourceOfTrueModel by lazy {
        BannerCarouselModel(
            banners = listOf(
                BannerModel(
                    product = ProductImageModel(
                        id = 0,
                        quantity = 0,
                        imageURL = "https://i.pinimg.com/474x/1f/73/25/1f7325bc264a816ab131d6d624fa792a.jpg"
                    )
                ),
                BannerModel(
                    product = ProductImageModel(
                        id = 0,
                        quantity = 0,
                        imageURL = "https://i.pinimg.com/474x/1f/73/25/1f7325bc264a816ab131d6d624fa792a.jpg"
                    ),
                    bannerInfo = BannerInfo(title = "John", description = "Italy")
                )
            )
        )
    }

    private val resultModel by lazy {
        BannerCarouselModel(
            banners = listOf(
                BannerModel(
                    product = ProductImageModel(
                        id = 0,
                        quantity = 0,
                        imageURL = "https://i.pinimg.com/474x/1f/73/25/1f7325bc264a816ab131d6d624fa792a.jpg"
                    ),
                    bannerInfo = BannerInfo(title = "John", description = "Italy")
                )
            )
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = BannerCarouselRender()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `resolve should convert JSON to BannerCarouselModel`() = runTest {
        val model = render.resolve(String(), sourceOfTrueModel)
        assert(model == resultModel)
    }

    @Test
    fun `Render name should be BANNER_CAROUSEL`() = runTest {
        assert(render.renders.contains(RenderType.BANNER_CAROUSEL))
    }
}
