package com.javi.dynamic.list.data.models

import androidx.constraintlayout.compose.ConstraintSet

const val CONTEXT_VIEW_LAYOUT_ID = "context_view"

data class MotionLayoutProperties(
    val constraintSetStart: ConstraintSet,
    val constraintSetEnd: ConstraintSet
)