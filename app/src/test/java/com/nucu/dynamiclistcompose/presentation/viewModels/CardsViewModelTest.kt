package com.nucu.dynamiclistcompose.presentation.viewModels

import com.javier.api.NavigationController
import com.ramcosta.composedestinations.spec.Direction
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CardsViewModelTest {

    @Mock
    lateinit var direction: Direction

    @Mock
    lateinit var navigationController: NavigationController

    private lateinit var viewModel: CardsViewModel

    @Before
    fun setUp() {
        viewModel = CardsViewModel(
            navigationController
        )
    }

    @Test
    fun `navigateTo should be called when loadBanner is executed`() {
        viewModel.navigateToCardsDetail(direction)
        verify(navigationController).navigateTo(direction)
    }
}