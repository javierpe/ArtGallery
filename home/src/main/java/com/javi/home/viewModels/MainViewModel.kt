package com.javi.home.viewModels

import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.presentation.viewModels.ContextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ContextViewModel() {

    override val context = ContextType.HOME

    override val requestModel = DynamicListRequestModel(
        contextType = context
    )
}