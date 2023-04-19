package com.javi.dynamic.list.components.card

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.dynamic.list.presentation.components.card.CARD_COMPONENT_SCREEN_TAG
import com.javi.dynamic.list.presentation.components.card.CardElement
import com.javi.dynamic.list.presentation.components.card.CardsComponentViewScreen
import com.javi.dynamic.list.presentation.components.card.CardsModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardsComponentViewScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            DynamicListComposeTheme {
                CardsComponentViewScreen(
                    modifier = Modifier,
                    data = CardsModel(
                        title = String(),
                        cardElements = listOf(
                            CardElement(
                                id = 0,
                                title = String(),
                                images = emptyList()
                            )
                        )
                    ),
                    componentIndex = 0,
                    showCaseState = rememberShowCaseState(),
                    onCardPage = { _, _ -> }
                )
            }
        }
    }

    @Test
    fun cardScreenShouldExist() {
        composeTestRule
            .onNodeWithTag(CARD_COMPONENT_SCREEN_TAG)
            .assertExists("CardsComponentView does not exist!")
    }
}