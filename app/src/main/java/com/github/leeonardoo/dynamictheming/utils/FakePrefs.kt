package com.github.leeonardoo.dynamictheming.utils

import androidx.compose.ui.graphics.Color
import com.github.leeonardoo.dynamictheming.ui.theme.DesignSystemColors
import com.github.leeonardoo.dynamictheming.ui.theme.Purple500

object FakePrefs {

    private val defaultColor = Purple500
    private val fakeCustomColor = Color(0xFF00B875)

    private var current = defaultColor

    fun update(): DesignSystemColors {
        current = if (current == defaultColor)
            fakeCustomColor
        else
            defaultColor

        return DesignSystemColors(current, current)
    }
}