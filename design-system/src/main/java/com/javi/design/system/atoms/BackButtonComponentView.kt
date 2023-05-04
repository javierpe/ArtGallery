package com.javi.design.system.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.design.system.theme.DynamicListComposeTheme

@Composable
fun BackButtonComponentView(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    iconColor: Color = Color.White,
    icon: ImageVector = Icons.Default.ArrowBack,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .shadow(10.dp)
            .background(backgroundColor)
            .size(30.dp)
            .clickable {
                onClick?.invoke()
            }
            .padding(6.dp)
    ) {
        Icon(
            imageVector = icon,
            tint = iconColor,
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun PreviewBackButtonDarkComponentView() {
    DynamicListComposeTheme(darkTheme = true) {
        BackButtonComponentView()
    }
}

@Composable
@Preview
fun PreviewBackButtonLightComponentView() {
    DynamicListComposeTheme(darkTheme = false) {
        BackButtonComponentView()
    }
}
