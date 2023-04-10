package com.javi.design.system.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T : Any> StaticGridList(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    list: List<T>,
    content: @Composable (Int, Int, T) -> Unit
) {
    val chunkedData = remember {
        derivedStateOf {
            list.chunked(columns)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        chunkedData.value.forEachIndexed { columnIndex, element ->
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                element.forEachIndexed { rowIndex, item ->
                    content(columnIndex, rowIndex, item)
                }
            }
        }
    }
}
