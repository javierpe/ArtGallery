package com.nucu.dynamiclistcompose.presentation.viewModels

import app.cash.turbine.test
import com.javi.basket.api.LocalBasketApi
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.actions.DynamicListAction
import com.nucu.dynamiclistcompose.data.api.DynamicListUseCaseApi
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
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
    lateinit var useCase: DynamicListUseCaseApi

    @Mock
    lateinit var localBasketApi: LocalBasketApi

    private lateinit var dynamicListViewModel: DynamicListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        dynamicListViewModel = DynamicListViewModel(
            useCase,
            localBasketApi
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `dynamicListAction should have a initial action`() = runTest {
        dynamicListViewModel.dynamicListAction.test {
            assert(awaitItem() is com.javi.dynamic.list.data.actions.DynamicListAction.LoadingAction)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `dynamicListAction should have the state provides by useCase`() = runTest {
        val requestModel = DynamicListRequestModel(ContextType.HOME)
        whenever(useCase.get(0, requestModel)).thenReturn(
            flow { emit(com.javi.dynamic.list.data.actions.DynamicListAction.ErrorAction(Throwable("Error"))) }
        )

        dynamicListViewModel.dynamicListAction.test {
            dynamicListViewModel.load(requestModel)
            awaitItem()
            assert(awaitItem() is com.javi.dynamic.list.data.actions.DynamicListAction.ErrorAction)
            cancelAndConsumeRemainingEvents()
        }
    }
}