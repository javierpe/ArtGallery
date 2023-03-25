package com.javi.dynamic.list.presentation.components.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.javi.design.system.molecules.StaticGridList
import com.javi.design.system.molecules.headers.DURATION
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.design.system.theme.Typography
import com.javi.render.data.RenderType
import kotlinx.coroutines.launch

@Composable
fun FiltersComponentViewScreen(
    modifier: Modifier,
    data: List<FilterItemModel>,
    windowWidthSizeClass: WindowWidthSizeClass,
    onSelectItem: (RenderType) -> Unit
) {

    AnimatedVisibility(visible = windowWidthSizeClass == WindowWidthSizeClass.Compact) {
        FilterListComponentView(
            modifier = modifier,
            data = data,
            onSelectItem = onSelectItem
        )
    }

    AnimatedVisibility(
        visible = windowWidthSizeClass == WindowWidthSizeClass.Medium
                || windowWidthSizeClass == WindowWidthSizeClass.Expanded
    ) {
        FilterGridComponentView(
            modifier = modifier,
            data = data,
            isMediumScreen = windowWidthSizeClass == WindowWidthSizeClass.Medium,
            onSelectItem = onSelectItem
        )
    }
}

@Composable
fun FilterGridComponentView(
    modifier: Modifier,
    data: List<FilterItemModel>,
    isMediumScreen: Boolean = false,
    onSelectItem: (RenderType) -> Unit
) {

    var state by remember {
        mutableStateOf(mapOf(0 to 0))
    }

    val size by animateDpAsState(targetValue = if (isMediumScreen) 110.dp else 90.dp, tween(DURATION))

    StaticGridList(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        list = data
    ) { columnIndex, rowIndex, item ->

        val isSelected = state == mapOf(columnIndex to rowIndex)

        FilterItemComponent(
            modifier = Modifier.size(size),
            text = item.text,
            isSelected = isSelected,
            cornerRadius = 20.dp,
            color = Color(android.graphics.Color.parseColor(item.color))
        ) {
            RenderType
                .values()
                .firstOrNull { render -> render.value == item.goTo }
                ?.let {
                    state = mapOf(columnIndex to rowIndex)
                    onSelectItem.invoke(it)
                }
        }
    }
}

@Composable
fun FilterListComponentView(
    modifier: Modifier,
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
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        state = listState,
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(
            items = data,
        ) {  index, item ->
            FilterItemComponent(
                modifier = Modifier.wrapContentSize(),
                text = item.text,
                isSelected = state == index,
                color = Color(android.graphics.Color.parseColor(item.color))
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


@Suppress("LongParameterList")
@Composable
fun FilterItemComponent(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    cornerRadius: Dp = 15.dp,
    color: Color,
    onClick: () -> Unit
) {

    val elevationAnimation: Dp by animateDpAsState(
        if (isSelected) 50.dp else 0.dp,
        spring(stiffness = Spring.StiffnessLow)
    )

    val colorAnimation: Color by animateColorAsState(
        targetValue = if (isSelected) color else MaterialTheme.colors.onPrimary,
        spring(stiffness = Spring.StiffnessLow)
    )

    val colorTextAnimation: Color by animateColorAsState(
        targetValue = if (isSelected) color else MaterialTheme.colors.secondary.copy(alpha = 0.5f),
        spring(stiffness = Spring.StiffnessLow)
    )

    Box(
        modifier = modifier
            .shadow(
                clip = false,
                ambientColor = colorAnimation,
                spotColor = colorAnimation,
                elevation = elevationAnimation,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(colorAnimation.copy(alpha = 0.5f))
            .clickable { onClick.invoke() },
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp),
            color = colorTextAnimation,
            textAlign = TextAlign.Center,
            style = Typography.subtitle2
        )
    }
}

@Composable
@Preview
fun PreviewFiltersComponentViewScreen() {
    DynamicListComposeTheme {
        FiltersComponentViewScreen(
            modifier = Modifier,
            data = emptyList(),
            windowWidthSizeClass = WindowWidthSizeClass.Compact
        ) { }
    }
}