package com.javi.design.system.molecules

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
    navItems: List<NavigationBarItem>
) {
    val selectedItem = remember {
        mutableStateOf(navItems.first().name)
    }

    BottomNavigation(
        modifier = modifier,
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        navItems.forEach {
            BottomNavigationItem(
                selected = (selectedItem.value == it.name),
                onClick = {
                    selectedItem.value = it.name
                    it.onClick?.invoke()
                },
                icon = { Icon(imageVector = it.icon, contentDescription = it.name) },
                label = { Text(text = it.name) },
                enabled = true,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.LightGray
            )
        }
    }
}

@Composable
@Preview
fun PreviewNavigationBar() {
    DynamicListComposeTheme {
        NavigationBar(
            navItems = listOf(
                NavigationBarItem(name = "Home", icon = Icons.Rounded.Home),
                NavigationBarItem(name = "Actual", icon = Icons.Rounded.Star),
                NavigationBarItem(name = "Settings", icon = Icons.Rounded.Notifications),
            )
        )
    }
}