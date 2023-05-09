package com.javi.dynamic.list.data.extensions

import com.dynamic.factory.ComponentModel
import com.javi.dynamic.list.data.models.ComponentItemModel

fun ComponentModel<Any>.toComponentItemModel(): ComponentItemModel {
    return ComponentItemModel(
        index = index,
        render = render,
        resource = resource
    )
}
