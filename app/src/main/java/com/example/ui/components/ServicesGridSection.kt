package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.ClientEntity
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue
import com.example.ui.theme.ShajeenSkyContainer

/**
 * Service identifier for the 12 core services shown in the reference image
 */
enum class CoreServiceType(
    val title: String,
    val category: String,
    val shortDesc: String
) {
    FLIGHT("حجز تذاكر الطيران", "طيران", "حجوزات مؤكدة على جميع خطوط الطيران العالمية"),
    PASSPORT("جوازات السفر", "جوازات", "إصدار وتجديد جوازات السفر الرسمية"),
    VISA("التأشيرات ( الفيزا )", "تأشيرات", "تأشيرات العمل والزيارة والسياحة والعمرة"),
    HOTEL("حجوزات الفنادق", "فنادق", "فنادق مختارة قريبة من الحرمين وأرقى الوجهات"),
    INSURANCE("التأمين على السفر", "تأمين", "تغطية تأمينية دولية معتمدة لكافة السفارات"),
    TOURS("الرحلات السياحية", "سياحة", "برامج سياحية متكاملة لسقطرى وصلالة والعالم"),
    HAJJ_UMRAH("الحج والعمرة", "حج وعمرة", "باقات VIP واقتصادية براً وجواً مع التفويج"),
    CAR_RENTAL("تأجير السيارات", "نقل وسيارات", "سيارات VIP وسيارات صالون وسفريات خاصة"),
    BUSINESS("خدمات رجال الأعمال", "رجال أعمال", "طيران خاص وحجوزات أجنحة واستقبال VIP"),
    AIRPORT("الإستقبال والتوديع في المطارات", "خدمات مطار", "استقبال وتوديع وتسهيل معاملات الركاب"),
    TRANSLATION("ترجمة وتصديق الوثائق", "وثائق وترجمة", "ترجمة معتمدة وتصديق خارجية وسفارات"),
    CARGO("شحن الأمتعة والبضائع", "شحن وطرود", "شحن سريع ومضمون للأمتعة والطرود والوثائق")
}

/**
 * 4-column Grid section matching reference image exactly.
 */
@Composable
fun ServicesGridSection(
    currentClient: ClientEntity?,
    onOpenServiceFlow: (CoreServiceType) -> Unit,
    onViewAllServices: () -> Unit,
    onDialPhone: (String) -> Unit,
    onOpenWhatsapp: (String) -> Unit,
    onSendEmail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("services_grid_section")
    ) {
        // Section Title: "خدماتنا" with blue airplane icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FlightTakeoff,
                    contentDescription = null,
                    tint = ShajeenSkyBlue,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "خدماتنا",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = ShajeenHeadingText,
                        fontSize = 17.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // 4 Columns Grid (3 rows x 4 items)
        val servicesList = listOf(
            // Row 1 (Right to Left in RTL):
            CoreServiceType.FLIGHT,
            CoreServiceType.PASSPORT,
            CoreServiceType.VISA,
            CoreServiceType.HOTEL,

            // Row 2:
            CoreServiceType.INSURANCE,
            CoreServiceType.TOURS,
            CoreServiceType.HAJJ_UMRAH,
            CoreServiceType.CAR_RENTAL,

            // Row 3:
            CoreServiceType.BUSINESS,
            CoreServiceType.AIRPORT,
            CoreServiceType.TRANSLATION,
            CoreServiceType.CARGO
        )

        // Chunk into rows of 4
        servicesList.chunked(4).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { serviceType ->
                    ServiceItemCard(
                        serviceType = serviceType,
                        onClick = { onOpenServiceFlow(serviceType) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Pill button: "عرض جميع الخدمات" with chevron `<`
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onViewAllServices() }
                .testTag("view_all_services_pill_btn"),
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFBAE6FD)),
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = null,
                    tint = ShajeenSkyBlue,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "عرض جميع الخدمات",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = ShajeenSkyBlue,
                        fontSize = 14.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Official Agency Contact Card matching Reference Image
        AgencyContactPillCard(
            phone = "770038009",
            whatsapp = "777779494",
            address = "صنعاء - شارع خولان\nجوار السلمي لمواد البناء",
            email = "info@shujaintravel.com",
            onDial = onDialPhone,
            onWhatsapp = onOpenWhatsapp,
            onEmail = onSendEmail
        )
    }
}

/**
 * Individual Service Card in the 4-column Grid.
 * Rounded, soft shadow, light background, large 3D-styled icon, clear 2-line title.
 */
@Composable
fun ServiceItemCard(
    serviceType: CoreServiceType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(116.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("service_grid_item_${serviceType.name}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color(0xFFE2EBF5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Service Icon (Vivid, 3D style artwork)
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .padding(top = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                ServiceVisualIcon(serviceType = serviceType)
            }

            // Service Title (Bold Arabic text, up to 2 lines)
            Text(
                text = serviceType.title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = ShajeenHeadingText,
                    fontSize = 10.5.sp,
                    lineHeight = 14.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 2.dp)
            )
        }
    }
}

/**
 * Custom 3D Art Icons matching each service in the reference image.
 */
@Composable
fun ServiceVisualIcon(serviceType: CoreServiceType) {
    when (serviceType) {
        CoreServiceType.FLIGHT -> {
            // Blue Airline Tickets
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF29B6F6),
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.AirplanemodeActive,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
        CoreServiceType.PASSPORT -> {
            // Blue Passport Book with Gold Seal
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF0288D1),
                border = BorderStroke(1.dp, Color(0xFF81D4FA)),
                modifier = Modifier.size(44.dp, 48.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Public,
                        contentDescription = null,
                        tint = Color(0xFFFFD54F),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .width(18.dp)
                            .height(2.dp)
                            .background(Color.White.copy(alpha = 0.8f))
                    )
                }
            }
        }
        CoreServiceType.VISA -> {
            // Passport Book with Globe overlay
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF0277BD),
                border = BorderStroke(1.dp, Color(0xFF4FC3F7)),
                modifier = Modifier.size(44.dp, 48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF29B6F6),
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }
            }
        }
        CoreServiceType.HOTEL -> {
            // 3D Blue Hotel Building with Windows
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF0288D1),
                border = BorderStroke(1.dp, Color(0xFF81D4FA)),
                modifier = Modifier.size(44.dp, 46.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Hotel,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
        CoreServiceType.INSURANCE -> {
            // Blue Shield with White Checkmark
            Surface(
                shape = CircleShape,
                color = Color(0xFF0288D1),
                border = BorderStroke(2.dp, Color(0xFF81D4FA)),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
        CoreServiceType.TOURS -> {
            // Island with Palm Tree (3D Tour)
            Surface(
                shape = CircleShape,
                color = Color(0xFFE0F7FA),
                border = BorderStroke(1.dp, Color(0xFF4DD0E1)),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Terrain,
                        contentDescription = null,
                        tint = Color(0xFF00897B),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
        CoreServiceType.HAJJ_UMRAH -> {
            // Holy Kaaba 3D Icon
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF1E293B),
                border = BorderStroke(1.5.dp, ShajeenGold),
                modifier = Modifier.size(42.dp, 44.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(7.dp))
                    // Golden Kiswah Band
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .background(ShajeenGold)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Mosque,
                        contentDescription = null,
                        tint = ShajeenGold.copy(alpha = 0.8f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
        CoreServiceType.CAR_RENTAL -> {
            // 3D Blue Modern Car
            Surface(
                shape = CircleShape,
                color = Color(0xFFE1F5FE),
                border = BorderStroke(1.dp, Color(0xFF81D4FA)),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = Color(0xFF0288D1),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
        CoreServiceType.BUSINESS -> {
            // 3D Blue Executive Briefcase
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF0288D1),
                border = BorderStroke(1.dp, Color(0xFF81D4FA)),
                modifier = Modifier.size(44.dp, 40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.BusinessCenter,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        CoreServiceType.AIRPORT -> {
            // Traveler with Luggage
            Surface(
                shape = CircleShape,
                color = Color(0xFFE0F2FE),
                border = BorderStroke(1.dp, Color(0xFF7DD3FC)),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Luggage,
                        contentDescription = null,
                        tint = Color(0xFF0288D1),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
        CoreServiceType.TRANSLATION -> {
            // Document with Translate Badge
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFE0F2FE),
                border = BorderStroke(1.dp, Color(0xFF38BDF8)),
                modifier = Modifier.size(44.dp, 46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = null,
                        tint = Color(0xFF0288D1),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
        CoreServiceType.CARGO -> {
            // 3D Blue Delivery Cargo Box
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF0288D1),
                border = BorderStroke(1.dp, Color(0xFF81D4FA)),
                modifier = Modifier.size(44.dp, 44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    }
}

/**
 * Official Agency Contact Card matching Reference Image:
 * - Phone: 770038009 (خدمة العملاء)
 * - WhatsApp: 777779494 (واتساب)
 * - Address: صنعاء - شارع خولان جوار السلمي لمواد البناء
 * - Email: info@shujaintravel.com
 */
@Composable
fun AgencyContactPillCard(
    phone: String,
    whatsapp: String,
    address: String,
    email: String,
    onDial: (String) -> Unit,
    onWhatsapp: (String) -> Unit,
    onEmail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("agency_contact_pill_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFE2EBF5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Row 1: Location & Customer Service Phone
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Address (Left side in English, right in RTL)
                Row(
                    modifier = Modifier.weight(1.1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFE0F2FE),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = Color(0xFF0288D1),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = address,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShajeenHeadingText,
                            fontSize = 11.5.sp,
                            lineHeight = 16.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Phone (770038009)
                Row(
                    modifier = Modifier
                        .weight(0.9f)
                        .clickable { onDial(phone) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = phone,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0288D1),
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            text = "خدمة العملاء",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ShajeenSecondaryText,
                                fontSize = 10.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFE0F2FE),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = Color(0xFF0288D1),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            HorizontalDivider(color = Color(0xFFF1F5F9))

            // Row 2: Email & WhatsApp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Email
                Row(
                    modifier = Modifier
                        .weight(1.1f)
                        .clickable { onEmail(email) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFE0F2FE),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = Color(0xFF0288D1),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = ShajeenHeadingText,
                            fontSize = 11.5.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // WhatsApp (777779494)
                Row(
                    modifier = Modifier
                        .weight(0.9f)
                        .clickable { onWhatsapp(whatsapp) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = whatsapp,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0288D1),
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            text = "واتساب",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ShajeenSecondaryText,
                                fontSize = 10.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFE0F2FE),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
