package com.javi.navigation.api

import androidx.navigation.NavController

interface NavigationDestinationsApi {

    /**
     * Call first to set NavController before to use all navigation destinations api
     */
    fun setUp(navController: NavController)

    fun popBackStack()

    fun navigateToProductDetailPage(imageURL: String)

    fun navigateToCardsPage(
        id: Int,
        title: String
    )
}