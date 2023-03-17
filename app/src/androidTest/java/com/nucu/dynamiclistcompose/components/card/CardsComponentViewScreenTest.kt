package com.nucu.dynamiclistcompose.components.card

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.nucu.dynamiclistcompose.MainActivity
import com.nucu.dynamiclistcompose.presentation.components.card.CARD_COMPONENT_SCREEN_TAG
import com.nucu.dynamiclistcompose.presentation.components.card.CardsComponentViewScreen
import com.nucu.dynamiclistcompose.presentation.components.card.CardsModel
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.theme.DynamicListComposeTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CardsComponentViewScreenTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
        val viewModel = composeTestRule.activity.viewModels<CardsViewModel>().value
        composeTestRule.activity.setContent {
            DynamicListComposeTheme {
                CardsComponentViewScreen(
                    modifier = Modifier,
                    data = CardsModel(String(), emptyList()),
                    componentIndex = 0,
                    showCaseState = rememberShowCaseState(),
                    viewModel = viewModel
                )
            }
        }
    }

    @Test
    fun cardsScreenShouldHaveComponentView() {
        composeTestRule
            .onNodeWithTag(CARD_COMPONENT_SCREEN_TAG)
            .assertExists("CardsComponentViewScreen does has not have a CardsComponentView!")
    }
}