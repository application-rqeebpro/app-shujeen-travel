package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenSkyBlue

/**
 * Top App Bar matching the reference image:
 * Hamburger menu on left, Agency Logo in center, Search, Support, Notifications on right.
 */
@Composable
fun ShajeenReferenceTopBar(
    onMenuClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onSupportClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    hasUnreadNotifications: Boolean = true,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("shajeen_reference_top_bar"),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Hamburger Menu
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .size(40.dp)
                    .testTag("top_bar_menu_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "القائمة",
                    tint = ShajeenDarkBlue,
                    modifier = Modifier.size(26.dp)
                )
            }

            // Center: Agency Logo + Name
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Official Shajeen Emblem lines
                Canvas(modifier = Modifier.size(32.dp, 28.dp)) {
                    val w = size.width
                    val h = size.height
                    // Ascending curved lines
                    val lineColors = listOf(
                        Color(0xFF0288D1),
                        Color(0xFF0277BD),
                        Color(0xFF01579B),
                        Color(0xFF0C2340)
                    )
                    for (i in 0 until 4) {
                        val y = h * (0.2f + i * 0.22f)
                        val strokeW = 3.5f
                        drawLine(
                            color = lineColors[i],
                            start = Offset(w * (0.15f + i * 0.08f), y),
                            end = Offset(w * 0.9f, y - 4f),
                            strokeWidth = strokeW
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "وكالة شجين",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = ShajeenDarkBlue,
                            fontSize = 17.sp,
                            letterSpacing = 0.5.sp
                        )
                    )
                    Text(
                        text = "للسفريات والسياحة",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShajeenSkyBlue,
                            fontSize = 9.sp
                        )
                    )
                }
            }

            // Right: Actions (Search, Support, Notifications)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "بحث",
                        tint = ShajeenDarkBlue,
                        modifier = Modifier.size(22.dp)
                    )
                }

                IconButton(
                    onClick = onSupportClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Headphones,
                        contentDescription = "خدمة العملاء",
                        tint = ShajeenDarkBlue,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { onNotificationsClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "الإشعارات",
                        tint = ShajeenDarkBlue,
                        modifier = Modifier.size(22.dp)
                    )
                    if (hasUnreadNotifications) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF0288D1))
                                .align(Alignment.TopEnd)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Luxury Hero Banner matching the reference image:
 * Sky blue cloud gradient, Burj Khalifa silhouette, flying airplane, Holy Kaaba,
 * "رحلتك تبدأ من هنا", "GLOBAL TRAVEL EXPERIENCE LUXURY JOURNEYS", "اكتشف العديد من العروض", dots indicators.
 */
@Composable
fun ShajeenHeroBannerCard(
    onDiscoverClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .testTag("shajeen_hero_banner_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFBAE6FD), // Sky blue top
                            Color(0xFFE0F2FE), // Light sky middle
                            Color(0xFFF0F9FF)  // Cloud white bottom
                        )
                    )
                )
        ) {
            // Background Artwork Canvas: Burj Khalifa on left, clouds, Kaaba on right
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Soft clouds at bottom
                val cloudBrush = Brush.verticalGradient(
                    colors = listOf(Color.White.copy(alpha = 0.4f), Color.White)
                )
                drawCircle(
                    brush = cloudBrush,
                    radius = h * 0.45f,
                    center = Offset(w * 0.15f, h * 0.95f)
                )
                drawCircle(
                    brush = cloudBrush,
                    radius = h * 0.55f,
                    center = Offset(w * 0.5f, h * 1.05f)
                )
                drawCircle(
                    brush = cloudBrush,
                    radius = h * 0.45f,
                    center = Offset(w * 0.85f, h * 0.95f)
                )

                // Burj Khalifa modern spire silhouette (Left side in LTR, so offset near x=w*0.18f)
                val towerColor = Color(0xFF64748B).copy(alpha = 0.35f)
                // Spire
                drawLine(
                    color = towerColor,
                    start = Offset(w * 0.22f, h * 0.15f),
                    end = Offset(w * 0.22f, h * 0.4f),
                    strokeWidth = 3f
                )
                // Tower tiers
                drawRoundRect(
                    color = towerColor,
                    topLeft = Offset(w * 0.20f, h * 0.4f),
                    size = Size(w * 0.04f, h * 0.45f),
                    cornerRadius = CornerRadius(2f, 2f)
                )
                drawRoundRect(
                    color = towerColor,
                    topLeft = Offset(w * 0.17f, h * 0.55f),
                    size = Size(w * 0.10f, h * 0.35f),
                    cornerRadius = CornerRadius(4f, 4f)
                )

                // Holy Kaaba silhouette on right side (x=w*0.78f)
                val kaabaX = w * 0.78f
                val kaabaY = h * 0.62f
                val kaabaW = w * 0.16f
                val kaabaH = h * 0.26f

                // Kaaba black cube
                drawRoundRect(
                    color = Color(0xFF1E293B),
                    topLeft = Offset(kaabaX, kaabaY),
                    size = Size(kaabaW, kaabaH),
                    cornerRadius = CornerRadius(4f, 4f)
                )
                // Golden belt
                drawRect(
                    color = Color(0xFFF59E0B),
                    topLeft = Offset(kaabaX, kaabaY + kaabaH * 0.2f),
                    size = Size(kaabaW, kaabaH * 0.12f)
                )
            }

            // Airplane flying in sky
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 18.dp, end = 60.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AirplanemodeActive,
                        contentDescription = null,
                        tint = Color(0xFF0288D1),
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            // Foreground Text and CTA
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Arabic Slogan: "رحلتك تبدأ من هنا"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "رحلتك تبدأ",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = ShajeenDarkBlue,
                                fontSize = 21.sp
                            )
                        )
                        Text(
                            text = "من هنا",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF0288D1),
                                fontSize = 21.sp
                            )
                        )
                    }
                }

                // Middle English Subtitle & CTA button
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "GLOBAL TRAVEL EXPERIENCE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF64748B),
                            fontSize = 8.5.sp,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "LUXURY JOURNEYS",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0288D1),
                            fontSize = 13.sp,
                            letterSpacing = 1.2.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Pill Button: "اكتشف العديد من العروض"
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onDiscoverClick() },
                        color = Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, Color(0xFFBAE6FD)),
                        shadowElevation = 1.dp
                    ) {
                        Text(
                            text = "اكتشف العديد من العروض",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShajeenDarkBlue,
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp)
                        )
                    }
                }

                // Bottom Pagination 3 Dots (Active dot is solid blue)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(18.dp)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFF0288D1))
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFBAE6FD))
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFBAE6FD))
                    )
                }
            }
        }
    }
}
