package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatTextdirectionLToR
import androidx.compose.material.icons.filled.FormatTextdirectionRToL
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.locale.AppLanguage
import com.example.ui.locale.LocalAppStrings
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenSkyBlue

/**
 * Compact, pill-shaped Language Switcher designed for Top Bars and Screen Headers.
 * Provides instant 1-tap switching between Arabic (RTL) and English (LTR).
 */
@Composable
fun LanguageSwitcherPill(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(19.dp))
            .testTag("language_switcher_pill"),
        shape = RoundedCornerShape(19.dp),
        color = Color(0xFFF1F5F9),
        border = BorderStroke(1.dp, Color(0xFFCBD5E1))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(3.dp)
        ) {
            // Arabic option
            val isArabic = currentLanguage == AppLanguage.ARABIC
            val arBgColor by animateColorAsState(
                targetValue = if (isArabic) ShajeenSkyBlue else Color.Transparent,
                animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
                label = "arBg"
            )
            val arTextColor by animateColorAsState(
                targetValue = if (isArabic) Color.White else Color(0xFF475569),
                animationSpec = tween(durationMillis = 200),
                label = "arText"
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(arBgColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (!isArabic) onLanguageSelected(AppLanguage.ARABIC)
                    }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .testTag("language_pill_arabic"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "🇸🇦",
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "عربي",
                        fontSize = 12.sp,
                        fontWeight = if (isArabic) FontWeight.Bold else FontWeight.Medium,
                        color = arTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(2.dp))

            // English option
            val isEnglish = currentLanguage == AppLanguage.ENGLISH
            val enBgColor by animateColorAsState(
                targetValue = if (isEnglish) ShajeenSkyBlue else Color.Transparent,
                animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
                label = "enBg"
            )
            val enTextColor by animateColorAsState(
                targetValue = if (isEnglish) Color.White else Color(0xFF475569),
                animationSpec = tween(durationMillis = 200),
                label = "enText"
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(enBgColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (!isEnglish) onLanguageSelected(AppLanguage.ENGLISH)
                    }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .testTag("language_pill_english"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "🇬🇧",
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "EN",
                        fontSize = 12.sp,
                        fontWeight = if (isEnglish) FontWeight.Bold else FontWeight.Medium,
                        color = enTextColor
                    )
                }
            }
        }
    }
}

/**
 * Full Segmented Language Switcher with detailed RTL / LTR layout direction badges.
 * Suitable for Profile, Settings, and modal dialogs.
 */
@Composable
fun LanguageSwitcherSegment(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .testTag("language_switcher_segment"),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Arabic Selection Card
        val isArabic = currentLanguage == AppLanguage.ARABIC
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(64.dp)
                .clip(RoundedCornerShape(14.dp))
                .clickable { onLanguageSelected(AppLanguage.ARABIC) }
                .testTag("lang_select_arabic"),
            shape = RoundedCornerShape(14.dp),
            color = if (isArabic) ShajeenSkyBlue.copy(alpha = 0.08f) else Color(0xFFF8FAFC),
            border = BorderStroke(
                width = if (isArabic) 2.dp else 1.dp,
                color = if (isArabic) ShajeenSkyBlue else Color(0xFFE2E8F0)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🇸🇦", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "العربية",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = if (isArabic) FontWeight.Bold else FontWeight.SemiBold,
                                color = if (isArabic) ShajeenDarkBlue else Color(0xFF334155)
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FormatTextdirectionRToL,
                                contentDescription = null,
                                tint = if (isArabic) ShajeenSkyBlue else Color.Gray,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "RTL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isArabic) ShajeenSkyBlue else Color.Gray
                            )
                        }
                    }
                }

                if (isArabic) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(ShajeenSkyBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Active",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        // English Selection Card
        val isEnglish = currentLanguage == AppLanguage.ENGLISH
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(64.dp)
                .clip(RoundedCornerShape(14.dp))
                .clickable { onLanguageSelected(AppLanguage.ENGLISH) }
                .testTag("lang_select_english"),
            shape = RoundedCornerShape(14.dp),
            color = if (isEnglish) ShajeenSkyBlue.copy(alpha = 0.08f) else Color(0xFFF8FAFC),
            border = BorderStroke(
                width = if (isEnglish) 2.dp else 1.dp,
                color = if (isEnglish) ShajeenSkyBlue else Color(0xFFE2E8F0)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🇬🇧", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "English",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = if (isEnglish) FontWeight.Bold else FontWeight.SemiBold,
                                color = if (isEnglish) ShajeenDarkBlue else Color(0xFF334155)
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FormatTextdirectionLToR,
                                contentDescription = null,
                                tint = if (isEnglish) ShajeenSkyBlue else Color.Gray,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "LTR",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isEnglish) ShajeenSkyBlue else Color.Gray
                            )
                        }
                    }
                }

                if (isEnglish) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(ShajeenSkyBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Active",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Dedicated Language Selection Card for Profile/Settings Screen.
 * Displays informative overview and RTL / LTR layout indicator.
 */
@Composable
fun LanguageSelectionCard(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("language_selection_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ShajeenGold.copy(alpha = 0.15f),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = ShajeenGold,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = strings.appLanguage,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShajeenDarkBlue
                            )
                        )
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC) strings.rtlDirectionDesc else strings.ltrDirectionDesc,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ShajeenSkyBlue,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

                // Layout Direction badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9)
                ) {
                    Text(
                        text = if (currentLanguage.isRtl) "RTL" else "LTR",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = strings.languageToggleHint,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            LanguageSwitcherSegment(
                currentLanguage = currentLanguage,
                onLanguageSelected = onLanguageSelected
            )
        }
    }
}

/**
 * Modal Dialog for selecting language.
 */
@Composable
fun LanguageSelectionDialog(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onDismissRequest: () -> Unit
) {
    val strings = LocalAppStrings.current

    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.testTag("language_selection_dialog"),
        shape = RoundedCornerShape(20.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = null,
                    tint = ShajeenSkyBlue
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = strings.switchLanguage,
                    fontWeight = FontWeight.Bold,
                    color = ShajeenDarkBlue
                )
            }
        },
        text = {
            Column {
                Text(
                    text = strings.languageToggleHint,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LanguageSwitcherSegment(
                    currentLanguage = currentLanguage,
                    onLanguageSelected = {
                        onLanguageSelected(it)
                        onDismissRequest()
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(strings.close, color = ShajeenSkyBlue, fontWeight = FontWeight.Bold)
            }
        }
    )
}
