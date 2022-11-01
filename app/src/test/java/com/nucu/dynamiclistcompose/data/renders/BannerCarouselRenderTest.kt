package com.nucu.dynamiclistcompose.data.renders

import com.javi.render.processor.data.enums.RenderType
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerInfo
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

    private val sourceOfTrueModel by lazy {
        BannerCarouselModel(
            banners = listOf(
                BannerModel(
                    imageURL = "https://i.pinimg.com/474x/1f/73/25/1f7325bc264a816ab131d6d624fa792a.jpg"
                ),
                BannerModel(
                    imageURL = "https://i.pinimg.com/474x/96/07/52/9607523b8c71cb4895a3f87b30421900.jpg",
                    bannerInfo = BannerInfo(title = "John", description = "Italy")
                )
            )
        )
    }

    private val resultModel by lazy {
        BannerCarouselModel(
            banners = listOf(
                BannerModel(
                    imageURL = "https://i.pinimg.com/474x/96/07/52/9607523b8c71cb4895a3f87b30421900.jpg",
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