package com.javi.dynamic.list.components.banner

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.javi.data.ProductImageModel
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.dynamic.list.presentation.components.banner.BANNER_IMAGE_TEST_TAG
import com.javi.dynamic.list.presentation.components.banner.BannerComponentView
import com.javi.dynamic.list.presentation.components.banner.BannerInfo
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class BannerComponentViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var onClick: () -> Unit

    private val productImageModel by lazy {
        ProductImageModel(id = 0, quantity = 0, imageURL = String())
    }

    @Before
    fun setUp() {
        val model = BannerModel(
            product = productImageModel,
            bannerInfo = BannerInfo(title = "", description = "")
        )

        composeTestRule.setContent {
            DynamicListComposeTheme {
                BannerComponentView(
                    modifier = Modifier,
                    imageUrl = model.product.imageURL,
                    quantity = model.product.quantity,
                    title = model.bannerInfo?.title.orEmpty(),
                    description = model.bannerInfo?.description.orEmpty(),
                    componentIndex = 0,
                    showCaseState = rememberShowCaseState(),
                    onClickAction = onClick,
                    onDecrement = { },
                    onAdd = { }
                )
            }
        }
    }

    @Test
    fun bannerViewModelClickShouldBeInvoked() {
        composeTestRule
            .onNodeWithTag(BANNER_IMAGE_TEST_TAG)
            .performClick()

        verify(onClick).invoke()
    }
}
