package com.nucu.dynamiclistcompose.viewModels

import com.nucu.dynamiclistcompose.models.AnalyticSources
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ContextViewModel() {

    override val source = AnalyticSources.MARKET

    override val requestModel = DynamicListRequestModel(
        context = "",
        aSources = source
    )
}