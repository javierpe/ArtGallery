package com.nucu.dynamiclistcompose.components.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.renders.base.RenderType

@Composable
fun FiltersComponentView(
    data: List<FilterItemModel>,
    onSelectItem: (RenderType) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(items = data) {
            FilterItemComponent(it.text) {
                RenderType
                    .values()
                    .firstOrNull { render -> render.value == it.goTo }
                    ?.let(onSelectItem)
            }
        }
    }
}

@Composable
fun FilterItemComponent(text: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = 8.dp
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clickable { onClick.invoke() }
                .padding(5.dp),
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
@Preview
fun PreviewFiltersComponentView() {
    FiltersComponentView(emptyList()) { }
}