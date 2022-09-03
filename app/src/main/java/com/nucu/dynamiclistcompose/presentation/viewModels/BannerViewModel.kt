package com.nucu.dynamiclistcompose.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javier.api.NavigationController
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    /*
    * Add here analytics, navigation, preferences, etc.
    * */
    private val navigationController: NavigationController
) : ViewModel() {

    fun loadBanner(direction: Direction) {
        navigationController.navigateTo(
            direction
        )
    }
}