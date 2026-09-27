package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.entity.AdminLogEntity
import com.example.data.entity.AgencyNewsEntity
import com.example.data.entity.AgencySettingsEntity
import com.example.data.entity.BookingEntity
import com.example.data.entity.ClientEntity
import com.example.data.entity.ClientNotificationEntity
import com.example.data.entity.ElectronicBookingEntity
import com.example.data.entity.PaymentWalletEntity
import com.example.data.entity.TravelServiceEntity
import com.example.data.entity.VisaRequirementEntity
import com.example.ui.components.ElectronicBookingsAdminTab
import com.example.ui.components.dialPhone
import com.example.ui.components.getServiceIcon
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenGoldLight
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenHomeBgEnd
import com.example.ui.theme.ShajeenHomeBgStart
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue
import com.example.ui.theme.ShajeenSkyContainer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    services: List<TravelServiceEntity>,
    clients: List<ClientEntity>,
    bookings: List<BookingEntity>,
    newsList: List<AgencyNewsEntity>,
    agencySettings: AgencySettingsEntity?,
    electronicBookings: List<ElectronicBookingEntity> = emptyList(),
    paymentWallets: List<PaymentWalletEntity> = emptyList(),
    visaRequirements: List<VisaRequirementEntity> = emptyList(),
    notifications: List<ClientNotificationEntity> = emptyList(),
    adminLogs: List<AdminLogEntity> = emptyList(),
    onUpdateElectronicBookingStatus: (Long, Long, String, String, String) -> Unit = { _, _, _, _, _ -> },
    onUpdateElectronicPaymentStatus: (Long, Long, String, String) -> Unit = { _, _, _, _ -> },
    onRequestAdditionalDocs: (Long, Long, String, String) -> Unit = { _, _, _, _ -> },
    onDeleteElectronicBooking: (ElectronicBookingEntity) -> Unit = {},
    onSavePaymentWallet: (PaymentWalletEntity) -> Unit = {},
    onDeletePaymentWallet: (PaymentWalletEntity) -> Unit = {},
    onSaveService: (TravelServiceEntity) -> Unit,
    onDeleteService: (TravelServiceEntity) -> Unit,
    onToggleServiceVisibility: (TravelServiceEntity) -> Unit = {},
    onToggleServiceOffer: (TravelServiceEntity) -> Unit = {},
    onDeleteClient: (ClientEntity) -> Unit,
    onUpdateClient: (ClientEntity) -> Unit = {},
    onToggleClientStatus: (ClientEntity) -> Unit = {},
    onUpdateBookingStatus: (bookingId: Long, status: String) -> Unit,
    onUpdateBookingStatusAndNotes: (Long, String, String) -> Unit = { _, _, _ -> },
    onDeleteBooking: (BookingEntity) -> Unit = {},
    onUpdateAgencySettings: (
        address: String,
        phone1: String,
        phone2: String,
        phone3: String,
        phone4: String,
        announcement: String
    ) -> Unit,
    onUpdateAgencyFullSettings: (
        agencyName: String,
        address: String,
        phone1: String,
        phone2: String,
        phone3: String,
        phone4: String,
        announcement: String,
        whatsapp: String,
        email: String,
        hours: String,
        social: String,
        about: String,
        tagline: String,
        showAnnouncement: Boolean,
        maintenance: Boolean
    ) -> Unit = { _, _, _, _, _, _, _, _, _, _, _, _, _, _, _ -> },
    onAddNews: (title: String, content: String, dateText: String, tag: String) -> Unit,
    onUpdateNews: (AgencyNewsEntity) -> Unit = {},
    onDeleteNews: (AgencyNewsEntity) -> Unit,
    onSendPushNotification: (title: String, message: String, targetUserId: Long?) -> Unit = { _, _, _ -> },
    onDeleteNotification: (Long) -> Unit = {},
    onChangeAdminPassword: (oldPin: String, newPin: String, onResult: (Boolean, String) -> Unit) -> Unit = { _, _, cb -> cb(false, "") },
    onClearAdminLogs: () -> Unit = {},
    onReturnToHome: () -> Unit = {},
    onLogoutAdmin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }

    val adminTabs = listOf(
        AdminTabItem("الإحصائيات", Icons.Default.Dashboard),
        AdminTabItem("المستخدمون (${clients.size})", Icons.Default.People),
        AdminTabItem("الحجوزات (${bookings.size})", Icons.Default.Bookmark),
        AdminTabItem("التأشيرات (${electronicBookings.size})", Icons.Default.FlightTakeoff),
        AdminTabItem("الخدمات والعروض (${services.size})", Icons.Default.ShoppingBag),
        AdminTabItem("الأخبار والإعلانات", Icons.Default.Campaign),
        AdminTabItem("الإشعارات", Icons.Default.Notifications),
        AdminTabItem("المحافظ والدفع (${paymentWallets.size})", Icons.Default.AccountBalanceWallet),
        AdminTabItem("إعدادات الوكالة", Icons.Default.Business),
        AdminTabItem("الرئيسية والمحتوى", Icons.Default.Public),
        AdminTabItem("الأمان وسجل العمليات", Icons.Default.Security)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ShajeenHomeBgStart)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar: Agency Owner Header
            Surface(
                color = ShajeenDarkBlue,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = ShajeenGold.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, ShajeenGold.copy(alpha = 0.6f)),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.MilitaryTech,
                                        contentDescription = "تاج المالك",
                                        tint = ShajeenGold,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = "بوابة مالك الوكالة - لوحة التحكم الشاملة",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                )
                                Text(
                                    text = agencySettings?.agencyName ?: "وكالة شجين للسفريات والسياحة",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ShajeenGoldLight,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        // Home and Logout Buttons
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = onReturnToHome,
                                modifier = Modifier.testTag("admin_home_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "العودة للرئيسية",
                                    tint = Color.White
                                )
                            }
                            IconButton(
                                onClick = onLogoutAdmin,
                                modifier = Modifier.testTag("admin_logout_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = "تسجيل خروج المالك",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Horizontal Tab Selector Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        adminTabs.forEachIndexed { index, tab ->
                            val isSelected = (selectedTab == index)
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { selectedTab = index }
                                    .testTag("admin_tab_$index"),
                                color = if (isSelected) ShajeenGold else Color.White.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = tab.icon,
                                        contentDescription = null,
                                        tint = if (isSelected) ShajeenDarkBlue else Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = tab.title,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = if (isSelected) ShajeenDarkBlue else Color.White,
                                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Tab Content Body
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                when (selectedTab) {
                    0 -> AdminOverviewTab(
                        clientsCount = clients.size,
                        bookingsCount = bookings.size,
                        electronicBookingsCount = electronicBookings.size,
                        servicesCount = services.size,
                        walletsCount = paymentWallets.size,
                        adminLogs = adminLogs,
                        onNavigateTab = { selectedTab = it }
                    )
                    1 -> AdminClientsTab(
                        clients = clients,
                        bookings = bookings,
                        electronicBookings = electronicBookings,
                        onUpdateClient = onUpdateClient,
                        onToggleClientStatus = onToggleClientStatus,
                        onDeleteClient = onDeleteClient
                    )
                    2 -> AdminBookingsTab(
                        bookings = bookings,
                        onUpdateStatus = onUpdateBookingStatusAndNotes,
                        onDeleteBooking = onDeleteBooking
                    )
                    3 -> ElectronicBookingsAdminTab(
                        bookings = electronicBookings,
                        wallets = paymentWallets,
                        visaRequirements = visaRequirements,
                        onUpdateBookingStatus = onUpdateElectronicBookingStatus,
                        onUpdatePaymentStatus = onUpdateElectronicPaymentStatus,
                        onRequestAdditionalDocs = onRequestAdditionalDocs,
                        onDeleteBooking = onDeleteElectronicBooking,
                        onSaveWallet = onSavePaymentWallet,
                        onDeleteWallet = onDeletePaymentWallet
                    )
                    4 -> AdminServicesTab(
                        services = services,
                        onSaveService = onSaveService,
                        onDeleteService = onDeleteService,
                        onToggleVisibility = onToggleServiceVisibility,
                        onToggleOffer = onToggleServiceOffer
                    )
                    5 -> AdminNewsTab(
                        newsList = newsList,
                        announcement = agencySettings?.announcement ?: "",
                        showAnnouncement = agencySettings?.showAnnouncement ?: true,
                        onAddNews = onAddNews,
                        onUpdateNews = onUpdateNews,
                        onDeleteNews = onDeleteNews,
                        onSaveAnnouncement = { ann, show ->
                            agencySettings?.let { s ->
                                onUpdateAgencySettings(s.address, s.phone1, s.phone2, s.phone3, s.phone4, ann)
                            }
                        }
                    )
                    6 -> AdminNotificationsTab(
                        clients = clients,
                        notifications = notifications,
                        onSendNotification = onSendPushNotification,
                        onDeleteNotification = onDeleteNotification
                    )
                    7 -> AdminWalletsTab(
                        wallets = paymentWallets,
                        onSaveWallet = onSavePaymentWallet,
                        onDeleteWallet = onDeletePaymentWallet
                    )
                    8 -> AdminAgencySettingsTab(
                        settings = agencySettings,
                        onSaveSettings = onUpdateAgencyFullSettings
                    )
                    9 -> AdminHomeContentTab(
                        settings = agencySettings,
                        onSaveSettings = onUpdateAgencyFullSettings
                    )
                    10 -> AdminSecurityTab(
                        adminLogs = adminLogs,
                        onChangePassword = onChangeAdminPassword,
                        onClearLogs = onClearAdminLogs,
                        totalClients = clients.size,
                        totalBookings = bookings.size,
                        totalElectronic = electronicBookings.size,
                        totalServices = services.size,
                        onLogout = onLogoutAdmin
                    )
                }
            }
        }
    }
}

private data class AdminTabItem(val title: String, val icon: ImageVector)

// ==========================================
// 1. OVERVIEW & ANALYTICS TAB
// ==========================================
@Composable
private fun AdminOverviewTab(
    clientsCount: Int,
    bookingsCount: Int,
    electronicBookingsCount: Int,
    servicesCount: Int,
    walletsCount: Int,
    adminLogs: List<AdminLogEntity>,
    onNavigateTab: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Stats Grid
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatCard(
                title = "إجمالي العملاء",
                value = "$clientsCount عميل",
                icon = Icons.Default.People,
                color = ShajeenSkyBlue,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateTab(1) }
            )
            StatCard(
                title = "حجوزات الخدمات",
                value = "$bookingsCount طلب",
                icon = Icons.Default.Bookmark,
                color = ShajeenDarkBlue,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateTab(2) }
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatCard(
                title = "التأشيرات الإلكترونية",
                value = "$electronicBookingsCount معاملة",
                icon = Icons.Default.FlightTakeoff,
                color = ShajeenGold,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateTab(3) }
            )
            StatCard(
                title = "الخدمات المتاحة",
                value = "$servicesCount خدمة",
                icon = Icons.Default.ShoppingBag,
                color = Color(0xFF10B981),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateTab(4) }
            )
        }

        // Quick Actions Row
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "إجراءات سريعة للمالك",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ShajeenHeadingText
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onNavigateTab(6) },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("إرسال إشعار", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onNavigateTab(4) },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenDarkBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("إضافة خدمة", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onNavigateTab(10) },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenGold),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = ShajeenDarkBlue, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("تغيير الرمز", color = ShajeenDarkBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Recent Audit Log Feed
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.History, contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "سجل العمليات والنشاط الأخير",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                        )
                    }
                    TextButton(onClick = { onNavigateTab(10) }) {
                        Text("عرض الكل", color = ShajeenSkyBlue, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (adminLogs.isEmpty()) {
                    Text(
                        text = "لا توجد عمليات مسجلة حتى الآن. ستظهر جميع العمليات الإدارية هنا تلقائياً.",
                        style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                    adminLogs.take(5).forEach { log ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = log.actionType,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                                )
                                Text(
                                    text = log.details,
                                    style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText, fontSize = 11.5.sp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Text(
                                text = sdf.format(Date(log.timestamp)),
                                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray, fontSize = 10.sp)
                            )
                        }
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = CircleShape,
                    color = color.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
                    }
                }
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = color.copy(alpha = 0.3f), modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(title, style = MaterialTheme.typography.labelSmall.copy(color = ShajeenSecondaryText, fontSize = 11.sp))
            Text(value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = ShajeenHeadingText))
        }
    }
}

// ==========================================
// 2. CLIENTS & USERS TAB
// ==========================================
@Composable
private fun AdminClientsTab(
    clients: List<ClientEntity>,
    bookings: List<BookingEntity>,
    electronicBookings: List<ElectronicBookingEntity>,
    onUpdateClient: (ClientEntity) -> Unit,
    onToggleClientStatus: (ClientEntity) -> Unit,
    onDeleteClient: (ClientEntity) -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var editingClient by remember { mutableStateOf<ClientEntity?>(null) }
    var deletingClient by remember { mutableStateOf<ClientEntity?>(null) }

    val filteredClients = clients.filter {
        searchQuery.isBlank() ||
        it.fullName.contains(searchQuery, ignoreCase = true) ||
        it.phone.contains(searchQuery, ignoreCase = true) ||
        it.idNumber.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("ابحث عن مستخدم بالاسم أو رقم الهاتف أو الهوية...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ShajeenSkyBlue) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = ShajeenSkyBlue,
                unfocusedBorderColor = Color(0xFFCBD5E1)
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (filteredClients.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("لا يوجد عملاء مطابقون للبحث", color = ShajeenSecondaryText)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(filteredClients, key = { it.id }) { client ->
                    val clientBookingsCount = bookings.count { it.clientId == client.id }
                    val clientEBookingsCount = electronicBookings.count { it.userId == client.id }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                                        shape = CircleShape,
                                        color = if (client.isActive) ShajeenSkyContainer else Color(0xFFFFE4E6),
                                        modifier = Modifier.size(38.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                Icons.Default.People,
                                                contentDescription = null,
                                                tint = if (client.isActive) ShajeenSkyBlue else Color(0xFFE11D48),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = client.fullName,
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                                        )
                                        Text(
                                            text = "${client.phone} • ${client.city}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText, fontSize = 11.5.sp)
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (client.isActive) Color(0xFFD1FAE5) else Color(0xFFFFE4E6)
                                ) {
                                    Text(
                                        text = if (client.isActive) "نشط" else "معطل",
                                        color = if (client.isActive) Color(0xFF065F46) else Color(0xFF9F1239),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "الهوية: ${client.idType} (${client.idNumber}) • الحجوزات: ${clientBookingsCount + clientEBookingsCount} طلب",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontSize = 11.sp)
                            )

                            if (client.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "ملاحظات الإدارة: ${client.notes}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = ShajeenGold, fontSize = 11.sp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(8.dp))

                            // Action buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Call
                                IconButton(onClick = { dialPhone(context, client.phone) }, modifier = Modifier.size(34.dp)) {
                                    Icon(Icons.Default.Phone, contentDescription = "اتصال", tint = ShajeenSkyBlue, modifier = Modifier.size(18.dp))
                                }

                                // Toggle Active
                                TextButton(onClick = { onToggleClientStatus(client) }) {
                                    Text(if (client.isActive) "تعطيل" else "تفعيل", color = if (client.isActive) Color.Red else Color(0xFF10B981), fontSize = 12.sp)
                                }

                                // Edit
                                TextButton(onClick = { editingClient = client }) {
                                    Text("تعديل", color = ShajeenSkyBlue, fontSize = 12.sp)
                                }

                                // Delete
                                TextButton(onClick = { deletingClient = client }) {
                                    Text("حذف", color = Color.Gray, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Edit Client Dialog
    editingClient?.let { client ->
        var name by remember { mutableStateOf(client.fullName) }
        var phone by remember { mutableStateOf(client.phone) }
        var idNum by remember { mutableStateOf(client.idNumber) }
        var city by remember { mutableStateOf(client.city) }
        var notes by remember { mutableStateOf(client.notes) }

        AlertDialog(
            onDismissRequest = { editingClient = null },
            title = { Text("تعديل بيانات العميل", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("الاسم") }, singleLine = true)
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("الهاتف") }, singleLine = true)
                    OutlinedTextField(value = idNum, onValueChange = { idNum = it }, label = { Text("رقم الهوية") }, singleLine = true)
                    OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("المدينة") }, singleLine = true)
                    OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("ملاحظات إدارية") })
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = client.copy(
                            fullName = name.trim(),
                            phone = phone.trim(),
                            idNumber = idNum.trim(),
                            city = city.trim(),
                            notes = notes.trim()
                        )
                        onUpdateClient(updated)
                        editingClient = null
                        Toast.makeText(context, "تم حفظ التعديلات", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue)
                ) {
                    Text("حفظ")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingClient = null }) { Text("إلغاء") }
            }
        )
    }

    // Delete Client Dialog
    deletingClient?.let { client ->
        AlertDialog(
            onDismissRequest = { deletingClient = null },
            title = { Text("تأكيد حذف الحساب", fontWeight = FontWeight.Bold, color = Color.Red) },
            text = { Text("هل أنت متأكد من رغبتك في حذف حساب العميل (${client.fullName}) ورقم هاتفه (${client.phone}) نهائياً؟") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteClient(client)
                        deletingClient = null
                        Toast.makeText(context, "تم حذف حساب العميل", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("حذف نهائي")
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingClient = null }) { Text("إلغاء") }
            }
        )
    }
}

// ==========================================
// 3. BOOKINGS MANAGEMENT TAB
// ==========================================
@Composable
private fun AdminBookingsTab(
    bookings: List<BookingEntity>,
    onUpdateStatus: (Long, String, String) -> Unit,
    onDeleteBooking: (BookingEntity) -> Unit
) {
    val context = LocalContext.current
    var statusFilter by remember { mutableStateOf("الكل") }
    var searchQuery by remember { mutableStateOf("") }
    var editingNotesBooking by remember { mutableStateOf<BookingEntity?>(null) }
    var deletingBooking by remember { mutableStateOf<BookingEntity?>(null) }

    val statuses = listOf("الكل", "جديد", "قيد المراجعة", "تم التأكيد", "تم الدفع", "مكتمل", "ملغي")

    val filtered = bookings.filter {
        val matchStatus = statusFilter == "الكل" || it.status == statusFilter
        val matchSearch = searchQuery.isBlank() ||
                it.clientName.contains(searchQuery, ignoreCase = true) ||
                it.clientPhone.contains(searchQuery, ignoreCase = true) ||
                it.serviceTitle.contains(searchQuery, ignoreCase = true)
        matchStatus && matchSearch
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("بحث عن حجز بالعميل أو الخدمة...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ShajeenSkyBlue) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = ShajeenSkyBlue,
                unfocusedBorderColor = Color(0xFFCBD5E1)
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Status filter chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            statuses.forEach { s ->
                val isSel = statusFilter == s
                FilterChip(
                    selected = isSel,
                    onClick = { statusFilter = s },
                    label = {
                        Text(
                            text = s,
                            fontWeight = if (isSel) FontWeight.ExtraBold else FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (isSel) Color.White else ShajeenHeadingText
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.White,
                        labelColor = ShajeenHeadingText,
                        selectedContainerColor = ShajeenSkyBlue,
                        selectedLabelColor = Color.White
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSel,
                        borderColor = if (isSel) ShajeenSkyBlue else Color(0xFFCBD5E1),
                        selectedBorderColor = ShajeenSkyBlue,
                        borderWidth = if (isSel) 1.5.dp else 1.dp
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (filtered.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("لا توجد حجوزات مطابقة", color = ShajeenSecondaryText)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(filtered, key = { it.id }) { booking ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = booking.serviceTitle,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                                )

                                AdminStatusBadge(status = booking.status)
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "العميل: ${booking.clientName} (${booking.clientPhone})",
                                style = MaterialTheme.typography.bodySmall.copy(color = ShajeenHeadingText, fontWeight = FontWeight.Medium)
                            )
                            Text(
                                text = "تاريخ الرحلة: ${booking.travelDate} • المسافرين: ${booking.passengersCount}",
                                style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText, fontSize = 11.5.sp)
                            )

                            if (booking.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "ملاحظات المسافر: ${booking.notes}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray, fontSize = 11.sp)
                                )
                            }

                            if (booking.adminNotes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "ملاحظات الإدارة الداخلية: ${booking.adminNotes}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = ShajeenGold, fontWeight = FontWeight.SemiBold, fontSize = 11.5.sp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(8.dp))

                            // Status switcher buttons & actions
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Contact Actions
                                IconButton(onClick = { dialPhone(context, booking.clientPhone) }, modifier = Modifier.size(32.dp)) {
                                    Icon(Icons.Default.Phone, contentDescription = "اتصال", tint = ShajeenSkyBlue, modifier = Modifier.size(16.dp))
                                }

                                // Quick Status Changes
                                listOf("تم التأكيد", "تم الدفع", "مكتمل", "ملغي").forEach { nextStatus ->
                                    if (booking.status != nextStatus) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = ShajeenSkyContainer,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable { onUpdateStatus(booking.id, nextStatus, booking.adminNotes) }
                                        ) {
                                            Text(
                                                text = nextStatus,
                                                color = ShajeenDarkBlue,
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }

                                // Edit Notes
                                TextButton(onClick = { editingNotesBooking = booking }) {
                                    Text("ملاحظة", fontSize = 11.sp, color = ShajeenSkyBlue)
                                }

                                // Delete
                                IconButton(onClick = { deletingBooking = booking }, modifier = Modifier.size(32.dp)) {
                                    Icon(Icons.Default.Delete, contentDescription = "حذف", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Edit Notes Dialog
    editingNotesBooking?.let { b ->
        var notesText by remember { mutableStateOf(b.adminNotes) }
        var statusSelection by remember { mutableStateOf(b.status) }

        AlertDialog(
            onDismissRequest = { editingNotesBooking = null },
            title = { Text("تحديث الحجز وملاحظات الإدارة", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("الحجز: ${b.serviceTitle} للعميل ${b.clientName}")
                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        label = { Text("الملاحظات الإدارية الداخلية") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateStatus(b.id, statusSelection, notesText.trim())
                        editingNotesBooking = null
                        Toast.makeText(context, "تم حفظ الملاحظات والحالة", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue)
                ) {
                    Text("حفظ")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingNotesBooking = null }) { Text("إلغاء") }
            }
        )
    }

    // Delete Booking Dialog
    deletingBooking?.let { b ->
        AlertDialog(
            onDismissRequest = { deletingBooking = null },
            title = { Text("تأكيد حذف الحجز", fontWeight = FontWeight.Bold, color = Color.Red) },
            text = { Text("هل أنت متأكد من رغبتك في حذف حجز (${b.serviceTitle}) للعميل (${b.clientName}) نهائياً؟") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteBooking(b)
                        deletingBooking = null
                        Toast.makeText(context, "تم حذف الحجز", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("حذف")
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingBooking = null }) { Text("إلغاء") }
            }
        )
    }
}

@Composable
private fun AdminStatusBadge(status: String) {
    val (bgColor, textColor) = when (status) {
        "تم التأكيد", "مكتمل", "تم الدفع", "صدرت التأشيرة" -> Color(0xFFD1FAE5) to Color(0xFF065F46)
        "جديد", "قيد المراجعة", "تم استلام الطلب", "تم رفع الجوازات" -> ShajeenSkyContainer to ShajeenSkyBlue
        "ملغي", "مرفوض" -> Color(0xFFFFE4E6) to Color(0xFF9F1239)
        else -> Color(0xFFFEF3C7) to Color(0xFF92400E)
    }

    Surface(shape = RoundedCornerShape(8.dp), color = bgColor) {
        Text(
            text = status,
            color = textColor,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.5.sp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

// ==========================================
// 4. SERVICES & SPECIAL OFFERS TAB
// ==========================================
@Composable
private fun AdminServicesTab(
    services: List<TravelServiceEntity>,
    onSaveService: (TravelServiceEntity) -> Unit,
    onDeleteService: (TravelServiceEntity) -> Unit,
    onToggleVisibility: (TravelServiceEntity) -> Unit,
    onToggleOffer: (TravelServiceEntity) -> Unit
) {
    val context = LocalContext.current
    var editingService by remember { mutableStateOf<TravelServiceEntity?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var deletingService by remember { mutableStateOf<TravelServiceEntity?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "قائمة الخدمات والرحلات والعروض (${services.size})",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
            )

            Button(
                onClick = {
                    editingService = TravelServiceEntity(
                        title = "",
                        category = "خدمات السفر والسياحة",
                        description = "",
                        price = "حسب الوجهة",
                        subtitle = "",
                        iconType = "flight",
                        badge = "",
                        isFeatured = true,
                        isVisible = true,
                        isOffer = false
                    )
                    showAddDialog = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("إضافة خدمة", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(services, key = { it.id }) { service ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
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
                                    shape = RoundedCornerShape(10.dp),
                                    color = ShajeenSkyContainer,
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(getServiceIcon(service.iconType), contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.size(18.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = service.title,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                                    )
                                    Text(
                                        text = "${service.category} • ${service.price}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText, fontSize = 11.5.sp)
                                    )
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                if (service.isOffer) {
                                    Surface(shape = RoundedCornerShape(6.dp), color = ShajeenGold.copy(alpha = 0.2f)) {
                                        Text("عرض خاص", color = ShajeenDarkBlue, fontWeight = FontWeight.Bold, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                    }
                                }
                                Surface(shape = RoundedCornerShape(6.dp), color = if (service.isVisible) Color(0xFFD1FAE5) else Color(0xFFFFE4E6)) {
                                    Text(if (service.isVisible) "ظاهر" else "مخفي", color = if (service.isVisible) Color(0xFF065F46) else Color(0xFF9F1239), fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                        }

                        if (service.description.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = service.description,
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontSize = 11.sp),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                // Toggle Visibility
                                OutlinedButton(
                                    onClick = { onToggleVisibility(service) },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Icon(if (service.isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (service.isVisible) "إخفاء" else "إظهار", fontSize = 10.sp)
                                }

                                // Toggle Offer
                                OutlinedButton(
                                    onClick = { onToggleOffer(service) },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = ShajeenGold, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (service.isOffer) "إلغاء العرض" else "جعله عرضاً", fontSize = 10.sp)
                                }
                            }

                            Row {
                                TextButton(onClick = {
                                    editingService = service
                                    showAddDialog = true
                                }) {
                                    Text("تعديل", color = ShajeenSkyBlue, fontSize = 12.sp)
                                }
                                TextButton(onClick = { deletingService = service }) {
                                    Text("حذف", color = Color.Gray, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Add / Edit Service Dialog
    if (showAddDialog && editingService != null) {
        val s = editingService!!
        var title by remember { mutableStateOf(s.title) }
        var category by remember { mutableStateOf(s.category) }
        var price by remember { mutableStateOf(s.price) }
        var subtitle by remember { mutableStateOf(s.subtitle) }
        var description by remember { mutableStateOf(s.description) }
        var badge by remember { mutableStateOf(s.badge) }
        var iconType by remember { mutableStateOf(s.iconType) }
        var isOffer by remember { mutableStateOf(s.isOffer) }
        var isVisible by remember { mutableStateOf(s.isVisible) }

        val categories = listOf("خدمات السفر والسياحة", "الحج والعمرة", "حجوزات النقل", "البرامج السياحية", "عروض خاصة")

        Dialog(onDismissRequest = { showAddDialog = false }) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(18.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(if (s.id == 0L) "إضافة خدمة أو عرض جديد" else "تعديل الخدمة", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان الخدمة *") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("القسم / الفئة *") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("السعر أو التكلفة *") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = subtitle, onValueChange = { subtitle = it }, label = { Text("وصف فرعي مختصر") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = badge, onValueChange = { badge = it }, label = { Text("شارة مميزة (مثال: الأكثر طلباً)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("التفاصيل والوصف الكامل") }, minLines = 2, modifier = Modifier.fillMaxWidth())

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isOffer, onCheckedChange = { isOffer = it })
                        Text("تمييز كعرض خاص في الصفحة الرئيسية", fontSize = 12.sp)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isVisible, onCheckedChange = { isVisible = it })
                        Text("ظهور الخدمة للمستخدمين في التطبيق", fontSize = 12.sp)
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { showAddDialog = false }) { Text("إلغاء") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (title.isNotBlank()) {
                                    val toSave = s.copy(
                                        title = title.trim(),
                                        category = category.trim(),
                                        price = price.trim(),
                                        subtitle = subtitle.trim(),
                                        description = description.trim(),
                                        badge = badge.trim(),
                                        isOffer = isOffer,
                                        isVisible = isVisible
                                    )
                                    onSaveService(toSave)
                                    showAddDialog = false
                                    Toast.makeText(context, "تم حفظ الخدمة بنجاح", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue)
                        ) {
                            Text("حفظ الخدمة")
                        }
                    }
                }
            }
        }
    }

    // Delete Service Dialog
    deletingService?.let { s ->
        AlertDialog(
            onDismissRequest = { deletingService = null },
            title = { Text("حذف الخدمة", color = Color.Red, fontWeight = FontWeight.Bold) },
            text = { Text("هل أنت متأكد من حذف خدمة (${s.title}) نهائياً؟") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteService(s)
                        deletingService = null
                        Toast.makeText(context, "تم حذف الخدمة", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) { Text("حذف") }
            },
            dismissButton = {
                TextButton(onClick = { deletingService = null }) { Text("إلغاء") }
            }
        )
    }
}

// ==========================================
// 5. NEWS & BROADCAST TAB
// ==========================================
@Composable
private fun AdminNewsTab(
    newsList: List<AgencyNewsEntity>,
    announcement: String,
    showAnnouncement: Boolean,
    onAddNews: (String, String, String, String) -> Unit,
    onUpdateNews: (AgencyNewsEntity) -> Unit,
    onDeleteNews: (AgencyNewsEntity) -> Unit,
    onSaveAnnouncement: (String, Boolean) -> Unit
) {
    val context = LocalContext.current
    var currentAnnText by remember { mutableStateOf(announcement) }
    var currentAnnShow by remember { mutableStateOf(showAnnouncement) }

    var showNewsDialog by remember { mutableStateOf(false) }
    var newsTitle by remember { mutableStateOf("") }
    var newsContent by remember { mutableStateOf("") }
    var newsTag by remember { mutableStateOf("إعلان رسمي") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Broadcast Announcement Banner Setting
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "شريط الإعلان المباشر في الصفحة الرئيسية",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                        )
                        Switch(
                            checked = currentAnnShow,
                            onCheckedChange = { currentAnnShow = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = ShajeenSkyBlue, checkedTrackColor = ShajeenSkyContainer)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = currentAnnText,
                        onValueChange = { currentAnnText = it },
                        label = { Text("نص الإعلان البارز في أعلى الشاشة الرئيسية") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            onSaveAnnouncement(currentAnnText.trim(), currentAnnShow)
                            Toast.makeText(context, "تم تحديث شريط الإعلان المباشر", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenDarkBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("حفظ ونشر الإعلان المباشر")
                    }
                }
            }
        }

        // News Articles Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "الأخبار والمستجدات الرسمية (${newsList.size})",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                )

                Button(
                    onClick = { showNewsDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("إضافة خبر", fontSize = 12.sp)
                }
            }
        }

        if (newsList.isEmpty()) {
            item {
                Text("لا توجد أخبار مضافة حتى الآن", color = ShajeenSecondaryText, modifier = Modifier.padding(16.dp))
            }
        } else {
            items(newsList, key = { it.id }) { news ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(news.title, fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                            Surface(shape = RoundedCornerShape(6.dp), color = ShajeenSkyContainer) {
                                Text(news.tag, color = ShajeenSkyBlue, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(news.content, style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray))
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(news.dateText, fontSize = 10.sp, color = Color.Gray)
                            IconButton(onClick = { onDeleteNews(news) }, modifier = Modifier.size(28.dp)) {
                                Icon(Icons.Default.Delete, contentDescription = "حذف", tint = Color.Gray, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    // Add News Dialog
    if (showNewsDialog) {
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        AlertDialog(
            onDismissRequest = { showNewsDialog = false },
            title = { Text("إضافة خبر أو إعلان جديد", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = newsTitle, onValueChange = { newsTitle = it }, label = { Text("عنوان الخبر *") }, singleLine = true)
                    OutlinedTextField(value = newsTag, onValueChange = { newsTag = it }, label = { Text("تصنيف الإعلان") }, singleLine = true)
                    OutlinedTextField(value = newsContent, onValueChange = { newsContent = it }, label = { Text("تفاصيل الخبر *") }, minLines = 3)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newsTitle.isNotBlank()) {
                            onAddNews(newsTitle.trim(), newsContent.trim(), sdf.format(Date()), newsTag.trim())
                            newsTitle = ""
                            newsContent = ""
                            showNewsDialog = false
                            Toast.makeText(context, "تم إضافة الخبر ونشره", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue)
                ) { Text("نشر الخبر") }
            },
            dismissButton = {
                TextButton(onClick = { showNewsDialog = false }) { Text("إلغاء") }
            }
        )
    }
}

// ==========================================
// 6. REAL PUSH NOTIFICATIONS TAB
// ==========================================
@Composable
private fun AdminNotificationsTab(
    clients: List<ClientEntity>,
    notifications: List<ClientNotificationEntity>,
    onSendNotification: (String, String, Long?) -> Unit,
    onDeleteNotification: (Long) -> Unit
) {
    val context = LocalContext.current
    var notifTitle by remember { mutableStateOf("") }
    var notifMessage by remember { mutableStateOf("") }
    var selectedTargetUserId by remember { mutableStateOf<Long?>(null) } // null = All Users
    var targetDropdownExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Send Push Notification Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = ShajeenSkyContainer, modifier = Modifier.size(34.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Notifications, contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.size(18.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("إرسال إشعار فوري (Push Notification)", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                            Text("يصل الإشعار إلى هواتف المستخدمين كإشعار نظام حقيقي", fontSize = 11.sp, color = ShajeenSecondaryText)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Target recipient selector
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = if (selectedTargetUserId == null) "جميع المستخدمين المسجلين (عام)" else {
                                val c = clients.firstOrNull { it.id == selectedTargetUserId }
                                "${c?.fullName ?: "مستخدم"} (${c?.phone ?: ""})"
                            },
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("المستلمون") },
                            trailingIcon = {
                                IconButton(onClick = { targetDropdownExpanded = !targetDropdownExpanded }) {
                                    Icon(Icons.Default.People, contentDescription = null, tint = ShajeenSkyBlue)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { targetDropdownExpanded = true }
                        )

                        androidx.compose.material3.DropdownMenu(
                            expanded = targetDropdownExpanded,
                            onDismissRequest = { targetDropdownExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("جميع المستخدمين (إرسال عام للكل)", fontWeight = FontWeight.Bold) },
                                onClick = {
                                    selectedTargetUserId = null
                                    targetDropdownExpanded = false
                                }
                            )
                            clients.forEach { c ->
                                DropdownMenuItem(
                                    text = { Text("${c.fullName} - ${c.phone}") },
                                    onClick = {
                                        selectedTargetUserId = c.id
                                        targetDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = notifTitle,
                        onValueChange = { notifTitle = it },
                        label = { Text("عنوان الإشعار (مثال: عرض خاص جديد من وكالة شجين)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = notifMessage,
                        onValueChange = { notifMessage = it },
                        label = { Text("نص الإشعار والتفاصيل") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            if (notifTitle.isNotBlank() && notifMessage.isNotBlank()) {
                                onSendNotification(notifTitle.trim(), notifMessage.trim(), selectedTargetUserId)
                                notifTitle = ""
                                notifMessage = ""
                                Toast.makeText(context, "تم إرسال الإشعار الفوري بنجاح إلى الأجهزة!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "يرجى كتابة عنوان ونص الإشعار", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("إرسال الإشعار الفوري الآن", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Sent Notifications History
        item {
            Text(
                text = "سجل الإشعارات المرسلة داخل التطبيق (${notifications.size})",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
            )
        }

        if (notifications.isEmpty()) {
            item {
                Text("لا توجد إشعارات مسجلة في السجل", color = ShajeenSecondaryText, modifier = Modifier.padding(16.dp))
            }
        } else {
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
            items(notifications, key = { it.id }) { notif ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(notif.title, fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                            Text(notif.message, style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(sdf.format(Date(notif.timestamp)), fontSize = 10.sp, color = Color.Gray)
                        }

                        IconButton(onClick = { onDeleteNotification(notif.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "حذف الإشعار", tint = Color.Gray, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 7. PAYMENT WALLETS TAB
// ==========================================
@Composable
private fun AdminWalletsTab(
    wallets: List<PaymentWalletEntity>,
    onSaveWallet: (PaymentWalletEntity) -> Unit,
    onDeleteWallet: (PaymentWalletEntity) -> Unit
) {
    val context = LocalContext.current
    var editingWallet by remember { mutableStateOf<PaymentWalletEntity?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "المحافظ الإلكترونية ووسائل الدفع (${wallets.size})",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
            )

            Button(
                onClick = {
                    editingWallet = PaymentWalletEntity(walletName = "", accountNumber = "770038009", accountHolder = "وكالة شجين للسفريات والسياحة", instructions = "")
                    showDialog = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("إضافة محفظة", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(wallets, key = { it.id }) { wallet ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(wallet.walletName, fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                            Surface(shape = RoundedCornerShape(6.dp), color = if (wallet.isActive) Color(0xFFD1FAE5) else Color(0xFFFFE4E6)) {
                                Text(if (wallet.isActive) "مفعلة" else "معطلة", color = if (wallet.isActive) Color(0xFF065F46) else Color(0xFF9F1239), fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text("رقم الحساب: ${wallet.accountNumber}", fontWeight = FontWeight.Bold, color = ShajeenSkyBlue)
                        Text("اسم صاحب الحساب: ${wallet.accountHolder}", style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray))

                        if (wallet.instructions.isNotBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("التعليمات: ${wallet.instructions}", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontSize = 11.sp))
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TextButton(onClick = {
                                val toggled = wallet.copy(isActive = !wallet.isActive)
                                onSaveWallet(toggled)
                            }) {
                                Text(if (wallet.isActive) "تعطيل" else "تفعيل", color = if (wallet.isActive) Color.Red else Color(0xFF10B981), fontSize = 11.sp)
                            }
                            TextButton(onClick = {
                                editingWallet = wallet
                                showDialog = true
                            }) {
                                Text("تعديل", color = ShajeenSkyBlue, fontSize = 11.sp)
                            }
                            TextButton(onClick = { onDeleteWallet(wallet) }) {
                                Text("حذف", color = Color.Gray, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog && editingWallet != null) {
        val w = editingWallet!!
        var name by remember { mutableStateOf(w.walletName) }
        var acc by remember { mutableStateOf(w.accountNumber) }
        var holder by remember { mutableStateOf(w.accountHolder) }
        var inst by remember { mutableStateOf(w.instructions) }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (w.id == 0L) "إضافة محفظة دفع" else "تعديل المحفظة", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("اسم المحفظة (مثال: محفظة جيب)") }, singleLine = true)
                    OutlinedTextField(value = acc, onValueChange = { acc = it }, label = { Text("رقم الحساب (مثال: 770038009)") }, singleLine = true)
                    OutlinedTextField(value = holder, onValueChange = { holder = it }, label = { Text("اسم صاحب الحساب") }, singleLine = true)
                    OutlinedTextField(value = inst, onValueChange = { inst = it }, label = { Text("تعليمات التحويل") }, minLines = 2)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (name.isNotBlank() && acc.isNotBlank()) {
                            onSaveWallet(w.copy(walletName = name.trim(), accountNumber = acc.trim(), accountHolder = holder.trim(), instructions = inst.trim()))
                            showDialog = false
                            Toast.makeText(context, "تم حفظ المحفظة", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue)
                ) { Text("حفظ") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("إلغاء") }
            }
        )
    }
}

// ==========================================
// 8. AGENCY SETTINGS & CONTACTS TAB
// ==========================================
@Composable
private fun AdminAgencySettingsTab(
    settings: AgencySettingsEntity?,
    onSaveSettings: (
        String, String, String, String, String, String, String, String, String, String, String, String, String, Boolean, Boolean
    ) -> Unit
) {
    val context = LocalContext.current
    val s = settings ?: AgencySettingsEntity()

    var agencyName by remember { mutableStateOf(s.agencyName) }
    var address by remember { mutableStateOf(s.address) }
    var phone1 by remember { mutableStateOf(s.phone1) }
    var phone2 by remember { mutableStateOf(s.phone2) }
    var phone3 by remember { mutableStateOf(s.phone3) }
    var phone4 by remember { mutableStateOf(s.phone4) }
    var whatsapp by remember { mutableStateOf(s.whatsappNumber) }
    var email by remember { mutableStateOf(s.email) }
    var hours by remember { mutableStateOf(s.workingHours) }
    var social by remember { mutableStateOf(s.socialLinks) }
    var about by remember { mutableStateOf(s.aboutDescription) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("بيانات الوكالة والتواصل الرسمية", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                    OutlinedTextField(value = agencyName, onValueChange = { agencyName = it }, label = { Text("اسم الوكالة الرسمي") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("العنوان الرئيسي بصنعاء") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = phone1, onValueChange = { phone1 = it }, label = { Text("هاتف الحجوزات 1") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = phone2, onValueChange = { phone2 = it }, label = { Text("هاتف الحجوزات 2 (السعودية)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = phone3, onValueChange = { phone3 = it }, label = { Text("هاتف الحجوزات 3") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = phone4, onValueChange = { phone4 = it }, label = { Text("هاتف الإدارة والمحافظ 4") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = whatsapp, onValueChange = { whatsapp = it }, label = { Text("رقم الواتساب الرسمي") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("البريد الإلكتروني") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = hours, onValueChange = { hours = it }, label = { Text("ساعات وأيام العمل") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = social, onValueChange = { social = it }, label = { Text("روابط التواصل الاجتماعي") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = about, onValueChange = { about = it }, label = { Text("نبذة عن الوكالة ورسالتها") }, minLines = 2, modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            onSaveSettings(
                                agencyName.trim(), address.trim(), phone1.trim(), phone2.trim(), phone3.trim(), phone4.trim(),
                                s.announcement, whatsapp.trim(), email.trim(), hours.trim(), social.trim(), about.trim(),
                                s.homeTagline, s.showAnnouncement, s.maintenanceMode
                            )
                            Toast.makeText(context, "تم حفظ بيانات الوكالة بنجاح", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("حفظ التحديثات الرسمية", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ==========================================
// 9. HOME CONTENT & CONTROL TAB
// ==========================================
@Composable
private fun AdminHomeContentTab(
    settings: AgencySettingsEntity?,
    onSaveSettings: (
        String, String, String, String, String, String, String, String, String, String, String, String, String, Boolean, Boolean
    ) -> Unit
) {
    val context = LocalContext.current
    val s = settings ?: AgencySettingsEntity()

    var tagline by remember { mutableStateOf(s.homeTagline) }
    var announcement by remember { mutableStateOf(s.announcement) }
    var showAnnounce by remember { mutableStateOf(s.showAnnouncement) }
    var maintenance by remember { mutableStateOf(s.maintenanceMode) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("إدارة محتوى وعناصر الصفحة الرئيسية", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                    OutlinedTextField(
                        value = tagline,
                        onValueChange = { tagline = it },
                        label = { Text("العنوان الترحيبي البارز في أعلى الرئيسية") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = announcement,
                        onValueChange = { announcement = it },
                        label = { Text("نص شريط الإعلانات العاجلة") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("إظهار شريط الإعلانات في الصفحة الرئيسية")
                        Switch(checked = showAnnounce, onCheckedChange = { showAnnounce = it })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("وضع الصيانة المؤقت")
                            Text("عرض تنبيه للمستخدمين بأن بعض الخدمات تخضع للتحديث", fontSize = 11.sp, color = Color.Gray)
                        }
                        Switch(checked = maintenance, onCheckedChange = { maintenance = it })
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            onSaveSettings(
                                s.agencyName, s.address, s.phone1, s.phone2, s.phone3, s.phone4,
                                announcement.trim(), s.whatsappNumber, s.email, s.workingHours, s.socialLinks, s.aboutDescription,
                                tagline.trim(), showAnnounce, maintenance
                            )
                            Toast.makeText(context, "تم حفظ إعدادات الصفحة الرئيسية", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("حفظ ونشر التعديلات", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ==========================================
// 10. SECURITY, AUDIT LOG & CHANGE PASSWORD TAB
// ==========================================
@Composable
private fun AdminSecurityTab(
    adminLogs: List<AdminLogEntity>,
    onChangePassword: (String, String, (Boolean, String) -> Unit) -> Unit,
    onClearLogs: () -> Unit,
    totalClients: Int,
    totalBookings: Int,
    totalElectronic: Int,
    totalServices: Int,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    var oldPin by remember { mutableStateOf("") }
    var newPin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var changeError by remember { mutableStateOf<String?>(null) }
    var changeSuccess by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Change Owner Password Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = ShajeenGold.copy(alpha = 0.2f), modifier = Modifier.size(34.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ShajeenGold, modifier = Modifier.size(18.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("تغيير كلمة مرور المالك المشفرة", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                            Text("يتم تشفير كلمة المرور بتقنية SHA-256 وحمايتها من الاختراق", fontSize = 11.sp, color = ShajeenSecondaryText)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = oldPin,
                        onValueChange = {
                            oldPin = it
                            changeError = null
                        },
                        label = { Text("كلمة المرور الحالية") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newPin,
                        onValueChange = {
                            newPin = it
                            changeError = null
                        },
                        label = { Text("كلمة المرور الجديدة (4 خانات فأكثر)") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = confirmPin,
                        onValueChange = {
                            confirmPin = it
                            changeError = null
                        },
                        label = { Text("تأكيد كلمة المرور الجديدة") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    changeError?.let {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(it, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    changeSuccess?.let {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(it, color = Color(0xFF10B981), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (newPin != confirmPin) {
                                changeError = "كلمتا المرور غير متطابقتين"
                                return@Button
                            }
                            if (newPin.trim().length < 4) {
                                changeError = "كلمة المرور يجب أن لا تقل عن 4 خانات"
                                return@Button
                            }
                            onChangePassword(oldPin.trim(), newPin.trim()) { ok, msg ->
                                if (ok) {
                                    changeSuccess = msg
                                    changeError = null
                                    oldPin = ""
                                    newPin = ""
                                    confirmPin = ""
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                } else {
                                    changeError = msg
                                    changeSuccess = null
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenGold, contentColor = ShajeenDarkBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("تغيير كلمة المرور وحفظها مشفرة", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Data Backup & Integrity Summary
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("ملخص قاعدة البيانات والنسخ الاحتياطي", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• العملاء المسجلون: $totalClients عميل", fontSize = 12.sp, color = Color.DarkGray)
                    Text("• حجوزات الخدمات: $totalBookings حجز", fontSize = 12.sp, color = Color.DarkGray)
                    Text("• معاملات التأشيرات: $totalElectronic معاملة", fontSize = 12.sp, color = Color.DarkGray)
                    Text("• الخدمات المسجلة: $totalServices خدمة", fontSize = 12.sp, color = Color.DarkGray)
                    Text("• سجل العمليات: ${adminLogs.size} عملية مسجلة", fontSize = 12.sp, color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("جميع البيانات محفوظة في قاعدة بيانات محلية مشفرة وتتم مزامنتها تلقائياً مع التطبيق.", fontSize = 11.sp, color = ShajeenSecondaryText)
                }
            }
        }

        // Admin Activity Audit Log
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("سجل تدقيق العمليات (Audit Log)", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                        if (adminLogs.isNotEmpty()) {
                            TextButton(onClick = onClearLogs) {
                                Text("مسح السجل", color = Color.Gray, fontSize = 11.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
                    adminLogs.take(20).forEach { log ->
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(log.actionType, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = ShajeenDarkBlue)
                                Text(sdf.format(Date(log.timestamp)), fontSize = 10.sp, color = Color.Gray)
                            }
                            Text(log.details, fontSize = 11.sp, color = ShajeenSecondaryText)
                            if (log.targetUser.isNotBlank()) {
                                Text("المستهدف: ${log.targetUser}", fontSize = 10.sp, color = ShajeenSkyBlue)
                            }
                        }
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                    }
                }
            }
        }

        // Logout
        item {
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("تسجيل خروج من لوحة تحكم المالك", fontWeight = FontWeight.Bold)
            }
        }
    }
}
