package com.example.urlscanner.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = darkColorScheme(
    primary          = CyanPrimary,
    onPrimary        = DeepNavy,
    primaryContainer = CardDark,
    onPrimaryContainer = CyanPrimary,
    secondary        = TealLight,
    onSecondary      = DeepNavy,
    background       = DeepNavy,
    onBackground     = TextPrimary,
    surface          = CardDark,
    onSurface        = TextPrimary,
    surfaceVariant   = SurfaceDark,
    onSurfaceVariant = TextSecondary,
    outline          = BorderColor,
    error            = DangerRed,
    onError          = TextPrimary
)

@Composable
fun URLScannerTheme(content: @Composable () -> Unit) {
    // Force dark theme — fits a cybersecurity tool perfectly
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography  = Typography,
        content     = content
    )
}
