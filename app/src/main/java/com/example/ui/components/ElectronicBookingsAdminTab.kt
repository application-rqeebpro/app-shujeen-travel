package com.example.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.UploadFile
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.ElectronicBookingEntity
import com.example.data.entity.PaymentWalletEntity
import com.example.data.entity.VisaRequirementEntity
import com.example.ui.theme.ShajeenBodyText
import com.example.ui.theme.ShajeenCardBg
import com.example.ui.theme.ShajeenCardBorderSoft
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenHomeBgStart
import com.example.ui.theme.ShajeenLightSky
import com.example.ui.theme.ShajeenPrimaryButton
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElectronicBookingsAdminTab(
    bookings: List<ElectronicBookingEntity>,
    wallets: List<PaymentWalletEntity>,
    visaRequirements: List<VisaRequirementEntity>,
    onUpdateBookingStatus: (bookingId: Long, userId: Long, bNum: String, newStatus: String, notes: String) -> Unit,
    onUpdatePaymentStatus: (bookingId: Long, userId: Long, bNum: String, paymentStatus: String) -> Unit,
    onRequestAdditionalDocs: (bookingId: Long, userId: Long, bNum: String, docDesc: String) -> Unit,
    onDeleteBooking: (ElectronicBookingEntity) -> Unit,
    onSaveWallet: (PaymentWalletEntity) -> Unit,
    onDeleteWallet: (PaymentWalletEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf("الكل") }
    var selectedSubTab by remember { mutableStateOf("الحجوزات") } // "الحجوزات" or "المحافظ والبيانات"

    // Dialog state for viewing/editing a specific booking
    var viewingBooking by remember { mutableStateOf<ElectronicBookingEntity?>(null) }
    var showRequestDocsDialog by remember { mutableStateOf(false) }
    var docsRequestText by remember { mutableStateOf("") }
    var adminNotesText by remember { mutableStateOf("") }

    // Wallet edit dialog
    var showWalletDialog by remember { mutableStateOf(false) }
    var editingWallet by remember { mutableStateOf<PaymentWalletEntity?>(null) }

    val statusFilters = listOf("الكل", "جديد", "قيد المراجعة", "بانتظار المستندات", "مؤكد", "مرفوض")

    val filteredBookings = remember(bookings, selectedFilter) {
        if (selectedFilter == "الكل") bookings
        else bookings.filter { it.bookingStatus == selectedFilter }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Sub-tabs: Bookings list vs Wallets management
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = (selectedSubTab == "الحجوزات"),
                onClick = { selectedSubTab = "الحجوزات" },
                label = { Text("الحجوزات الإلكترونية (${bookings.size})", fontWeight = FontWeight.Bold) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ShajeenPrimaryButton,
                    selectedLabelColor = Color.White
                )
            )
            FilterChip(
                selected = (selectedSubTab == "المحافظ والبيانات"),
                onClick = { selectedSubTab = "المحافظ والبيانات" },
                label = { Text("إدارة المحافظ (${wallets.size})", fontWeight = FontWeight.Bold) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ShajeenPrimaryButton,
                    selectedLabelColor = Color.White
                )
            )
        }

        if (selectedSubTab == "الحجوزات") {
            // Status filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                statusFilters.forEach { filter ->
                    FilterChip(
                        selected = (selectedFilter == filter),
                        onClick = { selectedFilter = filter },
                        label = { Text(filter, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ShajeenDarkBlue,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            if (filteredBookings.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = ShajeenSecondaryText,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "لا توجد طلبات حجز إلكتروني مطابقة",
                            style = MaterialTheme.typography.bodyMedium.copy(color = ShajeenSecondaryText)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredBookings, key = { it.id }) { booking ->
                        ElectronicBookingItemCard(
                            booking = booking,
                            onOpenDetails = {
                                viewingBooking = booking
                                adminNotesText = booking.adminNotes
                            },
                            onWhatsAppClick = {
                                sendStatusWhatsApp(context, booking)
                            },
                            onDelete = { onDeleteBooking(booking) }
                        )
                    }
                }
            }
        } else {
            // Wallets Management SubTab
            WalletsAdminSection(
                wallets = wallets,
                onAddWallet = {
                    editingWallet = null
                    showWalletDialog = true
                },
                onEditWallet = {
                    editingWallet = it
                    showWalletDialog = true
                },
                onDeleteWallet = onDeleteWallet
            )
        }
    }

    // Dialog: Full Booking Details & Management
    viewingBooking?.let { booking ->
        AlertDialog(
            onDismissRequest = { viewingBooking = null },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "طلب رقم: ${booking.bookingNumber}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "تاريخ التقديم: ${SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(Date(booking.createdAt))}",
                            style = MaterialTheme.typography.labelSmall.copy(color = ShajeenSecondaryText)
                        )
                    }
                    IconButton(onClick = { viewingBooking = null }) {
                        Icon(Icons.Default.Close, contentDescription = "إغلاق")
                    }
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    // Client Info
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = ShajeenHomeBgStart,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, ShajeenCardBorderSoft)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("بيانات مقدم الطلب:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = ShajeenHeadingText)
                            Text("الاسم: ${booking.fullName}", fontSize = 12.sp)
                            Text("الهاتف: ${booking.phone}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            if (booking.email.isNotBlank()) Text("البريد: ${booking.email}", fontSize = 11.sp)
                            Text("الوجهة: ${booking.country} ← ${booking.destination}", fontSize = 12.sp)
                            Text("نوع التأشيرة: ${if (booking.visaType == "أخرى") booking.otherVisaType else booking.visaType}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            if (booking.travelDate.isNotBlank()) Text("تاريخ السفر: ${booking.travelDate}", fontSize = 12.sp)
                            if (booking.notes.isNotBlank()) Text("ملاحظات العميل: ${booking.notes}", fontSize = 11.sp, color = ShajeenSecondaryText)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Payment & Verification Info
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = if (booking.paymentStatus == "تم التحقق") Color(0xFFF0FDF4) else ShajeenGold.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, if (booking.paymentStatus == "تم التحقق") Color(0xFF86EFAC) else ShajeenGold.copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("بيانات الدفع والتحقق:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = ShajeenHeadingText)
                            Text("المحفظة/الحساب: ${booking.paymentMethod}", fontSize = 12.sp)
                            Text("رقم الحوالة: ${if (booking.transactionNumber.isNotBlank()) booking.transactionNumber else "لا يوجد"}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("حالة الدفع: ${booking.paymentStatus}", fontSize = 12.sp, fontWeight = FontWeight.Bold,
                                color = if (booking.paymentStatus == "تم التحقق") Color(0xFF16A34A) else Color(0xFFD97706))

                            Spacer(modifier = Modifier.height(6.dp))

                            // Verify Payment Buttons
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Button(
                                    onClick = {
                                        onUpdatePaymentStatus(booking.id, booking.userId, booking.bookingNumber, "تم التحقق")
                                        viewingBooking = booking.copy(paymentStatus = "تم التحقق")
                                        Toast.makeText(context, "تم تأكيد والتحقق من الدفع يدوياً", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("تأكيد الدفع", fontSize = 11.sp)
                                }

                                OutlinedButton(
                                    onClick = {
                                        onUpdatePaymentStatus(booking.id, booking.userId, booking.bookingNumber, "مرفوض / غير مطابق")
                                        viewingBooking = booking.copy(paymentStatus = "مرفوض / غير مطابق")
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("رفض الحوالة", fontSize = 11.sp, color = Color(0xFFDC2626))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Booking Status Changer
                    Text("تحديث حالة الطلب العام:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("قيد المراجعة", "مؤكد", "مرفوض").forEach { st ->
                            val isCurrent = (booking.bookingStatus == st)
                            OutlinedButton(
                                onClick = {
                                    onUpdateBookingStatus(booking.id, booking.userId, booking.bookingNumber, st, adminNotesText)
                                    viewingBooking = booking.copy(bookingStatus = st, adminNotes = adminNotesText)
                                    Toast.makeText(context, "تم تغيير حالة الطلب إلى $st", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, if (isCurrent) ShajeenPrimaryButton else ShajeenCardBorderSoft),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (isCurrent) ShajeenPrimaryButton.copy(alpha = 0.1f) else Color.Transparent
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(st, fontSize = 10.sp, fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Admin Notes / Request more docs
                    OutlinedTextField(
                        value = adminNotesText,
                        onValueChange = { adminNotesText = it },
                        label = { Text("ملاحظات الإدارة للعميل") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        minLines = 2
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(
                        onClick = {
                            if (adminNotesText.isNotBlank()) {
                                onRequestAdditionalDocs(booking.id, booking.userId, booking.bookingNumber, adminNotesText)
                                Toast.makeText(context, "تم إرسال طلب المستندات والملاحظات إلى إشعارات العميل", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenDarkBlue),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("إرسال طلب مستندات / إشعار للعميل", fontSize = 11.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateBookingStatus(booking.id, booking.userId, booking.bookingNumber, booking.bookingStatus, adminNotesText)
                        viewingBooking = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenPrimaryButton)
                ) {
                    Text("حفظ التغييرات وإغلاق")
                }
            }
        )
    }

    // Wallet Dialog (Add/Edit)
    if (showWalletDialog) {
        var walletName by remember { mutableStateOf(editingWallet?.walletName ?: "") }
        var accountNumber by remember { mutableStateOf(editingWallet?.accountNumber ?: "770038009") }
        var instructions by remember { mutableStateOf(editingWallet?.instructions ?: "") }

        AlertDialog(
            onDismissRequest = { showWalletDialog = false },
            title = { Text(if (editingWallet == null) "إضافة محفظة أو حساب دفع" else "تعديل بيانات المحفظة") },
            text = {
                Column {
                    OutlinedTextField(
                        value = walletName,
                        onValueChange = { walletName = it },
                        label = { Text("اسم المحفظة / الحساب (مثال: محفظة جيب)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = accountNumber,
                        onValueChange = { accountNumber = it },
                        label = { Text("رقم الحساب / المحفظة") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = instructions,
                        onValueChange = { instructions = it },
                        label = { Text("تعليمات التحويل للعميل") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (walletName.isNotBlank() && accountNumber.isNotBlank()) {
                            onSaveWallet(
                                PaymentWalletEntity(
                                    id = editingWallet?.id ?: 0L,
                                    walletName = walletName.trim(),
                                    accountNumber = accountNumber.trim(),
                                    instructions = instructions.trim()
                                )
                            )
                            showWalletDialog = false
                            Toast.makeText(context, "تم حفظ بيانات المحفظة بنجاح", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenPrimaryButton)
                ) {
                    Text("حفظ")
                }
            },
            dismissButton = {
                TextButton(onClick = { showWalletDialog = false }) {
                    Text("إلغاء")
                }
            }
        )
    }
}

@Composable
private fun ElectronicBookingItemCard(
    booking: ElectronicBookingEntity,
    onOpenDetails: () -> Unit,
    onWhatsAppClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenDetails() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = ShajeenSkyBlue.copy(alpha = 0.15f),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Receipt, contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.padding(6.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = booking.bookingNumber,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                        )
                        Text(
                            text = booking.fullName,
                            style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText)
                        )
                    }
                }

                // Status Badge
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "التأشيرة: ${if (booking.visaType == "أخرى") booking.otherVisaType else booking.visaType}",
                    style = MaterialTheme.typography.bodySmall.copy(color = ShajeenHeadingText)
                )
                Text(
                    text = "الدفع: ${booking.paymentStatus}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (booking.paymentStatus == "تم التحقق") Color(0xFF16A34A) else Color(0xFFD97706)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // WhatsApp Quick Send
                    Button(
                        onClick = onWhatsAppClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("واتساب", fontSize = 11.sp)
                    }

                    // Open Details
                    OutlinedButton(
                        onClick = onOpenDetails,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("إدارة ومعاينة", fontSize = 11.sp, color = ShajeenHeadingText)
                    }
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "حذف", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
private fun WalletsAdminSection(
    wallets: List<PaymentWalletEntity>,
    onAddWallet: () -> Unit,
    onEditWallet: (PaymentWalletEntity) -> Unit,
    onDeleteWallet: (PaymentWalletEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("محافظ وحسابات السداد", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("يمكن تعديل أرقام الحسابات وتعليمات التحويل في أي وقت", style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText))
                }
                Button(
                    onClick = onAddWallet,
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenPrimaryButton),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("إضافة محفظة", fontSize = 11.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(wallets, key = { it.id }) { wallet ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
                border = BorderStroke(1.dp, ShajeenCardBorderSoft)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ShajeenSkyBlue.copy(alpha = 0.15f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.padding(8.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(wallet.walletName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = ShajeenHeadingText)
                            Text("الرقم: ${wallet.accountNumber}", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = ShajeenPrimaryButton)
                            if (wallet.instructions.isNotBlank()) {
                                Text(wallet.instructions, fontSize = 11.sp, color = ShajeenSecondaryText, maxLines = 1)
                            }
                        }
                    }

                    Row {
                        IconButton(onClick = { onEditWallet(wallet) }) {
                            Icon(Icons.Default.Edit, contentDescription = "تعديل", tint = ShajeenPrimaryButton)
                        }
                        IconButton(onClick = { onDeleteWallet(wallet) }) {
                            Icon(Icons.Default.Delete, contentDescription = "حذف", tint = Color(0xFFEF4444))
                        }
                    }
                }
            }
        }
    }
}

private fun sendStatusWhatsApp(context: Context, booking: ElectronicBookingEntity) {
    val phone = booking.phone.replace("+", "").replace(" ", "").trim()
    val cleanPhone = if (phone.startsWith("0")) "967${phone.removePrefix("0")}" else if (!phone.startsWith("967")) "967$phone" else phone
    val text = """
        *إشعار حالة الحجز - وكالة شجين للسفريات والسياحة*
        ------------------------------------------
        مرحباً أخي/أختي: *${booking.fullName}*
        رقم الطلب: *${booking.bookingNumber}*
        حالة المعاملة الحالية: *${booking.bookingStatus}*
        حالة الدفع: *${booking.paymentStatus}*
        ${if (booking.adminNotes.isNotBlank()) "ملاحظات الإدارة: ${booking.adminNotes}" else ""}
        ------------------------------------------
        نسعد بخدمتكم دائماً في وكالة شجين للسفريات والسياحة.
    """.trimIndent()

    try {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanPhone&text=${Uri.encode(text)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "تعذر فتح واتساب", Toast.LENGTH_SHORT).show()
    }
}
