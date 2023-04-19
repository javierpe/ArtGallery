package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.factories.MESSAGE_COMPONENT_TAG
import com.javi.dynamic.list.data.factories.MessageFactory
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.message.MessageModel
import com.javi.render.processor.core.RenderType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MessageFactoryTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    private lateinit var factory: MessageFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.MESSAGE.name,
            index = 0,
            resource = MessageModel(
                message = String()
            )
        )
    }

    @Before
    fun setUp() {
        factory = MessageFactory()
    }

    @Test
    fun createComponentShouldHaveMessageComponentView() {
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
            .onNodeWithTag(MESSAGE_COMPONENT_TAG)
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
    fun renderNameShouldBe_MESSAGE() {
        assert(factory.renders.contains(RenderType.MESSAGE))
    }
}
