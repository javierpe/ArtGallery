package com.nucu.dynamiclistcompose.ui.examples.viewModels

import androidx.lifecycle.ViewModel
import com.javier.api.NavigationController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BannerScreenViewModel @Inject constructor(
    private val navigationController: NavigationController
) : ViewModel() {

    fun onBackPressed() {
        navigationController.popBackStack()
    }
}