package com.javi.design.system.molecules.headers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.javi.data.enums.ContextType
import com.javi.design.system.atoms.BackButtonComponentView
import com.javi.design.system.data.showCase.ShapeType
import com.javi.design.system.data.showCase.ShowCaseStrategy
import com.javi.design.system.molecules.TooltipView
import com.javi.design.system.molecules.showCase.ShowCaseStyle
import com.javi.design.system.molecules.showCase.asShowCaseTarget
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.design.system.theme.Typography
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Define header design by context
 */
@Composable
fun DynamicListHeaderComponentView(
    title: String,
    contextType: ContextType,
    bodyLazyListState: LazyListState? = null,
    destinationsNavigator: DestinationsNavigator? = null
) {
    val icon = if (contextType == ContextType.HOME) Icons.Default.Star else Icons.Default.ArrowBack

    when (contextType) {

        ContextType.BANNER_DETAIL -> {
            SimpleHeaderView(
                title = title,
                icon = icon,
                onIconClick = {
                    destinationsNavigator?.popBackStack()
                }
            )
        }

        ContextType.HOME, ContextType.CARDS -> {
            HeaderWithImageView(
                title = title,
                bodyLazyListState = bodyLazyListState,
                icon = icon,
                onIconClick = {
                    if (contextType != ContextType.HOME) {
                        destinationsNavigator?.popBackStack()
                    }
                }
            )
        }
        else -> {
            SimpleHeaderView(
                title = title
            )
        }
    }
}

@Composable
fun SimpleHeaderView(
    title: String,
    icon: ImageVector? = null,
    onIconClick: (() -> Unit)? = null
) {
    val showCaseState = rememberShowCaseState()

    Column {
        ConstraintLayout(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
        ) {
            val (backRef, titleRef) = createRefs()

            icon?.let {
                BackButtonComponentView(
                    modifier = Modifier
                        .constrainAs(backRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .asShowCaseTarget(
                            index = 0,
                            style = ShowCaseStyle.Default.copy(shapeType = ShapeType.CIRCLE),
                            content = {
                                TooltipView(text = "Aquí puedes dar back")
                            },
                            strategy = ShowCaseStrategy(onlyUserInteraction = true),
                            key = "back-button",
                            state = showCaseState
                        ),
                    onClick = onIconClick,
                    iconColor = MaterialTheme.colors.secondary,
                    icon = it
                )
            }

            Text(
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(backRef.top)
                    bottom.linkTo(backRef.bottom)
                    start.linkTo(backRef.end, 10.dp)
                },
                text = title,
                style = Typography.h6,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHeaderComponentView() {
    DynamicListComposeTheme {
        DynamicListHeaderComponentView(
            title = "Hello from the header view of DynamicList",
            contextType = ContextType.HOME,
            rememberLazyListState()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSimpleHeaderComponentView() {
    DynamicListComposeTheme {
        DynamicListHeaderComponentView(
            title = "Hello from the header view of DynamicList",
            contextType = ContextType.HOME,
            rememberLazyListState()
        )
    }
}