package com.nucu.dynamiclistcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nucu.dynamiclistcompose.ui.theme.DynamicListComposeTheme
import com.nucu.dynamiclistcompose.ui.theme.Typography

/**
 * Define header design by context
 */
@Composable
fun DynamicListHeaderComponentView(
    title: String,
    onBackPressed: () -> Unit, // Remove this and set navigation compose here
    content: @Composable () -> Unit
) {
    Column {
        ConstraintLayout(
            modifier = Modifier.padding(16.dp).wrapContentHeight(),
        ) {
            val (backRef, titleRef) = createRefs()

            BackButtonComponentView(
                modifier = Modifier.constrainAs(backRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
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

        content()
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHeaderComponentView() {
    DynamicListComposeTheme {
        DynamicListHeaderComponentView("Hello from the header view of DynamicList", { }) {
            Text(text = "This is a container")
        }
    }
}