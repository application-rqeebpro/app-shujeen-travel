package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.BookingEntity
import com.example.data.entity.ClientNotificationEntity
import com.example.data.entity.ElectronicBookingEntity
import com.example.ui.theme.ShajeenBodyText
import com.example.ui.theme.ShajeenCardBg
import com.example.ui.theme.ShajeenCardBorderSoft
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenHomeBgEnd
import com.example.ui.theme.ShajeenHomeBgStart
import com.example.ui.theme.ShajeenPrimaryButton
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue

@Composable
fun MyBookingsScreen(
    bookings: List<BookingEntity>,
    electronicBookings: List<ElectronicBookingEntity> = emptyList(),
    notifications: List<ClientNotificationEntity> = emptyList(),
    onCancelBooking: (BookingEntity) -> Unit,
    onBrowseServices: () -> Unit,
    onNavigateToElectronicBooking: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val strings = com.example.ui.locale.LocalAppStrings.current
    var selectedTab by remember { mutableStateOf("الحجز الإلكتروني") } // "الحجز الإلكتروني" or "حجوزات الخدمات"
    var viewingElectronicBooking by remember { mutableStateOf<ElectronicBookingEntity?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(ShajeenHomeBgStart, ShajeenHomeBgEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = strings.myBookingsTitle,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShajeenHeadingText
                        )
                    )
                    Text(
                        text = strings.bookingStatus,
                        style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ShajeenPrimaryButton.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "${electronicBookings.size + bookings.size} طلب",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ShajeenPrimaryButton,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Notifications Banner if any
            if (notifications.isNotEmpty()) {
                val unread = notifications.filter { !it.isRead }
                if (unread.isNotEmpty()) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = ShajeenGold.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, ShajeenGold.copy(alpha = 0.4f))
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = ShajeenDarkBlue, modifier = Modifier.size(22.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "لديك ${unread.size} إشعار جديد من الوكالة!",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = ShajeenDarkBlue)
                                )
                                Text(
                                    text = unread.first().message,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = ShajeenDarkBlue),
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }

            // Tabs Selector: Electronic Bookings vs Standard Services Bookings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val isTab1 = (selectedTab == "الحجز الإلكتروني")
                FilterChip(
                    selected = isTab1,
                    onClick = { selectedTab = "الحجز الإلكتروني" },
                    label = {
                        Text(
                            text = "التأشيرات والحجز الإلكتروني (${electronicBookings.size})",
                            fontWeight = if (isTab1) FontWeight.ExtraBold else FontWeight.Bold,
                            color = if (isTab1) Color.White else ShajeenHeadingText
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.White,
                        labelColor = ShajeenHeadingText,
                        selectedContainerColor = ShajeenPrimaryButton,
                        selectedLabelColor = Color.White
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isTab1,
                        borderColor = if (isTab1) ShajeenPrimaryButton else Color(0xFFCBD5E1),
                        selectedBorderColor = ShajeenPrimaryButton,
                        borderWidth = 1.dp
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                val isTab2 = (selectedTab == "حجوزات الخدمات")
                FilterChip(
                    selected = isTab2,
                    onClick = { selectedTab = "حجوزات الخدمات" },
                    label = {
                        Text(
                            text = "خدمات الوكالة (${bookings.size})",
                            fontWeight = if (isTab2) FontWeight.ExtraBold else FontWeight.Bold,
                            color = if (isTab2) Color.White else ShajeenHeadingText
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.White,
                        labelColor = ShajeenHeadingText,
                        selectedContainerColor = ShajeenPrimaryButton,
                        selectedLabelColor = Color.White
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isTab2,
                        borderColor = if (isTab2) ShajeenPrimaryButton else Color(0xFFCBD5E1),
                        selectedBorderColor = ShajeenPrimaryButton,
                        borderWidth = 1.dp
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            if (selectedTab == "الحجز الإلكتروني") {
                if (electronicBookings.isEmpty()) {
                    EmptyStateView(
                        title = "لا توجد طلبات حجز إلكتروني حتى الآن",
                        subtitle = "يمكنك تقديم طلب تأشيرة أو معاملة سفر ورفع المستندات والسداد عبر المحافظ بخطوات ميسرة.",
                        buttonText = "تقديم طلب حجز إلكتروني جديد",
                        onAction = onNavigateToElectronicBooking
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(electronicBookings, key = { it.id }) { eBooking ->
                            ElectronicBookingClientCard(
                                booking = eBooking,
                                onOpenDetails = { viewingElectronicBooking = eBooking },
                                onWhatsApp = {
                                    sendWhatsAppQuery(context, eBooking)
                                }
                            )
                        }
                    }
                }
            } else {
                if (bookings.isEmpty()) {
                    EmptyStateView(
                        title = "لا توجد أي حجوزات خدمات حتى الآن",
                        subtitle = "اختر من باقات رحلات الطيران، برامج العمرة، أو النقل الدولي وقدّم طلبك بسهولة.",
                        buttonText = "تصفح خدمات الوكالة",
                        onAction = onBrowseServices
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(bookings, key = { it.id }) { booking ->
                            BookingItemCard(
                                booking = booking,
                                onCancel = { onCancelBooking(booking) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Electronic Booking Details Dialog for Client
    viewingElectronicBooking?.let { b ->
        AlertDialog(
            onDismissRequest = { viewingElectronicBooking = null },
            title = {
                Text(
                    text = "تفاصيل طلبك رقم: ${b.bookingNumber}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("المعاملة: ${if (b.visaType == "أخرى") b.otherVisaType else b.visaType}", fontWeight = FontWeight.Bold)
                    Text("الوجهة: ${b.destination}")
                    if (b.travelDate.isNotBlank()) Text("تاريخ السفر: ${b.travelDate}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = ShajeenHomeBgStart,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, ShajeenCardBorderSoft)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("حالة الطلب: ${b.bookingStatus}", fontWeight = FontWeight.Bold, color = ShajeenPrimaryButton)
                            Text("حالة السداد: ${b.paymentStatus}", fontWeight = FontWeight.Bold)
                            Text("المحفظة المستخدمة: ${b.paymentMethod}")
                            if (b.transactionNumber.isNotBlank()) Text("رقم العملية: ${b.transactionNumber}")
                            if (b.adminNotes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("رسالة وملاحظات الإدارة لك:", fontWeight = FontWeight.Bold, color = ShajeenDarkBlue)
                                Text(b.adminNotes, color = ShajeenHeadingText)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewingElectronicBooking = null },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenPrimaryButton)
                ) {
                    Text("إغلاق")
                }
            }
        )
    }
}

@Composable
private fun ElectronicBookingClientCard(
    booking: ElectronicBookingEntity,
    onOpenDetails: () -> Unit,
    onWhatsApp: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenDetails() }
            .testTag("client_electronic_booking_${booking.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ShajeenPrimaryButton.copy(alpha = 0.15f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Default.CreditCard, contentDescription = null, tint = ShajeenPrimaryButton, modifier = Modifier.padding(8.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = booking.bookingNumber,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                        )
                        Text(
                            text = if (booking.visaType == "أخرى") booking.otherVisaType else booking.visaType,
                            style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText)
                        )
                    }
                }

                // Booking Status
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (booking.bookingStatus) {
                        "مؤكد" -> Color(0xFFDCFCE7)
                        "مرفوض" -> Color(0xFFFEE2E2)
                        "قيد المراجعة" -> Color(0xFFFEF3C7)
                        else -> ShajeenHomeBgStart
                    }
                ) {
                    Text(
                        text = booking.bookingStatus,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = when (booking.bookingStatus) {
                                "مؤكد" -> Color(0xFF16A34A)
                                "مرفوض" -> Color(0xFFDC2626)
                                "قيد المراجعة" -> Color(0xFFD97706)
                                else -> ShajeenPrimaryButton
                            }
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "الوجهة: ${booking.destination}",
                    style = MaterialTheme.typography.bodySmall.copy(color = ShajeenBodyText)
                )
                Text(
                    text = "حالة الدفع: ${booking.paymentStatus}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (booking.paymentStatus == "تم التحقق") Color(0xFF16A34A) else Color(0xFFD97706)
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onWhatsApp,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                    modifier = Modifier.weight(1f).height(36.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("متابعة بالواتساب", fontSize = 11.sp)
                }

                OutlinedButton(
                    onClick = onOpenDetails,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, ShajeenCardBorderSoft),
                    modifier = Modifier.weight(1f).height(36.dp)
                ) {
                    Text("عرض التفاصيل", fontSize = 11.sp, color = ShajeenHeadingText)
                }
            }
        }
    }
}

@Composable
private fun EmptyStateView(
    title: String,
    subtitle: String,
    buttonText: String,
    onAction: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Surface(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(24.dp),
                color = ShajeenPrimaryButton.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = ShajeenPrimaryButton,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ShajeenHeadingText
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ShajeenSecondaryText,
                    lineHeight = 20.sp
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onAction,
                colors = ButtonDefaults.buttonColors(containerColor = ShajeenPrimaryButton),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(buttonText, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun BookingItemCard(
    booking: BookingEntity,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("booking_item_${booking.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Service title and status badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = booking.serviceCategory,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ShajeenPrimaryButton,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = booking.serviceTitle,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShajeenHeadingText
                        )
                    )
                }

                StatusBadge(status = booking.status)
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(12.dp))

            // Booking Details Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Travel Date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = ShajeenSkyBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "موعد الرحلة",
                            style = MaterialTheme.typography.labelSmall.copy(color = ShajeenSecondaryText, fontSize = 10.sp)
                        )
                        Text(
                            text = booking.travelDate,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = ShajeenHeadingText)
                        )
                    }
                }

                // Passengers count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = null,
                        tint = ShajeenSkyBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "عدد المسافرين",
                            style = MaterialTheme.typography.labelSmall.copy(color = ShajeenSecondaryText, fontSize = 10.sp)
                        )
                        Text(
                            text = "${booking.passengersCount} شخص",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = ShajeenHeadingText)
                        )
                    }
                }
            }

            if (booking.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = ShajeenHomeBgStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "ملاحظاتك: ${booking.notes}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ShajeenSecondaryText,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            // Cancel action if pending
            if (booking.status == "قيد المراجعة") {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onCancel,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("cancel_booking_${booking.id}")
                    ) {
                        Icon(Icons.Default.Cancel, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("إلغاء الطلب", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (bgColor, textColor, icon) = when (status) {
        "مؤكد" -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), Icons.Default.CheckCircle)
        "مكتمل" -> Triple(Color(0xFFE3F2FD), Color(0xFF1565C0), Icons.Default.TaskAlt)
        "ملغي" -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), Icons.Default.Cancel)
        else -> Triple(Color(0xFFFFF8E1), Color(0xFFF57F17), Icons.Default.HourglassTop)
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bgColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = status,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            )
        }
    }
}

private fun sendWhatsAppQuery(context: Context, booking: ElectronicBookingEntity) {
    val text = """
        *استفسار حول طلب الحجز الإلكتروني*
        رقم الطلب: ${booking.bookingNumber}
        الاسم: ${booking.fullName}
        نوع المعاملة: ${if (booking.visaType == "أخرى") booking.otherVisaType else booking.visaType}
        الوجهة: ${booking.destination}
        أود متابعة حالة الطلب. شكراً لكم!
    """.trimIndent()

    try {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=967770038009&text=${Uri.encode(text)}")
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (e: Exception) {
        Toast.makeText(context, "تعذر فتح واتساب", Toast.LENGTH_SHORT).show()
    }
}
