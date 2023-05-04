package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.text.TextModel
import com.javi.dynamic.list.presentation.factories.TEXT_COMPONENT_TAG
import com.javi.dynamic.list.presentation.factories.TextFactory
import com.javi.render.processor.core.RenderType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TextFactoryTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    private lateinit var factory: TextFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.TEXT.name,
            index = 0,
            resource = TextModel(
                text = String()
            )
        )
    }

    @Before
    fun setUp() {
        factory = TextFactory()
    }

    @Test
    fun createComponentShouldHaveTextComponentView() {
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
            .onNodeWithTag(TEXT_COMPONENT_TAG)
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
    fun renderNameShouldBe_TEXT() {
        assert(factory.render == RenderType.TEXT)
    }
}
