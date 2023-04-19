package com.javi.dynamic.list.components.bannerCarousel

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.data.ProductImageModel
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.dynamic.list.presentation.components.banner.BannerInfo
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.dynamic.list.presentation.components.bannerCarousel.BANNER_CAROUSEL_IMAGE_SCREEN_TEST_TAG
import com.javi.dynamic.list.presentation.components.bannerCarousel.BannerCarouselComponentViewScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BannerCarouselComponentViewScreenTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            DynamicListComposeTheme {
                BannerCarouselComponentViewScreen(
                    modifier = Modifier,
                    images = listOf(
                        BannerModel(
                            product = ProductImageModel(id = 0, quantity = 0, imageURL = ""),
                            bannerInfo = BannerInfo(title = "", description = "")
                        )
                    ),
                    componentIndex = 0,
                    showCaseState = rememberShowCaseState(),
                    onProductDetail = { }
                )
            }
        }
    }

    @Test
    fun bannerCarouselScreenShouldHaveComponentView() {
        composeTestRule
            .onNodeWithTag(BANNER_CAROUSEL_IMAGE_SCREEN_TEST_TAG)
            .assertExists("BannerCarouselComponentViewScreen does has not have a BannerCarouselComponentView!")
    }
}