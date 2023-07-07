package com.javi.design.system.molecules.headers

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.MotionLayoutScope
import com.javi.design.system.atoms.BackButtonComponentView
import com.javi.design.system.data.BACK_BUTTON_BACKGROUND
import com.javi.design.system.data.BACK_BUTTON_ICON_COLOR
import com.javi.design.system.data.TEXT_COLOR
import com.javi.design.system.theme.Typography


const val TITLE_LAYOUT_ID = "title"
const val BACK_BUTTON_LAYOUT_ID = "back_button"

@Composable
fun HomeHeaderComponentView(
    scope: MotionLayoutScope
) {
    val titleProperties = scope.customProperties(TITLE_LAYOUT_ID)
    val backButtonProperties = scope.customProperties(BACK_BUTTON_LAYOUT_ID)

    Text(
        modifier = Modifier.layoutId(TITLE_LAYOUT_ID),
        text = "Art Gallery",
        style = Typography.h5,
        textAlign = TextAlign.Left,
        color = titleProperties.color(TEXT_COLOR)
    )

    BackButtonComponentView(
        modifier = Modifier.layoutId(BACK_BUTTON_LAYOUT_ID),
        onClick = { },
        backgroundColor = backButtonProperties.color(BACK_BUTTON_BACKGROUND),
        iconColor = backButtonProperties.color(BACK_BUTTON_ICON_COLOR),
        icon = Icons.Default.Star
    )
}