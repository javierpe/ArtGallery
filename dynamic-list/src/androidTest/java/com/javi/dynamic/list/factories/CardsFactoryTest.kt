package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.cards.page.api.GetCardsPageUseCase
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.card.CardsModel
import com.javi.dynamic.list.presentation.factories.CARDS_COMPONENT_TAG
import com.javi.dynamic.list.presentation.factories.CardsFactory
import com.javi.render.processor.core.RenderType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardsFactoryTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var getCardsPageUseCase: GetCardsPageUseCase

    private lateinit var factory: CardsFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.CARDS.name,
            index = 0,
            resource = CardsModel(
                title = String(),
                cardElements = emptyList()
            )
        )
    }

    @Before
    fun setUp() {
        factory = CardsFactory(getCardsPageUseCase)
    }

    @Test
    fun createComponentShouldHaveCardsComponentViewScreen() {
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
            .onNodeWithTag(CARDS_COMPONENT_TAG)
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
    fun renderNameShouldBe_CARDS() {
        assert(factory.render == RenderType.CARDS)
    }
}
