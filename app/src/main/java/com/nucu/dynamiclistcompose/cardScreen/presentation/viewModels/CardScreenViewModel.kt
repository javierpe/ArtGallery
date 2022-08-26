package com.nucu.dynamiclistcompose.cardScreen.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javier.api.NavigationController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardScreenViewModel @Inject constructor(
    private val navigationController: NavigationController
): ViewModel() {

    fun goToBack() {
        navigationController.popBackStack()
    }
}