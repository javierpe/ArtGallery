package com.nucu.dynamiclistcompose.presentation.viewModels

import com.javier.api.NavigationController
import com.nucu.dynamiclistcompose.destinations.BannerScreenDestination
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
    fun `navigateTo should be called when navigateToCardsDetail is executed`() {
        viewModel.loadBanner(String())
        verify(navigationController).navigateTo(
            BannerScreenDestination(
                String()
            )
        )
    }
}