package com.javi.places.page.impl.viewModels

import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.presentation.viewModels.ContextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlacesPageViewModel @Inject constructor(): ContextViewModel() {

    override val context: ContextType = ContextType.PLACES

    override val requestModel: DynamicListRequestModel = DynamicListRequestModel(
        contextType = ContextType.PLACES
    )
}