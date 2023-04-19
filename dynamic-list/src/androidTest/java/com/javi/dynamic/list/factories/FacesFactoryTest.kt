package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.factories.FACES_COMPONENT_TAG
import com.javi.dynamic.list.data.factories.FacesFactory
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.faces.FacesModel
import com.javi.render.processor.core.RenderType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FacesFactoryTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var factory: FacesFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.FACES.name,
            index = 0,
            resource = FacesModel(
                items = emptyList()
            )
        )
    }

    @Before
    fun setUp() {
        factory = FacesFactory()
    }

    @Test
    fun createComponentShouldHaveFacesComponentView() {
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
            .onNodeWithTag(FACES_COMPONENT_TAG)
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
    fun renderNameShouldBe_FACES() {
        assert(factory.renders.contains(RenderType.FACES))
    }
}
