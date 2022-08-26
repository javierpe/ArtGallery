package com.nucu.dynamiclistcompose.presentation.components.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.ui.components.headers.DURATION
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

    val step by animateIntAsState(targetValue = if (isMediumScreen) 2 else 4, tween(DURATION))
    val size by animateDpAsState(targetValue = if (isMediumScreen) 110.dp else 90.dp, tween(DURATION))

    val chunkedData by remember {
        derivedStateOf {
            data.chunked(
                step
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        chunkedData.forEachIndexed { columnIndex, list ->
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                list.forEachIndexed { rowIndex, item ->

                    val isSelected = state == mapOf(columnIndex to rowIndex)

                    val colorAnimation: Color by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colors.secondary
                        else MaterialTheme.colors.primary,
                        spring(stiffness = Spring.StiffnessLow)
                    )

                    FilterItemComponent(
                        modifier = Modifier
                            .size(size)
                            .border(
                                width = 1.dp,
                                color = colorAnimation,
                                shape = RoundedCornerShape(7.dp)
                            ),
                        text = item.text,
                        isSelected = isSelected
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
            .fillMaxWidth()
            .height(75.dp)
            .clipToBounds(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(
            items = data,
        ) {  index, item ->
            FilterItemComponent(
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp)
                    .height(50.dp),
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
    modifier: Modifier = Modifier,
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
        modifier = modifier
            .clickable { onClick.invoke() },
        backgroundColor = colorAnimation
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(5.dp)
                    .fillMaxWidth(),
                color = textColorAnimation,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun PreviewFiltersComponentViewScreen() {
    FiltersComponentViewScreen(
        modifier = Modifier,
        data = emptyList(),
        windowWidthSizeClass = WindowWidthSizeClass.Compact
    ) { }
}