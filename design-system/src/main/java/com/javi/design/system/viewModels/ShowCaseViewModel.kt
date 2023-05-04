package com.javi.design.system.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javi.api.TooltipPreferencesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowCaseViewModel @Inject constructor(
    private val tooltipPreferencesApi: TooltipPreferencesApi
) : ViewModel() {

    private var job: Job? = null

    fun setShowed(key: String) {
        job?.cancel()
        job = viewModelScope.launch {
            tooltipPreferencesApi.saveBooleanState(
                key,
                true
            )
        }
    }
}
