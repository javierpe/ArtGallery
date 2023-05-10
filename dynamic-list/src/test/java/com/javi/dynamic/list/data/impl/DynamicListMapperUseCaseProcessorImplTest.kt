package com.javi.dynamic.list.data.impl

import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.ContainerModel
import com.javi.dynamic.list.domain.api.RenderMapperProcessor
import com.javi.dynamic.list.domain.renders.BannerCarouselMapperUseCase
import com.javi.dynamic.list.domain.renders.base.DynamicListMapperUseCase
import com.javi.dynamic.list.domain.useCases.RenderMapperProcessorUseCaseImpl
import com.javi.dynamic.list.presentation.components.text.TextModel
import com.javi.render.processor.core.RenderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DynamicListMapperUseCaseProcessorImplTest {

    private lateinit var render: BannerCarouselMapperUseCase

    private lateinit var renders: MutableSet<DynamicListMapperUseCase<*>>

    @Mock
    lateinit var renderMapperProcessor: RenderMapperProcessor

    private lateinit var dynamicListRenderProcessorUseCaseImpl: RenderMapperProcessorUseCaseImpl

    private val containerModel by lazy {
        ContainerModel(
            body = listOf(
                ComponentItemModel(
                    index = 0,
                    render = RenderType.TEXT.value,
                    resource = TextModel("Test"),
                    isChanged = false
                )
            ),
            header = emptyList()
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = BannerCarouselMapperUseCase()
        renders = mutableSetOf(render)
        dynamicListRenderProcessorUseCaseImpl = RenderMapperProcessorUseCaseImpl(
            renderMapperProcessor
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Render resource should match with some item in renders`() = runTest {
        val result = dynamicListRenderProcessorUseCaseImpl.invoke(
            containerModel
        )

        val mapper = (result as? DynamicListFlowState.MapperResultAction)?.body

        assert(
            !mapper.isNullOrEmpty()
        ) {
            "Result should be MapperResultAction and must have body"
        }
    }
}
