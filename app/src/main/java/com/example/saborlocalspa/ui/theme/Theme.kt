package com.example.saborlocalspa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Esquema de colores Light - SaborLocal (MORA DO)
 *
 * Paleta inspirada en productos premium, locales y auténticos:
 * - Morado bosque: Premium, exclusivo, conexión profunda
 * - Naranja terracota: Calidez y comunidad local
 * - Crema natural: Autenticidad y simplicidad
 * - Marrones tierra: Conexión con productores locales
 */
private val LightColorScheme = lightColorScheme(
    // ========= Primary (Morado Bosque) =========
    primary = Primary,                      // ForestPurple #6A1B9A ✅
    onPrimary = Color.White,
    primaryContainer = LightPurple,         // Morado claro #E1BEE7 ✅
    onPrimaryContainer = ForestPurple,      // ForestPurple #6A1B9A ✅

    // ========= Secondary (Azul Suave) =========
    secondary = Accent,                     // Azul suave #4A90E2
    onSecondary = Color.White,
    secondaryContainer = LightBlue,
    onSecondaryContainer = Color(0xFF1565C0),

    // ========= Tertiary (Morado Menta) =========
    tertiary = MintPurple,                  // MintPurple #BA68C8 ✅
    onTertiary = Color.White,
    tertiaryContainer = PalePurple,         // PalePurple #F3E5F5 ✅
    onTertiaryContainer = ForestPurple,     // ForestPurple ✅

    // ========= Error (SIN CAMBIO) =========
    error = Destructive,
    onError = Color.White,
    errorContainer = DestructiveLight,
    onErrorContainer = Color(0xFF8B0000),

    // ========= Background (SIN CAMBIO) =========
    background = Color(0xFFF8F9FA),
    onBackground = Foreground,

    // ========= Surface (SIN CAMBIO) =========
    surface = Surface,
    onSurface = Foreground,
    surfaceVariant = Color(0xFFFFFFFF),
    onSurfaceVariant = ForegroundMuted,

    // ========= Outline (SIN CAMBIO) =========
    outline = Border,
    outlineVariant = Beige,

    // ========= Inverse (MORA DO) =========
    inverseSurface = ForestPurple,          // ForestPurple #6A1B9A ✅
    inverseOnSurface = Color.White,
    inversePrimary = MintPurple,            // MintPurple #BA68C8 ✅

    // ========= Otros =========
    surfaceTint = MintPurple,               // MintPurple ✅
    scrim = Color.Black.copy(alpha = 0.32f)
)

/**
 * Esquema de colores Dark - SaborLocal (MORA DO)
 */
private val DarkColorScheme = darkColorScheme(
    // ========= Primary =========
    primary = MintPurple,                   // MintPurple más brillante ✅
    onPrimary = CharcoalGray,
    primaryContainer = ForestPurple,        // ForestPurple oscuro ✅
    onPrimaryContainer = LightPurple,       // LightPurple ✅

    // ========= Secondary =========
    secondary = SkyBlue,
    onSecondary = CharcoalGray,
    secondaryContainer = Color(0xFF1565C0),
    onSecondaryContainer = LightBlue,

    // ========= Tertiary =========
    tertiary = MintPurple,                  // MintPurple ✅
    onTertiary = CharcoalGray,
    tertiaryContainer = LeafPurple,         // LeafPurple ✅
    onTertiaryContainer = PalePurple,       // PalePurple ✅

    // ========= Error (SIN CAMBIO) =========
    error = Destructive,
    onError = Color.White,
    errorContainer = Color(0xFF8B0000),
    onErrorContainer = DestructiveLight,

    // ========= Background (SIN CAMBIO) =========
    background = Color(0xFF1A1A1A),
    onBackground = CreamBackground,

    // ========= Surface (SIN CAMBIO) =========
    surface = Color(0xFF242424),
    onSurface = CreamBackground,
    surfaceVariant = Color(0xFF3A3A3A),
    onSurfaceVariant = Beige,

    // ========= Outline (SIN CAMBIO) =========
    outline = MediumBrown,
    outlineVariant = EarthBrown,

    // ========= Inverse (MORA DO) =========
    inverseSurface = CreamSurface,
    inverseOnSurface = CharcoalGray,
    inversePrimary = ForestPurple,          // ForestPurple ✅

    // ========= Otros =========
    surfaceTint = MintPurple,               // MintPurple ✅
    scrim = Color.Black.copy(alpha = 0.32f)
)

// Shapes (SIN CAMBIO - Perfecto)
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

/**
 * Tema principal de SaborLocal (MORA DO)
 */
@Composable
fun MiAppModularTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
