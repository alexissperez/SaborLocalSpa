package com.example.saborlocalspa.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta de colores SaborLocal - Inspirada en productos frescos y locales
 *
 * **Filosofía del diseño:**
 * - Morado: Premium, auténtico, conexión local profunda
 * - Naranja/Terracota: Calidez, comunidad local, energía
 * - Crema/Beige: Natural, artesanal, acogedor
 * - Marrones tierra: Rústico, auténtico, de la tierra
 */

// ========= Morados (Premium y Auténtico) =========

/** Morado bosque profundo - Color principal de la marca */
val ForestPurple = Color(0xFF6A1B9A)

/** Morado hoja - Para hover states y acentos */
val LeafPurple = Color(0xFF7B1FA2)

/** Morado menta - Para containers y fondos suaves */
val MintPurple = Color(0xFFBA68C8)

/** Morado claro - Para fondos y superficies */
val LightPurple = Color(0xFFE1BEE7)

/** Morado muy claro - Para fondos secundarios */
val PalePurple = Color(0xFFF3E5F5)

// ========= Resto SIN CAMBIOS (mantén todo igual) =========
val SoftBlue = Color(0xFF4A90E2)
val SkyBlue = Color(0xFF5BA3F5)
val LightBlue = Color(0xFFE3F2FD)

val CreamBackground = Color(0xFFFFFBF5)
val CreamSurface = Color(0xFFFFF8F0)
val Beige = Color(0xFFF5EFE6)

val EarthBrown = Color(0xFF6B4423)
val MediumBrown = Color(0xFF8B6F47)
val LightBrown = Color(0xFFD4C4B0)

val CharcoalGray = Color(0xFF2B2D31)
val MediumGray = Color(0xFF6C757D)
val LightGray = Color(0xFFE9ECEF)
val PaleGray = Color(0xFFF8F9FA)

// Estados semánticos (SIN CAMBIOS)
val Success = MintPurple        // ← Ya usa MintPurple
val SuccessLight = LightPurple
val Destructive = Color(0xFFDC3545)
val DestructiveLight = Color(0xFFFFF0F0)
val Warning = Color(0xFFFFA726)
val WarningLight = Color(0xFFFFF3E0)
val Info = Color(0xFF4A90E2)
val InfoLight = Color(0xFFE3F2FD)

// ========= Colores Principales del Tema (CAMBIADOS) =========
val Primary = ForestPurple      // ← Ex ForestGreen
val PrimaryHover = LeafPurple   // ← Ex LeafGreen
val PrimaryLight = MintPurple   // ← Ex MintGreen
val Accent = SoftBlue
val AccentHover = SkyBlue

// Backgrounds, Borders, Textos (SIN CAMBIOS)
val Background = Color(0xFFF8F9FA)
val BackgroundSecondary = Color(0xFFFFFFFF)
val Surface = Color(0xFFFFFFFF)
val SurfaceVariant = Color(0xFFFFFFFF)
val Border = LightBrown
val BorderFocus = LeafPurple    // ← Ex LeafGreen
val Foreground = CharcoalGray
val ForegroundMuted = MediumGray
val ForegroundSubtle = LightGray
val Muted = Beige
val MutedForeground = MediumGray
val Ring = ForestPurple         // ← Ex ForestGreen
val RingOffset = Background

// SaborLocal específicos (AJUSTADOS)
val OrganicGreen = MintPurple   // ← Morado orgánico
val ArtisanalOrange = Color(0xFFFFA726)
val FarmBrown = EarthBrown
val AvailableGreen = Success    // ← MintPurple
val OutOfStockRed = Destructive
val LowStockYellow = Warning
