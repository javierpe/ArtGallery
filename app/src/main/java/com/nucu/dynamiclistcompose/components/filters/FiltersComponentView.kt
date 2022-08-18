package com.nucu.dynamiclistcompose.components.filters

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.renders.base.RenderType

@Composable
fun FiltersComponentView(
    data: List<FilterItemModel>,
    onSelectItem: (RenderType) -> Unit
) {
    var state by remember { mutableStateOf(0) }

    ScrollableTabRow(
        selectedTabIndex = state,
        indicator = {

        },
        backgroundColor = Color.Transparent,
        divider = { },
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.1f),
                        Color.Transparent,
                    )
                )
            ).clipToBounds()
    ) {
        data.forEachIndexed { index, item ->
            Tab(
                selected = false,
                onClick = { },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .height(55.dp)
            ) {
                FilterItemComponent(
                    text = item.text,
                    isSelected = state == index
                ) {
                    RenderType
                        .values()
                        .firstOrNull { render -> render.value == item.goTo }
                        ?.let {
                            state = index
                            onSelectItem.invoke(it)
                        }
                }
            }
        }
    }
}

@Composable
fun FilterItemComponent(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {

    val elevationAnimation: Dp by animateDpAsState(
        if (isSelected) 10.dp else 0.dp,
        spring(stiffness = Spring.StiffnessLow)
    )

    Card(
        shape = RoundedCornerShape(7.dp),
        elevation = elevationAnimation,
        modifier = Modifier.defaultMinSize(minWidth = 100.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clickable { onClick.invoke() }
                .padding(5.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colors.secondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun PreviewFiltersComponentView() {
    FiltersComponentView(emptyList()) { }
}