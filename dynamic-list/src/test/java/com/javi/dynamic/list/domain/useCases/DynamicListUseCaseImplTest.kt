package com.javi.dynamic.list.domain.useCases

import app.cash.turbine.test
import com.javi.basket.api.BasketUpdatesUseCase
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.repositories.DynamicListRepository
import com.javi.dynamic.list.data.useCases.GetSkeletonsByContextUseCase
import com.javi.dynamic.list.data.useCases.GetTooltipSequenceUseCase
import com.javi.dynamic.list.data.useCases.SaveSkeletonsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DynamicListUseCaseImplTest {

    @Mock
    lateinit var repository: DynamicListRepository

    @Mock
    lateinit var getDynamicListShowCaseUseCaseImpl: GetTooltipSequenceUseCase

    @Mock
    lateinit var saveSkeletonsUseCase: SaveSkeletonsUseCase

    @Mock
    lateinit var getSkeletonsByContextUseCase: GetSkeletonsByContextUseCase

    @Mock
    lateinit var basketUpdatesUseCase: BasketUpdatesUseCase

    private lateinit var dynamicListUseCaseImpl: DynamicListUseCaseImpl

    private val dispatcher = StandardTestDispatcher()

    private val dynamicListRequestModel by lazy {
        DynamicListRequestModel(ContextType.HOME)
    }

    private val successAction by lazy {
        DynamicListFlowState.SuccessAction(
            header = emptyList(),
            body = emptyList()
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        dynamicListUseCaseImpl = DynamicListUseCaseImpl(
            dispatcher,
            repository,
            getDynamicListShowCaseUseCaseImpl,
            saveSkeletonsUseCase,
            getSkeletonsByContextUseCase,
            basketUpdatesUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LoadingAction should return first`() = runTest {
        whenever(getSkeletonsByContextUseCase.invoke(dynamicListRequestModel.contextType.source)).thenReturn(
            emptyList()
        )

        assert(
            dynamicListUseCaseImpl(0, dynamicListRequestModel).first() is DynamicListFlowState.WithoutSkeletonDataAction
        )
    }

    @Test
    fun `SkeletonAction should return when skeleton database have data`() = runTest {
        whenever(
            repository.get(0, dynamicListRequestModel)
        ).thenReturn(flow { emit(successAction) })

        dynamicListUseCaseImpl(0, dynamicListRequestModel).first() is DynamicListFlowState.SkeletonDataAction
    }

    @Suppress("TooGenericExceptionThrown")
    @Test
    fun `ErrorAction should be return when an exception happens in the repository`() = runTest {
        whenever(repository.get(0, dynamicListRequestModel)).thenReturn(
            flow { throw Exception("Crash!") }
        )

        dynamicListUseCaseImpl.invoke(0, dynamicListRequestModel).test {
            awaitItem()
            assert(awaitItem() is DynamicListFlowState.ErrorAction)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
