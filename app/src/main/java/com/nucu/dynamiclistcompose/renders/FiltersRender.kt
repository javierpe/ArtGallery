package com.nucu.dynamiclistcompose.renders

import com.nucu.dynamiclistcompose.components.filters.FiltersModel
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender

class FiltersRender : DynamicListRender<FiltersModel> {

    override suspend fun resolve(render: String, resource: String?, source: String): FiltersModel {
        return FiltersModel(emptyList())
    }
}