package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.factories.PROFILE_COMPONENT_TAG
import com.javi.dynamic.list.data.factories.ProfileFactory
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.profile.ProfileModel
import com.javi.render.processor.core.RenderType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProfileFactoryTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    private lateinit var factory: ProfileFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.PROFILE.name,
            index = 0,
            resource = ProfileModel(
                name = String(),
                shortDescription = String(),
                lifeDate = String(),
                country = String(),
                color = String()
            )
        )
    }

    @Before
    fun setUp() {
        factory = ProfileFactory()
    }

    @Test
    fun createComponentShouldHavePosterComponentViewScreen() {
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
            .onNodeWithTag(PROFILE_COMPONENT_TAG)
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
    fun renderNameShouldBe_PROFILE() {
        assert(factory.renders.contains(RenderType.PROFILE))
    }
}