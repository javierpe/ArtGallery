package com.nucu.dynamiclistcompose.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javier.api.NavigationController
import com.javier.api.models.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    /*
    * Add here analytics, navigation, preferences, etc.
    * */
    private val navigationController: NavigationController
) : ViewModel() {

    fun loadBanner(bannerImageURL: String) {
        navigationController.navigateTo(
            Route.BannerScreen.name,
            listOf(
                URLEncoder.encode(bannerImageURL, StandardCharsets.UTF_8.toString())
            )
        )
    }
}