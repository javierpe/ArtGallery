package com.javi.navigation.impl

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import com.javi.card.page.destinations.CardsPageDestination
import com.javi.navigation.api.NavigationDestinationsApi
import com.javi.product.detail.presentation.screens.destinations.ProductImageScreenDestination
import com.ramcosta.composedestinations.navigation.navigate
import javax.inject.Inject

const val ANIMATION_DURATION = 300
private const val MESSAGE = "Call first to setUp to set a NavController!"

class NavigationDestinationsImpl @Inject constructor(

): NavigationDestinationsApi {

    private var navController: NavController? = null

    override fun setUp(navController: NavController) {
        this.navController = navController
    }

    override fun popBackStack() {
        checkNavController()

        navController?.popBackStack()
    }

    override fun navigateToProductDetailPage(
        launchSingle: Boolean,
        imageURL: String
    ) {

        checkNavController()

        navController?.navigate(
            direction = ProductImageScreenDestination(
                imageUrl = imageURL
            ),
            navOptionsBuilder = {
                fadeIn(
                    tween(ANIMATION_DURATION)
                )
            }
        )
    }

    override fun navigateToCardsPage(id: Int, title: String) {
        checkNavController()

        navController?.navigate(
            direction = CardsPageDestination(
                id = id,
                title = title
            )
        )
    }

    private fun checkNavController() {
        require(navController != null) {
            MESSAGE
        }
    }
}