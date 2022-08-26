package com.nucu.dynamiclistcompose.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javier.api.NavigationController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeaderViewModel @Inject constructor(
    private val navigationController: NavigationController
) : ViewModel() {

    fun isHome(): Boolean {
        return navigationController.isHome()
    }

    fun handleIconClick() {
        if (navigationController.hasBackElement()) {
            navigationController.popBackStack()
        }
    }
}