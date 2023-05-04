package com.javi.dynamic.list.data.models

import com.javi.dynamic.list.data.actions.ScrollAction
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class ComponentInfo(
    val dynamicListObject: DynamicListObject,
    val showCaseState: ShowCaseState,
    val scrollAction: ((ScrollAction) -> Unit)? = null
) {
    fun navigator(): DestinationsNavigator? {
        return dynamicListObject.destinationsNavigator
    }
}
