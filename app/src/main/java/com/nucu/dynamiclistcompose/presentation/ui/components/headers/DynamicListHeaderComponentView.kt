package com.nucu.dynamiclistcompose.presentation.ui.components.headers

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.data.models.ContextType
import com.nucu.dynamiclistcompose.data.models.showCase.ShapeType
import com.nucu.dynamiclistcompose.data.models.showCase.ShowCaseStrategy
import com.nucu.dynamiclistcompose.presentation.ui.components.BackButtonComponentView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.TooltipView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.theme.DynamicListComposeTheme
import com.nucu.dynamiclistcompose.presentation.ui.theme.Typography
import com.nucu.dynamiclistcompose.presentation.viewModels.HeaderViewModel

/**
 * Define header design by context
 */
@Composable
fun DynamicListHeaderComponentView(
    title: String,
    contextType: ContextType,
    bodyLazyListState: LazyListState? = null,
    viewModel: HeaderViewModel = hiltViewModel()
) {
    val icon = if (viewModel.isHome()) Icons.Default.Star else Icons.Default.ArrowBack

    when (contextType) {

        ContextType.BANNER_DETAIL -> {
            SimpleHeaderView(
                title = title,
                icon = icon,
                onIconClick = {
                    if (!viewModel.isHome()) {
                        viewModel.handleIconClick()
                    }
                }
            )
        }

        ContextType.HOME, ContextType.CARDS -> {
            HeaderWithImageView(
                title = title,
                bodyLazyListState = bodyLazyListState,
                icon = icon,
                onIconClick = {
                    if (!viewModel.isHome()) {
                        viewModel.handleIconClick()
                    }
                }
            )
        }
    }
}

@Composable
fun SimpleHeaderView(
    title: String,
    icon: ImageVector,
    onIconClick: () -> Unit
) {
    val showCaseState = rememberShowCaseState()

    Column {
        ConstraintLayout(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
        ) {
            val (backRef, titleRef) = createRefs()

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
                icon = icon
            )

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