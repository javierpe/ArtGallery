package com.javi.home.impl.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.presentation.viewModels.ContextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ContextViewModel() {

    override val context = ContextType.HOME

    init {
        viewModelScope.launch {
            dynamicListStateListener.collect {
                Log.e("StateListener_MainViewModel", it.toString())
            }
        }
    }
}
