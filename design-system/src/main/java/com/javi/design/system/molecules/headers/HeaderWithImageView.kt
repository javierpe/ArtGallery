package com.javi.design.system.molecules.headers

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.javi.design.system.atoms.BackButtonComponentView
import com.javi.design.system.data.BACKGROUND
import com.javi.design.system.data.BACK_BUTTON_BACKGROUND
import com.javi.design.system.data.BACK_BUTTON_ICON_COLOR
import com.javi.design.system.data.TEXT_COLOR
import com.javi.design.system.theme.HeaderDark
import com.javi.design.system.theme.HeaderLight
import com.javi.design.system.theme.Typography

const val DURATION = 500
const val MAX_HEIGHT = 120
const val MIN_HEIGHT = 65

@OptIn(ExperimentalMotionApi::class)
@Composable
fun HeaderWithImageView(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    bodyLazyListState: LazyListState? = null,
    onIconClick: () -> Unit
) {
    val backgroundLayoutId = "background"
    val titleLayoutId = "title"
    val backButtonLayoutId = "back_button"

    val systemUiController = rememberSystemUiController()

    val firstVisibleItem by remember {
        derivedStateOf {
            bodyLazyListState?.firstVisibleItemIndex ?: 0
        }
    }

    val progress by animateFloatAsState(
        targetValue = if (firstVisibleItem in -1..0) 0f else 1f,
        tween(DURATION)
    )

    val motionHeight by animateDpAsState(
        targetValue = if (firstVisibleItem in -1..0) MAX_HEIGHT.dp else MIN_HEIGHT.dp,
        tween(DURATION)
    )

    /**
     * First mode
     *
     *  val (backgroundRef, titleRef, backButtonRef) = createRefs()

     val constraintSetStart = ConstraintSet {
     constrain(backgroundRef) {
     start.linkTo(parent.start)
     end.linkTo(parent.end)
     top.linkTo(parent.top)

     height = Dimension.value(120.dp)
     }

     constrain(titleRef) {
     bottom.linkTo(parent.bottom)
     start.linkTo(parent.start, 16.dp)
     top.linkTo(backButtonRef.bottom)
     }

     constrain(backButtonRef) {
     start.linkTo(parent.start, 16.dp)
     top.linkTo(parent.top, 16.dp)
     }
     }

     val constraintSetEnd = ConstraintSet {
     constrain(backgroundRef) {
     start.linkTo(parent.start)
     end.linkTo(parent.end)
     top.linkTo(parent.top)

     height = Dimension.value(65.dp)
     }

     constrain(titleRef) {
     bottom.linkTo(backButtonRef.bottom)
     start.linkTo(backButtonRef.end, 10.dp)
     top.linkTo(backButtonRef.top)
     }

     constrain(backButtonRef) {
     start.linkTo(parent.start, 16.dp)
     top.linkTo(parent.top, 16.dp)
     }
     }
     */

    /**
     * Second mode
     *     val motionSceneContent = remember {
     context.resources
     .openRawResource(R.raw.motion_scene)
     .readBytes()
     .decodeToString()
     }
     */

    /**
     * For third mode check motion_scene.json5
     */

    val colorStart = if (isSystemInDarkTheme()) {
        MaterialTheme.colors.surface
    } else {
        HeaderDark
    }

    val colorEnd = if (isSystemInDarkTheme()) {
        MaterialTheme.colors.onSecondary
    } else {
        HeaderLight
    }

    MotionLayout(
        start = constraintSetStart(colorStart),
        end = constraintSetEnd(colorEnd),
        progress = progress,
        modifier = modifier.height(motionHeight)
    ) {
        val backgroundProperties = motionProperties(backgroundLayoutId)
        val titleProperties = motionProperties(titleLayoutId)
        val backButtonProperties = motionProperties(backButtonLayoutId)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(backgroundLayoutId)
                .background(
                    backgroundProperties.value.color(BACKGROUND)
                ),
        )

        systemUiController.setStatusBarColor(
            color = backgroundProperties.value.color(BACKGROUND),
            darkIcons = backgroundProperties.value.color(BACKGROUND) == MaterialTheme.colors.surface
        )

        systemUiController.setNavigationBarColor(
            color = if (isSystemInDarkTheme()) MaterialTheme.colors.onSecondary else Color.White,
            darkIcons = backgroundProperties.value.color(BACKGROUND) == MaterialTheme.colors.surface
        )

        Text(
            modifier = Modifier.layoutId(titleLayoutId),
            text = title,
            style = Typography.h5,
            textAlign = TextAlign.Left,
            color = titleProperties.value.color(TEXT_COLOR)
        )

        BackButtonComponentView(
            modifier = Modifier.layoutId(backButtonLayoutId),
            onClick = onIconClick,
            backgroundColor = backButtonProperties.value.color(BACK_BUTTON_BACKGROUND),
            iconColor = backButtonProperties.value.color(BACK_BUTTON_ICON_COLOR),
            icon = icon
        )
    }
}

@Suppress("ImplicitDefaultLocale", "MagicNumber")
fun Int.hexToString() = String.format("#%06X", 0xFFFFFF and this)

private fun constraintSetStart(background: Color) = ConstraintSet(
    """ {
	background: { 
		start: ['parent', 'start'],
		end: ['parent', 'end'],
		top: ['parent', 'top'],
        height: $MAX_HEIGHT,
        custom: {
          background: '${background.toArgb().hexToString()}'
        }
	},
	title: {
		bottom: ['parent', 'bottom'],
		start: ['parent', 'start', 16],
        top: ['back_button', 'bottom'],
        custom: {
          textColor: '#FFFFFF'
        }
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

private fun constraintSetEnd(background: Color) = ConstraintSet(
    """ {
	background: { 
		start: ['parent', 'start'],
		end: ['parent', 'end'],
		top: ['parent', 'top'],
        height: $MIN_HEIGHT,
        custom: {
          background: '${background.toArgb().hexToString()}'
        }
	},
	title: {
		bottom: ['back_button', 'bottom'],
		start: ['back_button', 'end', 10],
        top: ['back_button', 'top'],
        custom: {
          textColor: '#000000'
        }
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
