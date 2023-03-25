package com.nucu.dynamiclistcompose.data.controllers

import com.javi.render.data.RenderType
import com.nucu.dynamiclistcompose.data.factories.BannerFactory
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
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
class DefaultDynamicListControllerTest {

    private lateinit var defaultDynamicListController: DefaultDynamicListController

    private val dispatcher = StandardTestDispatcher()

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.BANNER.value.lowercase(),
            index = 0,
            resource = String()
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        defaultDynamicListController = DefaultDynamicListController(
            delegates = mutableSetOf(
                BannerFactory()
            ),
            defaultDispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `mapComponents should have componentItemModel`() = runTest {

        whenever(tooltipPreferencesApi.getBooleanState(
            RenderType.BANNER.value,
            false
        )).thenReturn(flow { emit(false) })

        assert(defaultDynamicListController.getMapComponents().contains(componentItemModel))
    }

    @Test
    fun `skeletons should have BANNER render`() {
        defaultDynamicListController.dispatchSkeletons(
            renderTypes = listOf(
                RenderType.BANNER
            )
        )

        assert(defaultDynamicListController.getMapSkeletons().contains(RenderType.BANNER))
    }
}