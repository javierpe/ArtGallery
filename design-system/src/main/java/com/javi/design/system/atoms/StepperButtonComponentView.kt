package com.javi.design.system.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.design.system.theme.Typography

@Composable
fun StepperButtonComponentView(
    modifier: Modifier = Modifier,
    quantity: Int = 0,
    onAdd: () -> Unit,
    onDecrement: () -> Unit
) {
    if (quantity == 0) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.Black)
                .clickable { onAdd() }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Add to cart",
                style = Typography.h6,
                color = Color.White
            )
        }
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StepperButtonAction(icon = Icons.Default.Delete) {
                onDecrement()
            }

            Text(
                text = quantity.toString(),
                style = Typography.h6,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp)
            )

            StepperButtonAction(icon = Icons.Default.Add) {
                onAdd()
            }
        }
    }
}

@Composable
fun StepperButtonAction(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Black)
            .padding(10.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "action",
            tint = Color.White
        )
    }
}

@Composable
@Preview
fun PreviewStepperButtonAction() {
    DynamicListComposeTheme {
        StepperButtonAction(icon = Icons.Default.Add) { }
    }
}

@Composable
@Preview
fun PreviewBuyButtonComponentView() {
    DynamicListComposeTheme {
        StepperButtonComponentView(
            quantity = 1,
            onAdd = { },
            onDecrement = { }
        )
    }
}