package com.javi.dynamic.list.domain.models

import com.javi.dynamic.list.data.models.DynamicListElement
import java.util.LinkedList
import java.util.Queue

data class ShowCaseResultModel(
    val header: List<DynamicListElement> = emptyList(),
    val body: List<DynamicListElement> = emptyList(),
    val showCaseQueue: Queue<DynamicListShowCaseModel> = LinkedList()
)
