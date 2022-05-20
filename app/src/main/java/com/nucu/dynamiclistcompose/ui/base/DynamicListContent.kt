package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.models.DynamicListElement

@Composable
fun DynamicListScreen(
    content: List<DynamicListElement>,
    listState: LazyListState,
    widthSizeClass: WindowWidthSizeClass,
    onAction: ((ScrollAction) -> Unit)? = null
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.wrapContentHeight(),
        state = listState
    ) {
        items(items = content) {
            it.factory?.CreateComponent(
                component = it.componentItemModel,
                listener = it.listener,
                componentAction = onAction,
                widthSizeClass
            )
        }
    }
}