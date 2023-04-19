package com.javi.dynamic.list.data.controllers

import com.javi.dynamic.list.data.factories.BannerFactory
import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.render.processor.core.RenderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DefaultDynamicListControllerTest {

    @Mock
    lateinit var getProductDetailScreenUseCase: GetProductDetailPageUseCase

    private lateinit var defaultDynamicListController: DefaultDynamicListController

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        defaultDynamicListController = DefaultDynamicListController(
            delegates = mutableSetOf(
                BannerFactory(getProductDetailScreenUseCase)
            ),
            defaultDispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
