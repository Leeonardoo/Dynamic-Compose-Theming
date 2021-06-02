package com.github.leeonardoo.dynamictheming.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

private val LightColorPalette = DesignSystemColors(
    primary = Purple500,
    primaryVariant = Purple700
)

@Composable
fun DesignSystemTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    ProvideDesignSystemColors(LightColorPalette) {
        MaterialTheme(
            colors = Colors(
                primary = DesignSystemTheme.colors.primary,
                primaryVariant = DesignSystemTheme.colors.primary,
                secondary = MaterialTheme.colors.secondary,
                secondaryVariant = MaterialTheme.colors.secondaryVariant,
                background = MaterialTheme.colors.background,
                surface = MaterialTheme.colors.surface,
                error = MaterialTheme.colors.error,
                onPrimary = MaterialTheme.colors.onPrimary,
                onSecondary = MaterialTheme.colors.onSecondary,
                onBackground = MaterialTheme.colors.onBackground,
                onSurface = MaterialTheme.colors.onSurface,
                onError = MaterialTheme.colors.onError,
                isLight = !darkTheme
            ),
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

object DesignSystemTheme {

    val colors: DesignSystemColors
        @Composable
        get() = AmbientDesignSystemColors.current
}

class DesignSystemColors(
    primary: Color,
    primaryVariant: Color
) {
    var primary: Color by mutableStateOf(primary)
        private set
    var primaryVariant: Color by mutableStateOf(primaryVariant)
        private set

    fun update(other: DesignSystemColors) {
        primary = other.primary
        primaryVariant = other.primaryVariant
    }
}

@Composable
fun ProvideDesignSystemColors(
    colors: DesignSystemColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(AmbientDesignSystemColors provides colorPalette, content = content)
}

private val AmbientDesignSystemColors = staticCompositionLocalOf<DesignSystemColors> {
    error("No ColorPalette provided")
}