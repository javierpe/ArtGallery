package com.nucu.dynamiclistcompose.ui.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.ui.theme.DynamicListComposeTheme

@Composable
fun BackButtonComponentView(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .shadow(10.dp)
            .background(MaterialTheme.colors.primary)
            .size(30.dp)
            .clickable {
                onClick?.invoke()
            }
            .padding(6.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = MaterialTheme.colors.primaryVariant,
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun PreviewBackButtonComponentView() {
    DynamicListComposeTheme(darkTheme = true) {
        BackButtonComponentView()
    }
}