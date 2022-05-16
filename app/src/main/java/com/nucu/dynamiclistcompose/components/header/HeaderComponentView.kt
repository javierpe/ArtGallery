package com.nucu.dynamiclistcompose.components.header

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.ui.theme.Typography

@Composable
fun HeaderComponentView(text: String) {
    Text(
        text = text,
        style = Typography.h5,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewHeaderComponentView() {
    HeaderComponentView("Hola")
}