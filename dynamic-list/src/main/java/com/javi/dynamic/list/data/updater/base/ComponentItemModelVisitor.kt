package com.javi.dynamic.list.data.updater.base

import com.javi.dynamic.list.data.models.ComponentItemModel

abstract class ComponentItemModelVisitor(
    private val currentBody: List<ComponentItemModel>
) {
    abstract suspend fun update(): List<ComponentItemModel>

    fun propagate(
        updated: List<ComponentItemModel>
    ): List<ComponentItemModel> {
        val propagatedData = updated.zip(currentBody) { new, original ->
            if (original != new) {
                listOf(new.copy(isChanged = true))
            } else {
                listOf(original.copy(isChanged = false))
            }
        }.flatten()

        return propagatedData
    }
}
