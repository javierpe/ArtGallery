package com.javi.design.system.molecules

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.design.system.data.models.NavigationBarItem
import com.javi.design.system.theme.DynamicListComposeTheme

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    selected: String,
    navItems: List<NavigationBarItem>
) {
    val selectedItem = remember {
        mutableStateOf(
            selected
        )
    }

    BottomNavigation(
        modifier = modifier,
        elevation = 10.dp,
        backgroundColor = if (isSystemInDarkTheme()) MaterialTheme.colors.onSecondary else Color.White
    ) {
        navItems.forEach {
            BottomNavigationItem(
                selected = (selected == it.key),
                onClick = {
                    selectedItem.value = it.key
                    it.onClick?.invoke()
                },
                icon = { Icon(imageVector = it.icon ?: Icons.Default.Star, contentDescription = it.name) },
                label = { Text(text = it.name) },
                enabled = true,
                selectedContentColor = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.7f) else Color.Black,
                unselectedContentColor = if (isSystemInDarkTheme()) {
                    MaterialTheme.colors.onSecondary.copy(alpha = 0.4f)
                } else {
                    Color.LightGray
                }
            )
        }
    }
}

@Composable
@Preview
fun PreviewNavigationBar() {
    DynamicListComposeTheme {
        NavigationBar(
            selected = "",
            navItems = listOf(
                NavigationBarItem(name = "Home", icon = Icons.Rounded.Home),
                NavigationBarItem(name = "Actual", icon = Icons.Rounded.Star),
                NavigationBarItem(name = "Settings", icon = Icons.Rounded.Notifications),
            )
        )
    }
}
