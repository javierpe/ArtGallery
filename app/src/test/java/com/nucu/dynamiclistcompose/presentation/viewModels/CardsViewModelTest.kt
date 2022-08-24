package com.nucu.dynamiclistcompose.presentation.viewModels

import com.google.gson.Gson
import com.javier.api.NavigationController
import com.javier.api.models.Route
import com.nucu.dynamiclistcompose.presentation.viewModels.CardsViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CardsViewModelTest {

    @Mock
    lateinit var navigationController: NavigationController

    @Mock
    lateinit var gson: Gson

    private lateinit var viewModel: CardsViewModel

    @Before
    fun setUp() {
        viewModel = CardsViewModel(
            navigationController,
            gson
        )
    }

    @Test
    fun `navigateTo should be called when loadBanner is executed`() {
        viewModel.navigateToCardsDetail(
            String(),
            listOf(String(), String())
        )

        verify(navigationController).navigateTo(
            Route.CardScreen,
            listOf(
                String(),
                gson.toJson(
                    listOf(String(), String())
                )
            )
        )
    }
}