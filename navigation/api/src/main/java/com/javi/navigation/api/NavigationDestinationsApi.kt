package com.javi.navigation.api

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

interface NavigationDestinationsApi {

    /**
     * Call first to set NavController before to use all navigation destinations api
     */
    fun setUp(navHostController: NavHostController)

    /**
     * Dispose NavController instance
     */
    fun onDispose()

    /**
     * Return to back
     */
    fun popBackStack()

    /**
     * Navigate to product detail page
     * @param launchSingle
     * @param imageURL
     */
    fun navigateToProductDetailPage(
        launchSingle: Boolean = false,
        imageURL: String
    )

    /**
     * Navigate to cards page.
     * @param id of the cards container
     * @param title of the cards
     */
    fun navigateToCardsPage(
        id: Int,
        title: String
    )

    /**
     * Navigate to any destination like deeplink.
     * @param uri like 'cards_page/1/Cactus/'
     */
    fun navigate(uri: Uri)
}