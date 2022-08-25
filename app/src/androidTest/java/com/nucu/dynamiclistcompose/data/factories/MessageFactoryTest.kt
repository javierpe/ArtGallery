package com.nucu.dynamiclistcompose.data.factories

import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.nucu.dynamiclistcompose.MainActivity
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.components.message.MessageModel
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.rememberShowCaseState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MessageFactoryTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var factory: MessageFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.MESSAGE.name,
            index =  0,
            resource = MessageModel(
                message = String()
            )
        )
    }

    @Before
    fun setUp() {
        hiltTestRule.inject()
        factory = MessageFactory()
    }

    @Test
    fun createComponentShouldHaveMessageComponentView() {

        composeTestRule.activity.setContent {
            factory.CreateComponent(
                modifier = Modifier,
                component = componentItemModel,
                componentInfo = ComponentInfo(
                    windowWidthSizeClass = calculateWindowSizeClass(composeTestRule.activity).widthSizeClass,
                    showCaseState = rememberShowCaseState()
                )
            )
        }

        composeTestRule
            .onNodeWithTag("message_component")
            .assertExists()
    }

    @Test
    fun createSkeletonShouldHaveSkeleton() {

        composeTestRule.activity.setContent {
            factory.CreateSkeleton()
        }

        composeTestRule
            .onNodeWithTag("skeleton")
            .assertExists()
    }

    @Test
    fun renderNameShouldBe_MESSAGE() {
        assert(factory.renders.contains(RenderType.MESSAGE))
    }
}