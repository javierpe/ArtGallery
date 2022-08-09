package com.nucu.dynamiclistcompose.viewModels

import com.nucu.dynamiclistcompose.data.models.ContextType
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ContextViewModel() {

    override val context = ContextType.SCREEN_WITH_IMAGE

    override val requestModel = DynamicListRequestModel(
        contextType = context
    )
}