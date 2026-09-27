package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.AgencyNewsEntity
import com.example.data.entity.AgencySettingsEntity
import com.example.data.entity.ClientEntity
import com.example.data.entity.TravelServiceEntity
import com.example.ui.components.AgencyContactFooter
import com.example.ui.components.AgencyOwnerPortalCard
import com.example.ui.components.BookingDialog
import com.example.ui.components.CoreServiceType
import com.example.ui.components.ServiceCard
import com.example.ui.components.ServiceDetailsDialog
import com.example.ui.components.ServiceInteractiveFlowDialog
import com.example.ui.components.ServicesGridSection
import com.example.ui.components.ShajeenHeroBannerCard
import com.example.ui.components.ShajeenReferenceTopBar
import com.example.ui.components.TravelInputForm
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenHomeBgEnd
import com.example.ui.theme.ShajeenHomeBgStart
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue

@Composable
fun DashboardScreen(
    currentClient: ClientEntity?,
    agencySettings: AgencySettingsEntity?,
    newsList: List<AgencyNewsEntity>,
    services: List<TravelServiceEntity>,
    selectedCategory: String,
    searchQuery: String,
    onCategorySelected: (String) -> Unit,
    onSearchChanged: (String) -> Unit,
    onBookService: (service: TravelServiceEntity, date: String, passengers: Int, notes: String) -> Unit,
    onServiceRequestSubmitted: (
        serviceTitle: String,
        serviceCategory: String,
        clientName: String,
        clientPhone: String,
        travelDate: String,
        passengersCount: Int,
        details: String
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    onNavigateToElectronicBooking: () -> Unit = {},
    onOwnerLoginClick: () -> Unit = {},
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val categories = listOf(
        "الكل",
        "طيران",
        "جوازات",
        "تأشيرات",
        "فنادق",
        "سياحة",
        "حج وعمرة",
        "نقل وسيارات",
        "رجال أعمال",
        "شحن وطرود"
    )

    // Interactive service flow modal state
    var activeServiceFlow by remember { mutableStateOf<CoreServiceType?>(null) }
    var submittedRequestCode by remember { mutableStateOf<String?>(null) }
    var showAllServicesSection by remember { mutableStateOf(false) }

    var selectedServiceForBooking by remember { mutableStateOf<TravelServiceEntity?>(null) }
    var selectedServiceForDetails by remember { mutableStateOf<TravelServiceEntity?>(null) }

    fun dialPhone(num: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${num.replace(" ", "")}"))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "الاتصال بالرقم: $num", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsapp(num: String) {
        try {
            val clean = num.replace("+", "").replace(" ", "")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$clean"))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "فتح الواتساب: $num", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendEmail(email: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "البريد الإلكتروني: $email", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(ShajeenHomeBgStart, ShajeenHomeBgEnd)
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 95.dp)
        ) {
            // 1. Top Reference Header (Hamburger, Logo, Search, Headphones, Notifications)
            item {
                ShajeenReferenceTopBar(
                    onMenuClick = { showAllServicesSection = !showAllServicesSection },
                    onSearchClick = { showAllServicesSection = true },
                    onSupportClick = { dialPhone(agencySettings?.phone1 ?: "770038009") },
                    onNotificationsClick = {
                        Toast.makeText(context, "مركز الإشعارات والتنبيهات", Toast.LENGTH_SHORT).show()
                    },
                    hasUnreadNotifications = true
                )
            }

            // 2. Luxury Hero Banner matching reference image (Burj Khalifa, Aircraft, Kaaba, Slogans)
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    ShajeenHeroBannerCard(
                        onDiscoverClick = {
                            activeServiceFlow = CoreServiceType.TOURS
                        }
                    )
                }
            }

            // 3. Agency Owner Portal Quick Card ("بوابة مالك الوكالة")
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                    AgencyOwnerPortalCard(
                        onOwnerLoginClick = onOwnerLoginClick
                    )
                }
            }

            // 4. "خدماتنا" Grid Section (4-column Grid with all 12 Services + Pill Button + Contact Card)
            item {
                ServicesGridSection(
                    currentClient = currentClient,
                    onOpenServiceFlow = { serviceType ->
                        activeServiceFlow = serviceType
                    },
                    onViewAllServices = {
                        showAllServicesSection = !showAllServicesSection
                    },
                    onDialPhone = { dialPhone(it) },
                    onOpenWhatsapp = { openWhatsapp(it) },
                    onSendEmail = { sendEmail(it) }
                )
            }

            // 5. Expandable / Searchable All Services Catalog
            if (showAllServicesSection || searchQuery.isNotBlank()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 6.dp)
                    ) {
                        // Search Bar
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = onSearchChanged,
                            placeholder = {
                                Text(
                                    "ابحث في خدمات ورحلات شجين...",
                                    color = ShajeenSecondaryText,
                                    fontSize = 13.sp
                                )
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = "بحث", tint = ShajeenSkyBlue)
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { onSearchChanged("") }) {
                                        Icon(Icons.Default.Clear, contentDescription = "مسح", tint = ShajeenSecondaryText)
                                    }
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = ShajeenHeadingText,
                                unfocusedTextColor = ShajeenHeadingText
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .testTag("dashboard_search_input"),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Category Filter Chips
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            categories.forEach { category ->
                                val isSelected = (selectedCategory == category)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onCategorySelected(category) },
                                    label = {
                                        Text(
                                            text = category,
                                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (isSelected) Color.White else ShajeenHeadingText
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = ShajeenSkyBlue,
                                        selectedLabelColor = Color.White,
                                        containerColor = Color.White,
                                        labelColor = ShajeenHeadingText
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = isSelected,
                                        borderColor = if (isSelected) ShajeenSkyBlue else Color(0xFFCBD5E1),
                                        selectedBorderColor = ShajeenSkyBlue,
                                        borderWidth = 1.dp
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.testTag("category_chip_$category")
                                )
                            }
                        }
                    }
                }

                // Services List
                if (services.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "لا توجد خدمات مطابقة لبحثك",
                                style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray)
                            )
                        }
                    }
                } else {
                    items(services, key = { it.id }) { service ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                            ServiceCard(
                                service = service,
                                onBookClick = { selectedServiceForBooking = it },
                                onDetailsClick = { selectedServiceForDetails = it }
                            )
                        }
                    }
                }
            }

            // 6. Featured Electronic Booking Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .clickable { onNavigateToElectronicBooking() }
                        .testTag("dashboard_electronic_booking_banner"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color(0xFFBAE6FD)),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = ShajeenSkyBlue.copy(alpha = 0.15f),
                                modifier = Modifier.size(46.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.FlightTakeoff,
                                        contentDescription = null,
                                        tint = ShajeenSkyBlue,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "بوابة الحجز الإلكتروني والتأشيرات",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ShajeenHeadingText,
                                        fontSize = 14.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "إصدار التأشيرات وسداد رسوم المحافظ إلكترونياً",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = ShajeenSecondaryText,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Button(
                            onClick = onNavigateToElectronicBooking,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("فتح", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // 7. Latest News & Updates section
            if (newsList.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TravelExplore,
                                contentDescription = null,
                                tint = ShajeenSkyBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "أحدث أخبار وعروض الوكالة",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ShajeenHeadingText
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        newsList.forEach { news ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = news.title,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = ShajeenDarkBlue
                                            )
                                        )
                                        Surface(
                                            color = ShajeenSkyBlue.copy(alpha = 0.12f),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = news.tag,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = ShajeenSkyBlue,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = news.content,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = ShajeenSecondaryText,
                                            lineHeight = 17.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = news.dateText,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.Gray,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 8. Bottom Agency Contact Footer
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                    AgencyContactFooter(settings = agencySettings)
                }
            }
        }

        // Active Service Interactive Flow Sheet
        activeServiceFlow?.let { serviceType ->
            ServiceInteractiveFlowDialog(
                serviceType = serviceType,
                currentClient = currentClient,
                onDismiss = { activeServiceFlow = null },
                onSubmitRequest = { title, category, name, phone, date, count, details ->
                    onServiceRequestSubmitted(title, category, name, phone, date, count, details)
                    submittedRequestCode = "SHJ-REQ-${(1000..9999).random()}"
                    activeServiceFlow = null
                }
            )
        }

        // Request Submission Success Confirmation Dialog
        submittedRequestCode?.let { code ->
            AlertDialog(
                onDismissRequest = { submittedRequestCode = null },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFDCFCE7),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF16A34A),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "تم استلام طلبك بنجاح",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShajeenHeadingText
                            )
                        )
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "شكراً لاختيارك وكالة شجين للسفريات والسياحة. تم تسجيل طلبك في النظام وإرساله إلى لوحة التحكم.",
                            style = MaterialTheme.typography.bodyMedium.copy(color = ShajeenHeadingText, lineHeight = 20.sp)
                        )
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF1F5F9),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("رقم الطلب / المرجع", fontSize = 11.sp, color = ShajeenSecondaryText)
                                Text(code, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = ShajeenSkyBlue)
                            }
                        }
                        Text(
                            text = "سيتواصل معك موظف الحجوزات لتأكيد كافة الترتيبات وإصدار التذاكر أو المعاملة.",
                            style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { submittedRequestCode = null },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("تم ومتابعة")
                    }
                }
            )
        }

        // Classic Booking Dialog
        selectedServiceForBooking?.let { service ->
            BookingDialog(
                service = service,
                currentClient = currentClient,
                onDismiss = { selectedServiceForBooking = null },
                onConfirm = { travelDate, passengersCount, notes ->
                    onBookService(service, travelDate, passengersCount, notes)
                    selectedServiceForBooking = null
                }
            )
        }

        // Classic Details Dialog
        selectedServiceForDetails?.let { service ->
            ServiceDetailsDialog(
                service = service,
                onDismiss = { selectedServiceForDetails = null },
                onBookClick = {
                    selectedServiceForBooking = service
                }
            )
        }
    }
}
