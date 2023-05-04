package com.javi.dynamic.list.factories

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.presentation.components.poster.PosterModel
import com.javi.dynamic.list.presentation.factories.POSTER_COMPONENT_TAG
import com.javi.dynamic.list.presentation.factories.PosterFactory
import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.render.processor.core.RenderType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PosterFactoryTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var getProductDetailScreenUseCase: GetProductDetailPageUseCase

    private lateinit var factory: PosterFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.POSTER.name,
            index = 0,
            resource = PosterModel(
                title = String(),
                elements = emptyList()
            )
        )
    }

    @Before
    fun setUp() {
        factory = PosterFactory(getProductDetailScreenUseCase)
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
            .onNodeWithTag(POSTER_COMPONENT_TAG)
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
    fun renderNameShouldBe_POSTER() {
        assert(factory.render == RenderType.POSTER)
    }
}
