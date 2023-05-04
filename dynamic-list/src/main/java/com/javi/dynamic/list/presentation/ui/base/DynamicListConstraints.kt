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
            bottom.linkTo(parent.bottom)
            height = Dimension.preferredWrapContent
        }
    }
}

fun mediumConstraints(
    haveHeader: Boolean = false,
    haveBody: Boolean = false
): ConstraintSet {
    return ConstraintSet {
        val header = createRefFor(HEADER_ID)
        val body = createRefFor(BODY_ID)

        if (haveHeader) {
            constrain(header) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
                bottom.linkTo(parent.bottom)

                width = if (haveBody) {
                    end.linkTo(body.start, 8.dp)
                    Dimension.wrapContent
                } else {
                    end.linkTo(parent.start, 16.dp)
                    Dimension.fillToConstraints
                }

            }
        }

        if (haveBody) {
            constrain(body) {
                end.linkTo(parent.end, 16.dp)
                top.linkTo(parent.top, 16.dp)
                bottom.linkTo(parent.bottom)
                width = if (haveHeader) {
                    start.linkTo(header.end, 8.dp)
                    Dimension.preferredWrapContent
                } else {
                    start.linkTo(parent.start, 16.dp)
                    Dimension.fillToConstraints
                }
            }
        }
    }
}