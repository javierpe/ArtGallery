package com.nucu.dynamiclistcompose.components.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FiltersComponentView(
    onSelectItem: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            FilterItemComponent("Banner") {
                onSelectItem.invoke(7)
            }
        }
        item {
            FilterItemComponent("Text") {
                onSelectItem.invoke(3)
            }
        }
        item {
            FilterItemComponent("Header with elements") {
                onSelectItem.invoke(8)
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