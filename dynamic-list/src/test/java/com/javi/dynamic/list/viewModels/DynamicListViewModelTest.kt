package com.javi.dynamic.list.viewModels

import app.cash.turbine.test
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.useCases.GetDynamicListUseCase
import com.javi.dynamic.list.presentation.viewModels.DynamicListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class DynamicListViewModelTest {

    @Mock
    lateinit var getDynamicListUseCase: GetDynamicListUseCase

    private lateinit var dynamicListViewModel: DynamicListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        dynamicListViewModel = DynamicListViewModel(getDynamicListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `dynamicListAction should have a initial action`() = runTest {
        dynamicListViewModel.dynamicListAction.test {
            assert(awaitItem() is DynamicListFlowState.None)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `dynamicListAction should have the state provides by useCase`() = runTest {
        val requestModel = DynamicListRequestModel(ContextType.HOME)
        whenever(getDynamicListUseCase(0, requestModel)).thenReturn(
            flow { emit(DynamicListFlowState.ErrorAction(Throwable("Error"))) }
        )

        dynamicListViewModel.dynamicListAction.test {
            dynamicListViewModel.load(requestModel)
            awaitItem()
            assert(awaitItem() is DynamicListFlowState.ErrorAction)
            cancelAndConsumeRemainingEvents()
        }
    }
}
