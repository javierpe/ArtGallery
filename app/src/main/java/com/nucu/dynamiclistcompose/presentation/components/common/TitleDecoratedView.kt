@file:OptIn(ExperimentalUnitApi::class)

package com.nucu.dynamiclistcompose.presentation.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.nucu.dynamiclistcompose.presentation.ui.theme.Typography

@Composable
fun TitleDecoratedView(
    modifier: Modifier = Modifier,
    text: String,
    withDecoration: Boolean = true
) {
    val decoration = remember {
        derivedStateOf {
            TextDecoration.Underline
        }
    }

    val letterSpacing = remember {
        derivedStateOf {
            TextUnit(35f, TextUnitType.Sp)
        }
    }

    val titleUpperCase = remember {
        derivedStateOf { text.uppercase() }
    }

    Text(
        text = titleUpperCase.value,
        modifier = modifier
            .fillMaxWidth(),
        style = Typography.h6,
        color = MaterialTheme.colors.secondary,
        letterSpacing = letterSpacing.value,
        textAlign = TextAlign.Center,
        textDecoration = if (withDecoration) decoration.value else null
    )
}