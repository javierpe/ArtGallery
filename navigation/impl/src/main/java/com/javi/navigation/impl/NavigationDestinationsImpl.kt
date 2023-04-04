package com.javi.navigation.impl

import android.net.Uri
import androidx.navigation.NavHostController
import com.javi.card.page.destinations.CardsPageDestination
import com.javi.navigation.api.NavigationDestinationsApi
import com.javi.product.detail.presentation.screens.destinations.ProductImageScreenDestination
import com.ramcosta.composedestinations.navigation.navigate
import javax.inject.Inject

const val ANIMATION_DURATION = 300
private const val MESSAGE = "Call first to setUp to set a navHostController!"

class NavigationDestinationsImpl @Inject constructor(

): NavigationDestinationsApi {

    private var navHostController: NavHostController? = null

    override fun setUp(navHostController: NavHostController) {
        this.navHostController = navHostController
    }

    override fun onDispose() {
        this.navHostController = null
    }

    override fun popBackStack() {
        checknavHostController()

        navHostController?.popBackStack()
    }

    override fun navigateToProductDetailPage(
        launchSingle: Boolean,
        imageURL: String
    ) {

        checknavHostController()

        navHostController?.navigate(
            ProductImageScreenDestination(
                imageUrl = imageURL
            )
        )
    }

    override fun navigateToCardsPage(id: Int, title: String) {
        checknavHostController()

        navHostController?.navigate(
            direction = CardsPageDestination(
                id = id,
                title = title
            )
        )
    }

    override fun navigate(uri: Uri) {
        checknavHostController()

        //navHostController?.navigate(deep)
    }

    private fun checknavHostController() {
        require(navHostController != null) {
            MESSAGE
        }
    }
}