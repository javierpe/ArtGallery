package com.nucu.dynamiclistcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.models.ContextType
import com.nucu.dynamiclistcompose.models.tooltip.ShowCaseStrategy
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.ui.components.showCase.TooltipView
import com.nucu.dynamiclistcompose.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShapeType
import com.nucu.dynamiclistcompose.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.ui.theme.DynamicListComposeTheme
import com.nucu.dynamiclistcompose.ui.theme.Typography

/**
 * Define header design by context
 */
@Composable
fun DynamicListHeaderComponentView(
    title: String,
    contextType: ContextType,
    onBackPressed: () -> Unit, // Remove this and set navigation compose here
) {
    when (contextType) {

        ContextType.HOME -> {
            SimpleHeaderView(
                title = title,
                onBackPressed = onBackPressed
            )
        }

        ContextType.SCREEN_WITH_IMAGE -> {
            HeaderWithImageView(title = title)
        }
    }
}

@Composable
fun HeaderWithImageView(
    title: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            val (imageRef, titleRef) = createRefs()

            Image(
                modifier = Modifier
                    .height(130.dp)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(id = R.drawable.wallpaper),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier
                    .constrainAs(titleRef) {
                        bottom.linkTo(parent.bottom, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)

                        width = Dimension.fillToConstraints
                    },
                text = title,
                style = Typography.h5,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
fun SimpleHeaderView(
    title: String,
    onBackPressed: () -> Unit, // Remove this and set navigation compose here
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
                onBackPressed
            )

            Text(
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(backRef.top)
                    bottom.linkTo(backRef.bottom)
                    start.linkTo(backRef.end, 10.dp)
                },
                text = title,
                style = Typography.h6
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
            contextType = ContextType.SCREEN_WITH_IMAGE
        ) { }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSimpleHeaderComponentView() {
    DynamicListComposeTheme {
        DynamicListHeaderComponentView(
            title = "Hello from the header view of DynamicList",
            contextType = ContextType.HOME
        ) { }
    }
}