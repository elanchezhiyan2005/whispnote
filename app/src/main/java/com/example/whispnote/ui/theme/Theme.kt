package com.example.whispnote.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WhispNoteColorScheme = lightColorScheme(
    primary = Color(0xFF47EA98), // accent_green
    onPrimary = Color(0xFF111814), // text_primary
    secondary = Color(0xFF47EA98),
    onSecondary = Color(0xFF111814),
    background = Color(0xFFF0F4F2), // background_light
    onBackground = Color(0xFF638875), // text_secondary
    surface = Color(0xFFF0F4F2),
    onSurface = Color(0xFF111814)
)

@Composable
fun WhispNoteTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WhispNoteColorScheme,
        content = content
    )
}