package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

fun compactConstraints(): ConstraintSet {
    return ConstraintSet {
        val header = createRefFor(HEADER_ID)
        val body = createRefFor(BODY_ID)

        constrain(header) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(body) {
            top.linkTo(header.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}

fun mediumConstraints(): ConstraintSet {
    return ConstraintSet {
        val header = createRefFor(HEADER_ID)
        val bgHeader = createRefFor(BG_HEADER_ID)

        val body = createRefFor(BODY_ID)
        val bgBody = createRefFor(BG_BODY_ID)

        constrain(bgHeader) {
            top.linkTo(header.top, 16.dp)
            start.linkTo(header.start, 16.dp)
            bottom.linkTo(header.bottom, 16.dp)
            end.linkTo(header.end, 8.dp)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(bgBody) {
            top.linkTo(body.top, 16.dp)
            start.linkTo(body.start, 8.dp)
            bottom.linkTo(body.bottom, 16.dp)
            end.linkTo(body.end, 16.dp)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(header) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            end.linkTo(body.start)

            width = Dimension.wrapContent
        }

        constrain(body) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(header.end)

            width = Dimension.preferredWrapContent
        }
    }
}