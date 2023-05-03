package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.basket.api.AddProductToBasketUseCase
import com.javi.basket.api.DecrementProductOnBasketUseCase
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.bannerCarousel.BannerCarouselModel
import com.javi.dynamic.list.presentation.factories.BANNER_CAROUSEL_COMPONENT_TAG
import com.javi.dynamic.list.presentation.factories.BannerCarouselFactory
import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.render.processor.core.RenderType
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BannerCarouselFactoryTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var destinationsNavigator: DestinationsNavigator

    @Mock
    lateinit var getProductDetailPageUseCase: GetProductDetailPageUseCase

    @Mock
    lateinit var addProductToBasketUseCase: AddProductToBasketUseCase

    @Mock
    lateinit var decrementProductOnBasketUseCase: DecrementProductOnBasketUseCase

    private lateinit var factory: BannerCarouselFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.BANNER_CAROUSEL.name,
            index = 0,
            resource = BannerCarouselModel(
                banners = emptyList()
            )
        )
    }

    @Before
    fun setUp() {
        factory = BannerCarouselFactory(
            getProductDetailPageUseCase = getProductDetailPageUseCase,
            addProductToBasketUseCase = addProductToBasketUseCase,
            decrementProductOnBasketUseCase = decrementProductOnBasketUseCase
        )
    }

    @Test
    fun createComponentShouldHaveBannerCarouselComponentViewScreen() {
        composeTestRule.setContent {
            factory.CreateComponent(
                modifier = Modifier,
                component = componentItemModel,
                componentInfo = ComponentInfo(
                    dynamicListObject = DynamicListObject(
                        destinationsNavigator = destinationsNavigator
                    ),
                    showCaseState = rememberShowCaseState()
                )
            )
        }

        composeTestRule
            .onNodeWithTag(BANNER_CAROUSEL_COMPONENT_TAG)
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
    fun renderNameShouldBe_BANNER_CAROUSEL() {
        assert(factory.render == RenderType.BANNER_CAROUSEL)
    }
}
