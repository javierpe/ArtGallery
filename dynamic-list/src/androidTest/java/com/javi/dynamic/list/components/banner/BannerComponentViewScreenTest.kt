package com.javi.dynamic.list.components.banner

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.data.ProductImageModel
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.dynamic.list.presentation.components.banner.BANNER_IMAGE_SCREEN_TEST_TAG
import com.javi.dynamic.list.presentation.components.banner.BannerComponentViewScreen
import com.javi.dynamic.list.presentation.components.banner.BannerInfo
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BannerComponentViewScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            DynamicListComposeTheme {
                BannerComponentViewScreen(
                    modifier = Modifier,
                    model = BannerModel(
                        product = ProductImageModel(id = 0, quantity = 0, imageURL = ""),
                        bannerInfo = BannerInfo(title = "", description = "")
                    ),
                    componentIndex = 0,
                    showCaseState = rememberShowCaseState(),
                    onProductDetail = { },
                    onDecrement = { },
                    onAdd = { }
                )
            }
        }
    }

    @Test
    fun bannerScreenShouldHaveComponentView() {
        composeTestRule
            .onNodeWithTag(BANNER_IMAGE_SCREEN_TEST_TAG, useUnmergedTree = true)
            .assertExists("BannerComponentViewScreen does has not have a BannerComponentView!")
    }
}
