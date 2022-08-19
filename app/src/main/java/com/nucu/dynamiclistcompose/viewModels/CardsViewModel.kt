package com.nucu.dynamiclistcompose.viewModels

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.javier.api.NavigationController
import com.javier.api.models.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val navigationController: NavigationController,
    private val gson: Gson
): ViewModel() {

    fun navigateToCardsDetail(
        cardText: String,
        cardImageUrls: List<String>
    ) {
        navigationController.navigateTo(
            Route.CardScreen,
            listOf(
                cardText,
                gson.toJson(
                    cardImageUrls.map {
                        URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                    }
                )
            )
        )
    }
}