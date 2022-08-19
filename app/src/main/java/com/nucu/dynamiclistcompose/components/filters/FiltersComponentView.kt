package com.nucu.dynamiclistcompose.components.filters

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@Composable
fun FiltersComponentView(
    data: List<FilterItemModel>,
    onSelectItem: (RenderType) -> Unit
) {
    var state by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (state >= 0) {
        SideEffect {
            coroutineScope.launch {
                listState.animateScrollToItem(state)
            }
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.1f),
                        Color.Transparent,
                    )
                )
            )
            .height(75.dp)
            .clipToBounds(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        state = listState
    ) {
        itemsIndexed(
            items = data,
        ) {  index, item ->
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

@Composable
fun FilterItemComponent(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {

    val elevationAnimation: Dp by animateDpAsState(
        if (isSelected) 20.dp else 0.dp,
        spring(stiffness = Spring.StiffnessLow)
    )

    val colorAnimation: Color by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
        spring(stiffness = Spring.StiffnessLow)
    )

    val textColorAnimation: Color by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
        spring(stiffness = Spring.StiffnessLow)
    )

    Card(
        shape = RoundedCornerShape(7.dp),
        elevation = elevationAnimation,
        modifier = Modifier
            .defaultMinSize(minWidth = 100.dp),
        backgroundColor = colorAnimation
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clickable { onClick.invoke() }
                .padding(5.dp)
                .fillMaxWidth(),
            color = textColorAnimation,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun PreviewFiltersComponentView() {
    FiltersComponentView(emptyList()) { }
}