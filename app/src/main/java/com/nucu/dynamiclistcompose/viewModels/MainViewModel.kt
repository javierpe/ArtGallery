package com.nucu.dynamiclistcompose.viewModels

import com.nucu.dynamiclistcompose.models.ContextType
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ContextViewModel() {

    override val context = ContextType.HOME

    override val requestModel = DynamicListRequestModel(
        contextType = context
    )
}