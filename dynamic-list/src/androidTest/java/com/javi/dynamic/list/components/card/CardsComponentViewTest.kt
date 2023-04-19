package com.javi.dynamic.list.components.card

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.dynamic.list.presentation.components.card.CARD_COMPONENT_TAG
import com.javi.dynamic.list.presentation.components.card.CARD_ITEM_COMPONENT_TAG
import com.javi.dynamic.list.presentation.components.card.CARD_TITLE_COMPONENT_TAG
import com.javi.dynamic.list.presentation.components.card.CardElement
import com.javi.dynamic.list.presentation.components.card.CardsComponentView
import com.javi.dynamic.list.presentation.components.card.CardsModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardsComponentViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var onClick: (Int, String) -> Unit

    @Before
    fun setUp() {

        val model = CardsModel(
            title = String(),
            cardElements = listOf(
                CardElement(
                    id = 0,
                    title = "Test",
                    images = emptyList()
                )
            )
        )

        composeTestRule.setContent {
            DynamicListComposeTheme {
                CardsComponentView(
                    modifier = Modifier,
                    title = model.title,
                    cardElements = model.cardElements,
                    componentIndex = 0,
                    showCaseState = rememberShowCaseState(),
                    onNavigateToDetail = onClick
                )
            }
        }
    }

    @Test
    fun cardTitleShouldExist() {
        composeTestRule
            .onNodeWithTag(CARD_TITLE_COMPONENT_TAG)
            .assertExists("Title does not exist!")
    }

    @Test
    fun cardCarouselShouldExist() {
        composeTestRule
            .onNodeWithTag(CARD_COMPONENT_TAG)
            .assertExists("Card carousel does not exist!")
    }

    @Test
    fun cardItemShouldHaveCardItem() {
        composeTestRule
            .onNodeWithTag(CARD_ITEM_COMPONENT_TAG)
            .assertExists("Card item does not exist!")
    }
}
