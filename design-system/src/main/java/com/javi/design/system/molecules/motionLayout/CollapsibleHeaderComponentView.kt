package com.javi.design.system.molecules.motionLayout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.DebugFlags
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutScope
import com.javi.design.system.molecules.headers.DURATION

@Composable
fun CollapsibleHeaderComponentView(
    constraintSetStart: ConstraintSet,
    constraintSetEnd: ConstraintSet,
    bodyListState: LazyListState,
    contentHeader: @Composable (MotionLayoutScope) -> Unit,
    contentBody: @Composable (MotionLayoutScope) -> Unit
) {

    val firstVisibleItem by remember {
        derivedStateOf {
            bodyListState.firstVisibleItemIndex
        }
    }

    val progress by animateFloatAsState(
        targetValue = if (firstVisibleItem in -1..0) 0f else 1f,
        tween(DURATION),
        label = ""
    )

    MotionLayout(
        start = constraintSetStart,
        end = constraintSetEnd,
        progress = progress,
        modifier = Modifier.fillMaxSize(),
        debugFlags = DebugFlags.None,
    ) {
        contentHeader(this)
        contentBody(this)
    }
}