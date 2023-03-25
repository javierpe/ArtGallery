package com.nucu.dynamiclistcompose.presentation.viewModels

import com.javi.data.enums.ContextType
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ContextViewModel() {

    override val context = ContextType.HOME

    override val requestModel = DynamicListRequestModel(
        contextType = context
    )
}