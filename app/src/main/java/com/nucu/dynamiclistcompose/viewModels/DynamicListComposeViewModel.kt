package com.nucu.dynamiclistcompose.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nucu.dynamiclistcompose.actions.DynamicListComponentAction
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicListComposeViewModel @Inject constructor(

) : ViewModel(), DynamicListComponentAction {

    private val _scrollAction = MutableStateFlow<ScrollAction>(ScrollAction.None)
    val scrollAction: StateFlow<ScrollAction> = _scrollAction

    var data: List<ComponentItemModel>? = null

    override fun scrollAction(scrollAction: ScrollAction) {
        viewModelScope.launch {
            when (scrollAction) {
                is ScrollAction.ScrollWithTooltip -> { /* */ }
                is ScrollAction.ScrollRender -> {
                    val element = data?.firstOrNull {
                        it.render == scrollAction.renderType.value
                    }

                    element?.let {
                        data?.indexOf(element)?.let {
                            _scrollAction.value = ScrollAction.ScrollIndex(it, scrollAction.target)
                        }
                    }
                }
                else -> {
                    _scrollAction.value = scrollAction
                }
            }
        }
    }
}