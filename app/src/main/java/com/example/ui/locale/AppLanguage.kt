package com.example.ui.locale

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.LayoutDirection

enum class AppLanguage(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val layoutDirection: LayoutDirection,
    val flag: String
) {
    ARABIC(
        code = "ar",
        nativeName = "العربية",
        englishName = "Arabic",
        layoutDirection = LayoutDirection.Rtl,
        flag = "🇸🇦"
    ),
    ENGLISH(
        code = "en",
        nativeName = "English",
        englishName = "English",
        layoutDirection = LayoutDirection.Ltr,
        flag = "🇬🇧"
    );

    val isRtl: Boolean get() = layoutDirection == LayoutDirection.Rtl
}

val LocalAppLanguage = compositionLocalOf { AppLanguage.ARABIC }
val LocalAppStrings = compositionLocalOf { getStrings(AppLanguage.ARABIC) }

fun getStrings(language: AppLanguage): AppStrings {
    return when (language) {
        AppLanguage.ARABIC -> ArabicStrings
        AppLanguage.ENGLISH -> EnglishStrings
    }
}
