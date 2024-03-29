package com.javi.dynamic.list.presentation.components.filters

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.design.system.theme.Typography
import com.javi.render.processor.core.RenderType
import kotlinx.coroutines.launch

@Composable
fun FiltersComponentViewScreen(
    modifier: Modifier,
    data: List<FilterItemModel>,
    windowWidthSizeClass: WindowWidthSizeClass? = null,
    onSelectItem: (RenderType) -> Unit
) {
    AnimatedVisibility(visible = windowWidthSizeClass == WindowWidthSizeClass.Compact) {
        FilterRowComponentView(
            modifier = modifier,
            data = data,
            onSelectItem = onSelectItem
        )
    }

    AnimatedVisibility(
        visible = windowWidthSizeClass == WindowWidthSizeClass.Medium ||
            windowWidthSizeClass == WindowWidthSizeClass.Expanded
    ) {
        FilterListComponentView(
            modifier = modifier,
            data = data,
            onSelectItem = onSelectItem
        )
    }
}

@Composable
fun FilterListComponentView(
    modifier: Modifier,
    data: List<FilterItemModel>,
    onSelectItem: (RenderType) -> Unit
) {
    var state by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .wrapContentWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        data.forEachIndexed { index, item ->
            FilterItemComponent(
                modifier = Modifier.width(120.dp),
                text = item.text,
                isSelected = state == index,
                cornerRadius = 12.dp,
                textAlign = TextAlign.Start,
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

@Composable
fun FilterRowComponentView(
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
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        state = listState,
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(
            items = data,
        ) { index, item ->
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
    textAlign: TextAlign = TextAlign.Center,
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
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(colorAnimation.copy(alpha = 0.5f))
            .clickable { onClick.invoke() },
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            color = colorTextAnimation,
            textAlign = textAlign,
            style = Typography.subtitle2
        )
    }
}

@Composable
@Preview(
    name = "FiltersComponentViewScreen | Night Mode On",
    showBackground = true,
    backgroundColor = 0xFF383838,
    uiMode = UI_MODE_NIGHT_YES
)
fun PreviewNightModeFiltersComponentViewScreen() {
    DynamicListComposeTheme {
        FiltersComponentViewScreen(
            modifier = Modifier,
            data = listOf(
                FilterItemModel(text = "Text 1", goTo = "", color = "#FECD50"),
                FilterItemModel(text = "Text 2", goTo = "", color = "#A0D4CD"),
                FilterItemModel(text = "Text 3", goTo = "", color = "#43AAA0"),
                FilterItemModel(text = "Text 4", goTo = "", color = "#619197"),
                FilterItemModel(text = "Text 5", goTo = "", color = "#FFDAB9")
            ),
            windowWidthSizeClass = WindowWidthSizeClass.Compact
        ) { }
    }
}

@Composable
@Preview(
    name = "FiltersComponentViewScreen | Night Mode Off",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
fun PreviewNoNightModeFiltersComponentViewScreen() {
    DynamicListComposeTheme {
        FiltersComponentViewScreen(
            modifier = Modifier,
            data = listOf(
                FilterItemModel(text = "Text 1", goTo = "", color = "#FECD50"),
                FilterItemModel(text = "Text 2", goTo = "", color = "#A0D4CD"),
                FilterItemModel(text = "Text 3", goTo = "", color = "#43AAA0"),
                FilterItemModel(text = "Text 4", goTo = "", color = "#619197"),
                FilterItemModel(text = "Text 5", goTo = "", color = "#FFDAB9")
            ),
            windowWidthSizeClass = WindowWidthSizeClass.Compact
        ) { }
    }
}
