package com.javi.dynamic.list.factories

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.dynamic.list.presentation.factories.FiltersFactory
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

    @Before
    fun setUp() {
        factory = FiltersFactory()
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
        assert(factory.render == RenderType.FILTERS)
    }
}
