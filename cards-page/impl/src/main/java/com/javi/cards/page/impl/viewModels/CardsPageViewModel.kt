package com.javi.cards.page.impl.viewModels

import com.javi.data.enums.ContextType
import com.javi.dynamic.list.presentation.viewModels.ContextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsPageViewModel @Inject constructor() : ContextViewModel() {

    override val context: ContextType = ContextType.CARDS
}