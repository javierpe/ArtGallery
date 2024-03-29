package com.javi.dynamic.list.domain.useCases

import com.javi.api.TooltipPreferencesApi
import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.domain.models.DynamicListShowCaseModel
import com.javi.dynamic.list.presentation.factories.TextFactory
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.render.processor.core.RenderType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.util.LinkedList
import java.util.Queue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetDynamicListShowCaseUseCaseTest {

    @Mock
    lateinit var tooltipPreferencesApi: TooltipPreferencesApi

    private lateinit var getDynamicListShowCaseUseCase: GetTooltipSequenceUseCaseImpl

    private val testFactory = TextFactory()

    private val delegates by lazy {
        mutableSetOf<DynamicListFactory>(
            testFactory
        )
    }

    private val body by lazy {
        listOf(
            ComponentItemModel(render = RenderType.TEXT.value.lowercase(), index = 0, resource = String())
        )
    }

    private val header by lazy {
        listOf(
            ComponentItemModel(render = RenderType.TEXT.value.lowercase(), index = 0, resource = String())
        )
    }

    @Before
    fun setUp() {
        getDynamicListShowCaseUseCase = GetTooltipSequenceUseCaseImpl(
            delegates,
            tooltipPreferencesApi
        )
    }

    @Test
    fun `LoadingAction should return first`() = runTest {
        val showCaseSequence: Queue<DynamicListShowCaseModel> = LinkedList()

        whenever(
            tooltipPreferencesApi.getBooleanState(
                RenderType.TEXT.value.lowercase(),
                false
            )
        ).thenReturn(flow { emit(false) })

        showCaseSequence.add(
            DynamicListShowCaseModel(RenderType.TEXT.value.lowercase(), 0)
        )

        showCaseSequence.add(
            DynamicListShowCaseModel("", 0, true)
        )

        val result = getDynamicListShowCaseUseCase(
            header = header,
            body = body
        )

        val expect = DynamicListFlowState.SuccessAction(
            body = arrayListOf(
                DynamicListElement(testFactory, body.first())
            ),
            header = arrayListOf(
                DynamicListElement(testFactory, body.first())
            ),
            showCaseQueue = showCaseSequence
        )

        assert(result.body == expect.body) {
            "Assertion body result!"
        }

        assert(result.header == expect.header) {
            "Assertion header result!"
        }

        assert(result.showCaseQueue == expect.showCaseQueue) {
            "Assertion showCaseQueue result!"
        }
    }
}
