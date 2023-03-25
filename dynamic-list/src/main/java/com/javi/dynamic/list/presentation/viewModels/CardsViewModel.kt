package com.javi.dynamic.list.presentation.viewModels

import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(): ContextViewModel() {

    override val context: ContextType = ContextType.CARDS
    override val requestModel: DynamicListRequestModel = DynamicListRequestModel(
        contextType = ContextType.CARDS
    )
}