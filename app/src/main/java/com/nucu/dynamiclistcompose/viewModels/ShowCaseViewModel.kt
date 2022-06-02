package com.nucu.dynamiclistcompose.viewModels

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nucu.dynamiclistcompose.api.TooltipPreferencesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowCaseViewModel @Inject constructor(
    private val tooltipPreferencesApi: TooltipPreferencesApi
): ViewModel() {

    fun setShowed(key: String) {
        viewModelScope.launch {
            tooltipPreferencesApi.saveState(
                booleanPreferencesKey(key),
                true
            )
        }
    }
}