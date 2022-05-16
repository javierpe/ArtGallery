package com.nucu.dynamiclistcompose.components.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.renders.base.RenderType

@Composable
fun FiltersComponentView(
    onSelectItem: (RenderType) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            FilterItemComponent("Banner") {
                onSelectItem.invoke(RenderType.BANNER_IMAGE)
            }
        }
        item {
            FilterItemComponent("Header") {
                onSelectItem.invoke(RenderType.HEADER)
            }
        }
        item {
            FilterItemComponent("Cards") {
                onSelectItem.invoke(RenderType.ONE_CLICK_REORDER)
            }
        }
    }
}

@Composable
fun FilterItemComponent(text: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = 10.dp
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clickable { onClick.invoke() }
                .padding(5.dp)
        )
    }
}

@Composable
@Preview
fun PreviewFiltersComponentView() {
    FiltersComponentView { }
}