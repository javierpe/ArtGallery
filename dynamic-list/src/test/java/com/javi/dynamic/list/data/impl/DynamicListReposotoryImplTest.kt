package com.javi.dynamic.list.data.impl

import app.cash.turbine.test
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.dataSources.DynamicListDataSourceApi
import com.javi.dynamic.list.data.models.DataContentModel
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.useCases.DynamicListRenderProcessorApi
import com.javi.dynamic.list.domain.repository.DynamicListRepositoryImpl
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
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class dynamicListRepositoryImplTest {

    @Mock
    lateinit var dynamicListDataSourceApi: DynamicListDataSourceApi

    @Mock
    lateinit var dynamicListRenderProcessorApi: DynamicListRenderProcessorApi

    private lateinit var dynamicListRepositoryImpl: DynamicListRepositoryImpl

    private val dynamicListRequestModel by lazy {
        DynamicListRequestModel(ContextType.HOME)
    }

    private val defaultDataContentModel by lazy {
        DataContentModel(
            header = emptyList(),
            body = emptyList()
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        dynamicListRepositoryImpl = DynamicListRepositoryImpl(
            dynamicListRenderProcessorApi = dynamicListRenderProcessorApi,
            dynamicListMockResponseApi = dynamicListDataSourceApi
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `SuccessAction should return`() = runTest {
        whenever(
            dynamicListDataSourceApi.getDataFromAsset(
                DynamicListRequestModel(
                    contextType = ContextType.HOME
                )
            )
        ).thenReturn(
            defaultDataContentModel
        )

        dynamicListRepositoryImpl.get(0, dynamicListRequestModel).test {
            assert(awaitItem() is DynamicListUIState.SuccessAction)
            cancelAndConsumeRemainingEvents()
        }
    }
}