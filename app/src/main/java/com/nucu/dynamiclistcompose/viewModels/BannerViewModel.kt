package com.nucu.dynamiclistcompose.viewModels

import androidx.lifecycle.ViewModel
import com.javier.api.NavigationController
import com.javier.api.models.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    /*
    * Add here analytics, navigation, preferences, etc.
    * */
    private val navigationController: NavigationController
) : ViewModel() {

    fun loadBanner(
        bannerIndex: Int,
        bannerText: String
    ) {
        navigationController.navigateTo(
            Route.BannerScreen.name, listOf(bannerIndex.toString(), bannerText)
        )
    }
}