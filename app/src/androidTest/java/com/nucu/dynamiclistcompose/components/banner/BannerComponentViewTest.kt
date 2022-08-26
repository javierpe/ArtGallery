package com.nucu.dynamiclistcompose.components.banner

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.nucu.dynamiclistcompose.presentation.components.banner.BANNER_IMAGE_TEST_TAG
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerComponentView
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.theme.DynamicListComposeTheme
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
    lateinit var onClick: (String) -> Unit

    @Before
    fun setUp() {
        composeTestRule.setContent {
            DynamicListComposeTheme {
                BannerComponentView(
                    modifier = Modifier,
                    model = BannerModel(String()),
                    componentIndex = 0,
                    showCaseState = rememberShowCaseState(),
                    onClickAction = onClick
                )
            }
        }
    }

    @Test
    fun bannerShouldHaveClickAction() {
        composeTestRule
            .onNodeWithTag(BANNER_IMAGE_TEST_TAG)
            .assert(hasClickAction())
    }

    @Test
    fun bannerViewModelClickShouldBeInvoked() {
        composeTestRule
            .onNodeWithTag(BANNER_IMAGE_TEST_TAG)
            .performClick()

        verify(onClick).invoke(String())
    }
}