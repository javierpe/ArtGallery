package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.factories.FILTERS_COMPONENT_TAG
import com.javi.dynamic.list.data.factories.FiltersFactory
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.filters.Filters
import com.javi.render.processor.core.RenderType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FiltersFactoryTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    private lateinit var factory: FiltersFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.FILTERS.name,
            index = 0,
            resource = Filters(
                items = emptyList()
            )
        )
    }

    @Before
    fun setUp() {
        factory = FiltersFactory()
    }

    @Test
    fun createComponentShouldHaveFiltersComponentView() {
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
            .onNodeWithTag(FILTERS_COMPONENT_TAG)
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
    fun renderNameShouldBe_FILTERS() {
        assert(factory.renders.contains(RenderType.FILTERS))
    }
}
