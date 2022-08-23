package com.nucu.dynamiclistcompose

import com.javier.api.NavigationController
import com.javier.api.models.Route
import com.nucu.dynamiclistcompose.presentation.viewModels.BannerViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class BannerViewModelTest {

    @Mock
    lateinit var navigationController: NavigationController

    private lateinit var viewModel: BannerViewModel

    @Before
    fun setUp() {
        viewModel = BannerViewModel(
            navigationController
        )
    }

    @Test
    fun `navigateTo should be called when loadBanner is executed`() {
        viewModel.loadBanner(String())
        verify(navigationController).navigateTo(Route.BannerScreen.name, listOf(String()))
    }
}