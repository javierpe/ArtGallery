package com.javi.home.impl.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.constraintlayout.compose.ConstraintSet

@Suppress("ImplicitDefaultLocale", "MagicNumber")
fun Int.hexToString() = String.format("#%06X", 0xFFFFFF and this)

fun constraintSetStart(
    textColor: Color
) = ConstraintSet(
    """ {
    context_view: {
        start: ['parent', 'start'],
        end: ['parent', 'end'],
        bottom: ['parent', 'bottom'],
        top: ['title', 'bottom'],
        height: 'preferWrap',
        width: 'parent'
    },
	title: {
		start: ['parent', 'start', 16],
        top: ['back_button', 'bottom'],
        custom: {
          textColor: '${textColor.toArgb().hexToString()}'
        },
	},
	back_button: {
		start: ['parent', 'start', 16],
		top: ['parent', 'top', 16],
        custom: {
          back_button_background: '#FFFFFF',
          back_button_icon_color: '#000000'
        }
	}
} """
)

fun constraintSetEnd(
    textColor: Color
) = ConstraintSet(
    """ {
    context_view: {
        start: ['parent', 'start'],
        end: ['parent', 'end'],
        bottom: ['parent', 'bottom'],
        top: ['title', 'bottom'],
        height: 'preferWrap',
        width: 'parent'
    },
	title: {
		start: ['back_button', 'end', 10],
		top: ['parent', 'top', 16],
        height: 'preferWrap',
        custom: {
          textColor: '${textColor.toArgb().hexToString()}'
        },
	},
	back_button: {
		start: ['parent', 'start', 16],
		top: ['parent', 'top', 16],
        custom: {
          back_button_background: '#000000',
          back_button_icon_color: '#FFFFFF'
        }
	}
} """
)
