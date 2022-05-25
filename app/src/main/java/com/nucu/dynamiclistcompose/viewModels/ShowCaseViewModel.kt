package com.nucu.dynamiclistcompose.viewModels

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nucu.dynamiclistcompose.api.TooltipPreferencesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowCaseViewModel @Inject constructor(
    private val tooltipPreferencesApi: TooltipPreferencesApi
): ViewModel() {

    private val _isShowed = MutableStateFlow(true)
    val isShowed: StateFlow<Boolean> = _isShowed

    fun isShowed(key: String) {
        viewModelScope.launch {
            _isShowed.value = tooltipPreferencesApi.getState(
                booleanPreferencesKey(key),
                false
            ).first()
        }
    }

    fun setShowed(key: String) {
        viewModelScope.launch {
            tooltipPreferencesApi.saveState(
                booleanPreferencesKey(key),
                true
            )
        }
    }
}