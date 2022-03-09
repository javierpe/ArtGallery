package com.nucu.dynamiclistcompose.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nucu.dynamiclistcompose.models.DynamicListComponentAction
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicListComposeViewModel @Inject constructor(

) : ViewModel(), DynamicListComponentAction {

    private val _scrollAction = MutableStateFlow<ScrollAction>(ScrollAction.None)
    val scrollAction: StateFlow<ScrollAction> = _scrollAction

    override fun scrollAction(scrollAction: ScrollAction) {
        viewModelScope.launch {
            _scrollAction.emit(scrollAction)
        }
    }
}