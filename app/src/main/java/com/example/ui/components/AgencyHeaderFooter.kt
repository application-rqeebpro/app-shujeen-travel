package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.entity.AgencySettingsEntity
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenGoldLight
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenLightSky
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue
import com.example.ui.theme.ShajeenSkyContainer

/**
 * Top Customer Service Headline Bar matching Reference Image:
 * "خدمة العملاء والحجوزات الفورية بصنعاء" + sky blue phone number
 */
@Composable
fun CustomerServiceTopBar(
    phoneNumber: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { dialPhone(context, phoneNumber) }
            .testTag("customer_service_top_bar"),
        color = Color.White,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 1.dp,
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = ShajeenSkyContainer,
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = ShajeenSkyBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "خدمة العملاء والحجوزات الفورية بصنعاء",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShajeenHeadingText,
                            fontSize = 11.5.sp
                        )
                    )
                    Text(
                        text = "متاح على مدار الساعة",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShajeenSecondaryText,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            Text(
                text = phoneNumber,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = ShajeenSkyBlue,
                    fontSize = 13.5.sp
                )
            )
        }
    }
}

/**
 * Agency Owner Portal Card:
 * Dark navy card, crown emblem, golden accents, and yellow/gold "دخول المالك" button.
 * CRITICAL: NEVER displays the owner PIN or secret credentials.
 */
@Composable
fun AgencyOwnerPortalCard(
    onOwnerLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("agency_owner_portal_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenDarkBlue),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.dp, ShajeenGold.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            ShajeenDarkBlue,
                            Color(0xFF132F4C)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Shimmering Golden Crown / Badge
                Surface(
                    shape = CircleShape,
                    color = ShajeenGold.copy(alpha = 0.18f),
                    border = BorderStroke(1.dp, ShajeenGold.copy(alpha = 0.6f)),
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.MilitaryTech,
                            contentDescription = "تاج الإدارة",
                            tint = ShajeenGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "بوابة مالك الوكالة",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 14.5.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = ShajeenGold.copy(alpha = 0.25f)
                        ) {
                            Text(
                                text = "خاص بالإدارة",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ShajeenGold,
                                    fontSize = 9.sp
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "إدارة الحجوزات، المحافظ، والعمليات",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 11.5.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Golden "دخول المالك" button
            Button(
                onClick = onOwnerLoginClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ShajeenGold,
                    contentColor = ShajeenDarkBlue
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                modifier = Modifier.testTag("admin_login_top_button")
            ) {
                Text(
                    text = "دخول المالك",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.5.sp
                    )
                )
            }
        }
    }
}

/**
 * Service Quick Icons Row inside the header:
 * Flight, Visa, Hajj & Umrah, Land Transport, Tourism
 */
@Composable
fun ServiceIconsHeaderRow(
    modifier: Modifier = Modifier
) {
    val items = listOf(
        ServiceIconItem("طيران", Icons.Default.AirplanemodeActive),
        ServiceIconItem("تأشيرات", Icons.Default.Assignment),
        ServiceIconItem("عمرة وحج", Icons.Default.Star),
        ServiceIconItem("نقل دولي", Icons.Default.DirectionsBus),
        ServiceIconItem("سياحة", Icons.Default.TravelExplore)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = ShajeenSkyContainer,
                    border = BorderStroke(1.dp, Color(0xFFBAE6FD)),
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = ShajeenSkyBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = ShajeenHeadingText,
                        fontSize = 10.5.sp
                    )
                )
            }
        }
    }
}

private data class ServiceIconItem(val label: String, val icon: ImageVector)

@Composable
fun AgencyBrandLogo(
    modifier: Modifier = Modifier,
    size: Int = 46,
    showSubtitle: Boolean = false,
    onDarkBackground: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(size.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color.White,
            shadowElevation = 2.dp,
            border = BorderStroke(1.dp, ShajeenSkyBlue.copy(alpha = 0.35f))
        ) {
            Box(
                modifier = Modifier.padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_shajeen_logo),
                    contentDescription = "شعار وكالة شجين للسفريات والسياحة",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = "وكالة شجين",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (onDarkBackground) Color.White else ShajeenHeadingText,
                    fontSize = 17.sp
                )
            )
            Text(
                text = "للسفريات والسياحة",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = if (onDarkBackground) ShajeenGoldLight else ShajeenSkyBlue,
                    fontSize = 12.sp
                )
            )
            if (showSubtitle) {
                Text(
                    text = "Shajeen Travel & Tourism",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (onDarkBackground) Color.White.copy(alpha = 0.7f) else ShajeenSecondaryText,
                        fontSize = 9.sp
                    )
                )
            }
        }
    }
}

/**
 * Official Logo Card displaying the exact layout from the agency branding:
 * Ascending airplane and aerodynamic wave ribbons emblem on top,
 * "وكالة شجين" in bold navy Arabic typography,
 * "للسفريات والسياحة" in sky blue Arabic typography,
 * "Shajeen Travel & Tourism Agency" in English.
 */
@Composable
fun OfficialShajeenLogoCard(
    modifier: Modifier = Modifier,
    width: Int = 180,
    elevation: Int = 2
) {
    Surface(
        modifier = modifier.width(width.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = elevation.dp,
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 18.dp, horizontal = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // The official luxury agency logo emblem
            Surface(
                modifier = Modifier.size(width = 160.dp, height = 110.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 3.dp,
                border = BorderStroke(1.dp, ShajeenSkyBlue.copy(alpha = 0.35f))
            ) {
                Box(
                    modifier = Modifier.padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_shajeen_logo),
                        contentDescription = "شعار شجين",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "وكالة شجين للسفريات والسياحة",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = ShajeenHeadingText,
                    fontSize = 15.5.sp,
                    lineHeight = 22.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Shajeen Travel & Tourism Agency",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = ShajeenSkyBlue,
                    fontSize = 11.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

// Brand colors for the Contact Card matching the Shajeen design system
private val ContactDarkBlue = Color(0xFF0B2545)
private val ContactPrimaryBlue = Color(0xFF159BD3)
private val ContactLightBlue = Color(0xFFEAF7FC)
private val ContactBorderColor = Color(0xFFE2E8F0)

@Composable
fun AgencyContactFooter(
    settings: AgencySettingsEntity?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val address = settings?.address?.ifBlank { "صنعاء - شارع خولان - جوار السلامي لمواد البناء" }
        ?: "صنعاء - شارع خولان - جوار السلامي لمواد البناء"
    val whatsappNumber = settings?.whatsappNumber?.ifBlank { "770038009" } ?: "770038009"
    val phoneNumbers = listOf(
        settings?.phone1 ?: "+967 777779492",
        settings?.phone2 ?: "+966 551160835",
        settings?.phone3 ?: "+967 774191789",
        settings?.phone4 ?: "+967 770038009"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("agency_contact_footer"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, ContactBorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header Row: Title & Location
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = CircleShape,
                    color = ContactLightBlue,
                    border = BorderStroke(1.dp, ContactPrimaryBlue.copy(alpha = 0.35f)),
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "العنوان",
                            tint = ContactPrimaryBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "معلومات التواصل والعنوان",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = ContactDarkBlue,
                            fontSize = 15.5.sp
                        )
                    )
                    Text(
                        text = "المقر الرئيسي - صنعاء",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ContactPrimaryBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.5.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Address Container with Location Icon
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = ContactLightBlue,
                border = BorderStroke(1.dp, ContactPrimaryBlue.copy(alpha = 0.25f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "الموقع",
                        tint = ContactPrimaryBlue,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = address,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ContactDarkBlue,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.5.sp,
                            lineHeight = 18.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            // Contact Numbers Header Label
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = ContactLightBlue,
                    modifier = Modifier.size(26.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "أرقام التواصل",
                            tint = ContactPrimaryBlue,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "أرقام خدمة العملاء والحجوزات (الضغط للاتصال المباشر):",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ContactDarkBlue,
                        fontSize = 12.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Responsive Phone Cards in 2-column rows
            phoneNumbers.chunked(2).forEach { pair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pair.forEach { phone ->
                        PhoneChip(
                            phoneNumber = phone,
                            onDial = { dialPhone(context, phone) },
                            onCopy = {
                                clipboardManager.setText(AnnotatedString(phone))
                                Toast.makeText(context, "تم نسخ الرقم: $phone", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (pair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Action Buttons: "فتح الموقع 📍" and "تواصل معنا 💬"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1. فتح الموقع 📍
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { openLocation(context, address) }
                        .testTag("contact_btn_open_location"),
                    color = ContactLightBlue,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.2.dp, ContactPrimaryBlue.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "فتح الموقع 📍",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = ContactDarkBlue,
                                fontSize = 13.sp
                            )
                        )
                    }
                }

                // 2. تواصل معنا 💬
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { openWhatsAppContact(context, whatsappNumber) }
                        .testTag("contact_btn_chat_whatsapp"),
                    color = ContactPrimaryBlue,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "تواصل معنا 💬",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 13.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PhoneChip(
    phoneNumber: String,
    onDial: () -> Unit,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onDial() }
            .testTag("phone_chip_$phoneNumber"),
        color = ContactLightBlue,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, ContactPrimaryBlue.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier.size(22.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "اتصال",
                            tint = ContactPrimaryBlue,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ContactDarkBlue,
                        fontSize = 11.5.sp,
                        letterSpacing = 0.2.sp
                    ),
                    maxLines = 1
                )
            }
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable { onCopy() }
                    .testTag("copy_phone_$phoneNumber")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "نسخ الرقم",
                        tint = ContactPrimaryBlue,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

fun openLocation(context: Context, address: String) {
    try {
        val geoUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        context.startActivity(mapIntent)
    } catch (e: Exception) {
        try {
            val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(address)}")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            context.startActivity(webIntent)
        } catch (e2: Exception) {
            Toast.makeText(context, "الموقع: $address", Toast.LENGTH_SHORT).show()
        }
    }
}

fun openWhatsAppContact(context: Context, whatsappNumber: String) {
    try {
        val clean = whatsappNumber.replace("+", "").replace(" ", "").trim()
        val url = if (clean.startsWith("967")) "https://wa.me/$clean" else "https://wa.me/967$clean"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "الواتساب: $whatsappNumber", Toast.LENGTH_SHORT).show()
    }
}

fun dialPhone(context: Context, phoneNumber: String) {
    try {
        val cleanNumber = phoneNumber.replace(" ", "")
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$cleanNumber")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "تعذر فتح تطبيق الاتصال", Toast.LENGTH_SHORT).show()
    }
}
