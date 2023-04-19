package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.data.ProductImageModel
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.factories.BANNER_COMPONENT_TAG
import com.javi.dynamic.list.data.factories.BannerFactory
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.banner.BannerInfo
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.render.processor.core.RenderType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BannerFactoryTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var factory: BannerFactory

    @Mock
    lateinit var productDetailScreenLoader: GetProductDetailPageUseCase

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.BANNER.name,
            index = 0,
            resource = BannerModel(
                product = ProductImageModel(id = 0, quantity = 0, imageURL = ""),
                bannerInfo = BannerInfo(title = "", description = "")
            )
        )
    }

    @Before
    fun setUp() {
        factory = BannerFactory(productDetailScreenLoader)
    }

    @Test
    fun createComponentShouldHaveBannerComponentViewScreen() {
        composeTestRule.setContent {
            factory.CreateComponent(
                modifier = Modifier,
                component = componentItemModel,
                componentInfo = ComponentInfo(
                    dynamicListObject = DynamicListObject(),
                    showCaseState = rememberShowCaseState()
                )
            )
        }

        composeTestRule
            .onNodeWithTag(BANNER_COMPONENT_TAG, useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun createSkeletonShouldHaveSkeleton() {
        composeTestRule.setContent {
            factory.CreateSkeleton()
        }

        composeTestRule
            .onNodeWithTag("skeleton")
            .assertExists()
    }

    @Test
    fun renderNameShouldBe_BANNER() {
        assert(factory.renders.contains(RenderType.BANNER))
    }
}
