package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = CelestialGold,
    onPrimary = DeepGoldOnPrimary,
    primaryContainer = GoldContainerDark,
    onPrimaryContainer = SoftGoldText,
    secondary = MoonLavender,
    onSecondary = DeepLavenderOnSecondary,
    secondaryContainer = LavenderContainerDark,
    onSecondaryContainer = StarlightWhite,
    tertiary = CrystalTeal,
    onTertiary = DeepTealOnTertiary,
    tertiaryContainer = TealContainerDark,
    onTertiaryContainer = StarlightWhite,
    background = MidnightVoid,
    onBackground = StarlightWhite,
    surface = DeepIndigoSurface,
    onSurface = StarlightWhite,
    surfaceVariant = ElevatedPlumContainer,
    onSurfaceVariant = MutedNebulaText,
    surfaceContainerLow = DeepIndigoSurface,
    surfaceContainer = ElevatedPlumContainer,
    surfaceContainerHigh = HighestPlumContainer,
    outline = OutlineMysticDark,
    outlineVariant = OutlineMysticDark.copy(alpha = 0.55f)
)

private val LightColorScheme = lightColorScheme(
    primary = RoyalAmethyst,
    onPrimary = Color.White,
    primaryContainer = AmethystContainerLight,
    onPrimaryContainer = OnAmethystContainerLight,
    secondary = BurnishedGold,
    onSecondary = Color.White,
    secondaryContainer = GoldContainerLight,
    onSecondaryContainer = OnGoldContainerLight,
    tertiary = SageTeal,
    onTertiary = Color.White,
    tertiaryContainer = TealContainerLight,
    onTertiaryContainer = OnTealContainerLight,
    background = ParchmentCream,
    onBackground = ObsidianInk,
    surface = IvorySurface,
    onSurface = ObsidianInk,
    surfaceVariant = WarmSandContainer,
    onSurfaceVariant = WarmSlateInk,
    surfaceContainerLow = IvorySurface,
    surfaceContainer = WarmSandContainer,
    surfaceContainerHigh = HighestSandContainer,
    outline = OutlineMysticLight,
    outlineVariant = OutlineMysticLight.copy(alpha = 0.6f)
)

private val KozmikShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp)
)

@Immutable
data class KozmikAccentPalette(
    val goldAccent: Color,
    val lavenderAccent: Color,
    val tealAccent: Color,
    val roseAccent: Color,
    val imageScrimStart: Color,
    val imageScrimEnd: Color
)

val LocalKozmikAccents = staticCompositionLocalOf {
    KozmikAccentPalette(
        goldAccent = CelestialGold,
        lavenderAccent = MoonLavender,
        tealAccent = CrystalTeal,
        roseAccent = RoseQuartz,
        imageScrimStart = MidnightVoid.copy(alpha = 0.25f),
        imageScrimEnd = MidnightVoid.copy(alpha = 0.92f)
    )
}

@Composable
fun KozmikSayiTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val accents = if (darkTheme) {
        KozmikAccentPalette(
            goldAccent = CelestialGold,
            lavenderAccent = MoonLavender,
            tealAccent = CrystalTeal,
            roseAccent = RoseQuartz,
            imageScrimStart = MidnightVoid.copy(alpha = 0.2f),
            imageScrimEnd = MidnightVoid.copy(alpha = 0.92f)
        )
    } else {
        KozmikAccentPalette(
            goldAccent = BurnishedGold,
            lavenderAccent = RoyalAmethyst,
            tealAccent = SageTeal,
            roseAccent = Color(0xFF9C4968),
            imageScrimStart = Color(0xFF171029).copy(alpha = 0.25f),
            imageScrimEnd = Color(0xFF171029).copy(alpha = 0.88f)
        )
    }

    CompositionLocalProvider(LocalKozmikAccents provides accents) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = KozmikTypography,
            shapes = KozmikShapes,
            content = content
        )
    }
}
