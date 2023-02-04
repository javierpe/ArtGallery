package com.nucu.dynamiclistcompose.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.nucu.dynamiclistcompose.R


private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    primaryVariant = TertiaryDark,
    secondary = SecondaryDark,
    surface = BackgroundDark,
    onSurface = BackgroundDark,
    onBackground = BackgroundDark,
    background = BackgroundDark,
    onPrimary = SkeletonDark,
    onSecondary = backgroundDynamicListContainerColorDark
)

private val LightColorPalette = lightColors(
    primary = PrimaryLight,
    primaryVariant = TertiaryLight,
    secondary = SecondaryLight,
    background = BackgroundLight,
    surface = BackgroundLight,
    onSecondary = backgroundDynamicListContainerColorLight
)

private fun provideDarkColorPalette(adaptivelyColor: Color): Colors {
    return darkColors(
        primary = PrimaryDark,
        primaryVariant = TertiaryDark,
        secondary = SecondaryDark,
        surface = BackgroundDark,
        onSurface = BackgroundDark,
        onBackground = adaptivelyColor,
        background = adaptivelyColor,
        onPrimary = SkeletonDark,
        onSecondary = backgroundDynamicListContainerColorDark
    )
}

@Composable
fun DynamicListComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        provideDarkColorPalette(colorResource(id = R.color.ic_launcher_background))
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}