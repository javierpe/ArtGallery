package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.cardsGrid.CardsGridModel
import com.javi.dynamic.list.presentation.factories.CARDS_GRID_TAG
import com.javi.dynamic.list.presentation.factories.CardsGridFactory
import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.render.processor.core.RenderType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardsGridFactoryTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var getProductDetailScreenUseCase: GetProductDetailPageUseCase

    private lateinit var factory: CardsGridFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.CARDS_GRID.name,
            index = 0,
            resource = CardsGridModel(
                id = 0,
                title = String(),
                images = emptyList()
            )
        )
    }

    @Before
    fun setUp() {
        factory = CardsGridFactory(getProductDetailScreenUseCase)
    }

    @Test
    fun createComponentShouldHaveCardsGridComponentViewScreen() {
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
            .onNodeWithTag(CARDS_GRID_TAG)
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
    fun renderNameShouldBe_CARDS_GRID() {
        assert(factory.render == RenderType.CARDS_GRID)
    }
}
