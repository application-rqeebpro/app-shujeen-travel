package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.entity.ClientEntity
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue

/**
 * Universal Dialog container that hosts the active service flow
 */
@Composable
fun ServiceInteractiveFlowDialog(
    serviceType: CoreServiceType,
    currentClient: ClientEntity?,
    onDismiss: () -> Unit,
    onSubmitRequest: (
        serviceTitle: String,
        serviceCategory: String,
        clientName: String,
        clientPhone: String,
        travelDate: String,
        passengersCount: Int,
        details: String
    ) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 28.dp),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color(0xFFF8FAFC)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header Bar
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            ServiceVisualIcon(serviceType = serviceType)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = serviceType.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ShajeenHeadingText,
                                        fontSize = 16.sp
                                    )
                                )
                                Text(
                                    text = serviceType.shortDesc,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = ShajeenSecondaryText,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF1F5F9))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "إغلاق",
                                tint = ShajeenHeadingText
                            )
                        }
                    }
                }

                // Body Flow
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    when (serviceType) {
                        CoreServiceType.FLIGHT -> FlightBookingFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.PASSPORT -> PassportServiceFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.VISA -> VisaApplicationFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.HOTEL -> HotelBookingFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.TOURS -> TourPackagesFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.HAJJ_UMRAH -> HajjUmrahFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.CAR_RENTAL -> CarRentalFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.INSURANCE -> TravelInsuranceFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.CARGO -> CargoShippingFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.TRANSLATION -> TranslationServiceFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.AIRPORT -> AirportMeetGreetFlow(currentClient, onSubmitRequest, onDismiss)
                        CoreServiceType.BUSINESS -> BusinessVipFlow(currentClient, onSubmitRequest, onDismiss)
                    }
                }
            }
        }
    }
}

// ========================================================
// 1. FLIGHT BOOKING FLOW
// ========================================================
data class SampleFlight(
    val airline: String,
    val flightNumber: String,
    val depTime: String,
    val arrTime: String,
    val duration: String,
    val stops: String,
    val price: String,
    val baggage: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlightBookingFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    var tripType by remember { mutableStateOf("ذهاب وعودة") } // ذهاب فقط / ذهاب وعودة
    var fromCity by remember { mutableStateOf("صنعاء (SAH)") }
    var toCity by remember { mutableStateOf("القاهرة (CAI)") }
    var depDate by remember { mutableStateOf("2026/10/15") }
    var retDate by remember { mutableStateOf("2026/10/25") }
    var passengersCount by remember { mutableIntStateOf(1) }
    var travelClass by remember { mutableStateOf("اقتصادية") }

    val airports = listOf("صنعاء (SAH)", "عدن (ADE)", "القاهرة (CAI)", "الرياض (RUH)", "جدة (JED)", "دبي (DXB)", "إسطنبول (IST)", "عمان (AMM)", "الدوحة (DOH)")
    var fromExpanded by remember { mutableStateOf(false) }
    var toExpanded by remember { mutableStateOf(false) }

    var searchedFlights by remember { mutableStateOf<List<SampleFlight>?>(null) }
    var clientName by remember { mutableStateOf(client?.fullName ?: "") }
    var clientPhone by remember { mutableStateOf(client?.phone ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Trip type selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("ذهاب وعودة", "ذهاب فقط").forEach { type ->
                val selected = tripType == type
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { tripType = type },
                    color = if (selected) ShajeenSkyBlue else Color.White,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (selected) ShajeenSkyBlue else Color(0xFFE2E8F0))
                ) {
                    Text(
                        text = type,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (selected) Color.White else ShajeenHeadingText
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }
        }

        // Airport Pickers Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // From City
                ExposedDropdownMenuBox(
                    expanded = fromExpanded,
                    onExpandedChange = { fromExpanded = it }
                ) {
                    OutlinedTextField(
                        value = fromCity,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("مدينة / مطار المغادرة") },
                        leadingIcon = { Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = ShajeenSkyBlue) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fromExpanded) },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = ShajeenSkyBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    )
                    ExposedDropdownMenu(
                        expanded = fromExpanded,
                        onDismissRequest = { fromExpanded = false }
                    ) {
                        airports.forEach { airport ->
                            DropdownMenuItem(
                                text = { Text(airport) },
                                onClick = {
                                    fromCity = airport
                                    fromExpanded = false
                                }
                            )
                        }
                    }
                }

                // To City
                ExposedDropdownMenuBox(
                    expanded = toExpanded,
                    onExpandedChange = { toExpanded = it }
                ) {
                    OutlinedTextField(
                        value = toCity,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("مدينة / مطار الوصول") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = ShajeenSkyBlue) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = toExpanded) },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = ShajeenSkyBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    )
                    ExposedDropdownMenu(
                        expanded = toExpanded,
                        onDismissRequest = { toExpanded = false }
                    ) {
                        airports.filter { it != fromCity }.forEach { airport ->
                            DropdownMenuItem(
                                text = { Text(airport) },
                                onClick = {
                                    toCity = airport
                                    toExpanded = false
                                }
                            )
                        }
                    }
                }

                // Dates
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = depDate,
                        onValueChange = { depDate = it },
                        label = { Text("تاريخ الذهاب") },
                        leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = ShajeenSkyBlue) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    if (tripType == "ذهاب وعودة") {
                        OutlinedTextField(
                            value = retDate,
                            onValueChange = { retDate = it },
                            label = { Text("تاريخ العودة") },
                            leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = ShajeenSkyBlue) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Passengers & Class
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = passengersCount.toString(),
                        onValueChange = { passengersCount = it.toIntOrNull() ?: 1 },
                        label = { Text("عدد المسافرين") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = travelClass,
                        onValueChange = { travelClass = it },
                        label = { Text("الدرجة") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = {
                        searchedFlights = listOf(
                            SampleFlight("طيران اليمنية", "IY 601", "08:30 ص", "11:45 ص", "3 س 15 د", "مباشر", "380$", "2 حقيبة x 23 كجم"),
                            SampleFlight("الخطوط السعودية", "SV 412", "02:15 م", "06:30 م", "4 س 15 د", "مباشر", "420$", "2 حقيبة x 23 كجم"),
                            SampleFlight("فلاي دبي", "FZ 124", "07:00 م", "11:30 م", "4 س 30 د", "ترانزيت 1", "340$", "1 حقيبة x 30 كجم")
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("البحث عن الرحلات المتاحة", fontWeight = FontWeight.Bold)
                }
            }
        }

        // Contact info input
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("بيانات التواصل لتأكيد الحجز", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("الاسم الكامل للمسافر الرئيسي") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = clientPhone,
                    onValueChange = { clientPhone = it },
                    label = { Text("رقم هاتف التواصل (واتساب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Search Results List
        searchedFlights?.let { flights ->
            Text(
                text = "الرحلات المتاحة ($fromCity ➔ $toCity):",
                fontWeight = FontWeight.Bold,
                color = ShajeenHeadingText,
                modifier = Modifier.padding(top = 4.dp)
            )

            flights.forEach { flight ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFBAE6FD)),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(flight.airline, fontWeight = FontWeight.Bold, color = ShajeenDarkBlue)
                            Text(flight.flightNumber, color = ShajeenSecondaryText, fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(flight.depTime, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = ShajeenHeadingText)
                                Text(fromCity.split(" ").firstOrNull() ?: "", fontSize = 11.sp, color = Color.Gray)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(flight.duration, fontSize = 11.sp, color = ShajeenSkyBlue, fontWeight = FontWeight.Bold)
                                Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.size(16.dp))
                                Text(flight.stops, fontSize = 10.sp, color = Color(0xFF10B981))
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(flight.arrTime, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = ShajeenHeadingText)
                                Text(toCity.split(" ").firstOrNull() ?: "", fontSize = 11.sp, color = Color.Gray)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFF1F5F9))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(flight.price, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = ShajeenSkyBlue)
                                Text(flight.baggage, fontSize = 10.sp, color = Color.Gray)
                            }

                            Button(
                                onClick = {
                                    val details = "مسار: $fromCity إلى $toCity | رحلة: ${flight.airline} (${flight.flightNumber}) | ذهاب: $depDate | نوع: $tripType | درجة: $travelClass | ركاب: $passengersCount"
                                    onSubmit("حجز طيران: ${flight.airline}", "طيران", clientName, clientPhone, depDate, passengersCount, details)
                                    onClose()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("طلب الحجز الآن", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ========================================================
// 2. PASSPORT SERVICE FLOW
// ========================================================
@Composable
private fun PassportServiceFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    var reqType by remember { mutableStateOf("إصدار جديد") } // إصدار جديد / تجديد
    var fullName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }
    var nationality by remember { mutableStateOf("يمني") }
    var birthDate by remember { mutableStateOf("") }
    var idNumber by remember { mutableStateOf("") }
    var oldPassportNum by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var idPhotoAttached by remember { mutableStateOf(false) }
    var personalPhotoAttached by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("نوع الطلب", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("إصدار جديد", "تجديد جواز سفر").forEach { type ->
                        val selected = reqType == type
                        FilterChip(
                            selected = selected,
                            onClick = { reqType = type },
                            label = { Text(type, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ShajeenSkyBlue,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("الاسم الكامل باللغة العربية والانجليزية") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف (واتساب للتواصل)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = nationality,
                        onValueChange = { nationality = it },
                        label = { Text("الجنسية") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = birthDate,
                        onValueChange = { birthDate = it },
                        label = { Text("تاريخ الميلاد") },
                        placeholder = { Text("يوم/شهر/سنة") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = idNumber,
                    onValueChange = { idNumber = it },
                    label = { Text("رقم البطاقة الشخصية / العائلية") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (reqType == "تجديد جواز سفر") {
                    OutlinedTextField(
                        value = oldPassportNum,
                        onValueChange = { oldPassportNum = it },
                        label = { Text("رقم الجواز السابق المنتهي") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Attachments Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("المستندات المطلوبة (رفع إلكتروني)", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                OutlinedButton(
                    onClick = { idPhotoAttached = !idPhotoAttached },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (idPhotoAttached) Icons.Default.CheckCircle else Icons.Default.UploadFile,
                        contentDescription = null,
                        tint = if (idPhotoAttached) Color(0xFF10B981) else ShajeenSkyBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (idPhotoAttached) "تم إرفاق صورة البطاقة الشخصية ✓" else "إرفاق صورة البطاقة الشخصية")
                }

                OutlinedButton(
                    onClick = { personalPhotoAttached = !personalPhotoAttached },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (personalPhotoAttached) Icons.Default.CheckCircle else Icons.Default.UploadFile,
                        contentDescription = null,
                        tint = if (personalPhotoAttached) Color(0xFF10B981) else ShajeenSkyBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (personalPhotoAttached) "تم إرفاق الصورة الشخصية بخلفية بيضاء ✓" else "إرفاق صورة شخصية رسمية (4x6)")
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("ملاحظات إضافية") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        }

        Button(
            onClick = {
                val details = "نوع الطلب: $reqType | الجنسية: $nationality | تاريخ الميلاد: $birthDate | رقم الهوية: $idNumber | الجواز القديم: $oldPassportNum | المرفقات: بطاقة: $idPhotoAttached، صورة: $personalPhotoAttached | ملاحظات: $notes"
                onSubmit("طلب جواز سفر ($reqType)", "جوازات", fullName, phone, "فوري", 1, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("إرسال طلب الجواز", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

// ========================================================
// 3. VISA APPLICATION FLOW
// ========================================================
@Composable
private fun VisaApplicationFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    var selectedVisaType by remember { mutableStateOf("تأشيرة عمل") }
    var applicantName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }
    var destinationCountry by remember { mutableStateOf("المملكة العربية السعودية") }
    var passportNumber by remember { mutableStateOf("") }
    var travelDate by remember { mutableStateOf("") }
    var hasPassportPhoto by remember { mutableStateOf(false) }
    var hasContractDoc by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf("") }

    val visaTypes = listOf("تأشيرة عمل", "تأشيرة سياحية", "تأشيرة زيارة عائلية", "تأشيرة زيارة شخصية", "تأشيرة عمرة", "أخرى")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("اختر نوع التأشيرة", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    visaTypes.chunked(2).forEach { row ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            row.forEach { type ->
                                val selected = selectedVisaType == type
                                FilterChip(
                                    selected = selected,
                                    onClick = { selectedVisaType = type },
                                    label = { Text(type, fontSize = 12.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = ShajeenSkyBlue,
                                        selectedLabelColor = Color.White
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                // Requirements box
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFE0F2FE),
                    border = BorderStroke(1.dp, Color(0xFF7DD3FC))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("متطلبات $selectedVisaType:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = ShajeenDarkBlue)
                        val reqs = when (selectedVisaType) {
                            "تأشيرة عمل" -> "1. جواز سفر ساري 6 أشهر\n2. فحص طبي معتمد\n3. مؤهل علمي وعقد عمل موثق\n4. تفويض التأشيرة الرسمية"
                            "تأشيرة سياحية" -> "1. صورة الجواز ساري المفعول\n2. حجز فندقي وتذكرة طيران\n3. صور شخصية بخلفية بيضاء"
                            "تأشيرة زيارة عائلية" -> "1. مستند التأشيرة من وزارة الخارجية\n2. إثبات صلة القرابة\n3. صور الجوازات"
                            else -> "1. صورة الجواز ساري المفعول\n2. الصور الشخصية والمستندات الداعمة"
                        }
                        Text(reqs, fontSize = 11.5.sp, color = ShajeenHeadingText, lineHeight = 16.sp)
                    }
                }

                OutlinedTextField(
                    value = applicantName,
                    onValueChange = { applicantName = it },
                    label = { Text("اسم صاحب التأشيرة كاملاً") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف (واتساب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = destinationCountry,
                    onValueChange = { destinationCountry = it },
                    label = { Text("الدولة وجهة التأشيرة") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = passportNumber,
                        onValueChange = { passportNumber = it },
                        label = { Text("رقم الجواز") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = travelDate,
                        onValueChange = { travelDate = it },
                        label = { Text("تاريخ السفر المتوقع") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedButton(
                    onClick = { hasPassportPhoto = !hasPassportPhoto },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (hasPassportPhoto) Icons.Default.CheckCircle else Icons.Default.UploadFile,
                        contentDescription = null,
                        tint = if (hasPassportPhoto) Color(0xFF10B981) else ShajeenSkyBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (hasPassportPhoto) "تم إرفاق صورة الجواز ✓" else "إرفاق صورة الجواز (الصفحة الأولى)")
                }

                OutlinedButton(
                    onClick = { hasContractDoc = !hasContractDoc },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (hasContractDoc) Icons.Default.CheckCircle else Icons.Default.UploadFile,
                        contentDescription = null,
                        tint = if (hasContractDoc) Color(0xFF10B981) else ShajeenSkyBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (hasContractDoc) "تم إرفاق المستندات الداعمة ✓" else "إرفاق المستندات الداعمة / التفويض")
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("ملاحظات خاصة بالتأشيرة") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        }

        Button(
            onClick = {
                val details = "نوع التأشيرة: $selectedVisaType | الوجهة: $destinationCountry | رقم الجواز: $passportNumber | السفر: $travelDate | جواز مرفق: $hasPassportPhoto | مستندات: $hasContractDoc | ملاحظات: $notes"
                onSubmit("طلب تأشيرة ($selectedVisaType)", "تأشيرات", applicantName, phone, travelDate, 1, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("إرسال طلب التأشيرة", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

// ========================================================
// 4. HOTEL BOOKING FLOW
// ========================================================
@Composable
private fun HotelBookingFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    var city by remember { mutableStateOf("مكة المكرمة") }
    var hotelName by remember { mutableStateOf("") }
    var checkIn by remember { mutableStateOf("2026/10/20") }
    var checkOut by remember { mutableStateOf("2026/10/25") }
    var guestsCount by remember { mutableIntStateOf(2) }
    var roomsCount by remember { mutableIntStateOf(1) }
    var roomType by remember { mutableStateOf("غرفة مزدوجة (إطلالة حرم)") }
    var clientName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }
    var notes by remember { mutableStateOf("") }

    val cities = listOf("مكة المكرمة", "المدينة المنورة", "القاهرة", "دبي", "الرياض", "صنعاء", "صلالة", "إسطنبول")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("بيانات حجز الفندق", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("المدينة / الوجهة") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = hotelName,
                    onValueChange = { hotelName = it },
                    label = { Text("اسم الفندق (إن وجد، أو اتركه للترشيح)") },
                    placeholder = { Text("مثال: فيرمونت مكة، سويس أوتيل، هيلتون...") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = checkIn,
                        onValueChange = { checkIn = it },
                        label = { Text("تاريخ الدخول") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = checkOut,
                        onValueChange = { checkOut = it },
                        label = { Text("تاريخ الخروج") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = guestsCount.toString(),
                        onValueChange = { guestsCount = it.toIntOrNull() ?: 1 },
                        label = { Text("عدد النزلاء") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = roomsCount.toString(),
                        onValueChange = { roomsCount = it.toIntOrNull() ?: 1 },
                        label = { Text("عدد الغرف") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = roomType,
                    onValueChange = { roomType = it },
                    label = { Text("نوع الغرفة / الإطلالة") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("اسم النزيل الرئيسي") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف (واتساب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("طلبات خاصة (شامل الإفطار، سرير إضافي...)") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        }

        Button(
            onClick = {
                val details = "فندق: ${hotelName.ifBlank { "ترشيح من الوكالة" }} في $city | دخول: $checkIn | خروج: $checkOut | نزلاء: $guestsCount | غرف: $roomsCount ($roomType) | ملاحظات: $notes"
                onSubmit("حجز فندق: $city", "فنادق", clientName, phone, checkIn, guestsCount, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.Hotel, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("طلب حجز الفندق", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

// ========================================================
// 5. TOUR PACKAGES FLOW
// ========================================================
data class TourProgram(
    val title: String,
    val destination: String,
    val duration: String,
    val price: String,
    val description: String,
    val itinerary: String,
    val included: String,
    val excluded: String
)

@Composable
private fun TourPackagesFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val tours = listOf(
        TourProgram(
            title = "رحلة جزيرة سقطرى الأسطورية",
            destination = "سقطرى - اليمن",
            duration = "5 أيام / 4 ليالي",
            price = "750$",
            description = "اكتشف أعجوبة الطبيعة وشجرة دم الأخوين الفريدة ومحمية ديطوح وشواطئ قلنسية الخلابة.",
            itinerary = "اليوم 1: الوصول إلى حديبو وجولة وادي عيره.\nاليوم 2: هضبة دكسم وغابات دم الأخوين.\nاليوم 3: لاغون ديطوح وشاطئ قلنسية ومغامرة القوارب مع الدلافين.\nاليوم 4: كهف هوق ومحمية روش البحرية للغوص.\nاليوم 5: التوديع والمغادرة.",
            included = "تذاكر الطيران، الإقامة والمخيمات الفاخرة، 3 وجبات يومياً، المواصلات بدفع رباعي، دليل سياحي مرخص",
            excluded = "المشتريات الشخصية، رسوم الغوص المتقدم"
        ),
        TourProgram(
            title = "موسم خريف صلالة الساحر",
            destination = "صلالة - سلطنة عمان",
            duration = "7 أيام / 6 ليالي",
            price = "580$",
            description = "طبيعة خضراء وأجواء باردة وشلالات مائية في وادي دربات وعين رزات وشاطئ المغسيل.",
            itinerary = "اليوم 1: الوصول والاستقبال في فندق 4 نجوم.\nاليوم 2: وادي دربات وجبل سمحان وشلالات إثوم.\nاليوم 3: عين صحلنوت وشاطئ المغسيل والنافورات الطبيعية.\nاليوم 4: سوق الحافة التراثي وبساتين الفواكه الاستوائية.\nاليوم 5: جولة حرة وتسوق.\nاليوم 6-7: التوديع والعودة.",
            included = "فندق 4 نجوم مع الإفطار، النقل السياحي المكيف، الجولات اليومية، رسوم المزارات",
            excluded = "تذاكر الطيران الدولية، وجبات الغداء والعشاء"
        ),
        TourProgram(
            title = "جولة معالم صنعاء التراثية",
            destination = "صنعاء القديمة وكوكبان ودار الحجر",
            duration = "3 أيام / 2 ليالي",
            price = "150$",
            description = "سياحة ثقافية وتاريخية عريقة في أزقة صنعاء وباب اليمن وقصر دار الحجر التاريخي.",
            itinerary = "اليوم 1: جولة في صنعاء القديمة، سوق الملح، وباب اليمن.\nاليوم 2: زيارة قصر دار الحجر بوادي ظهر وجبال شبام كوكبان.\nاليوم 3: وجبة تقليدية وتوديع.",
            included = "الإقامة، المواصلات الخاصة، وجبات تراثية، مرشد سياحي",
            excluded = "المصاريف الشخصية"
        )
    )

    var expandedTour by remember { mutableStateOf<TourProgram?>(null) }
    var travelersCount by remember { mutableIntStateOf(1) }
    var travelDate by remember { mutableStateOf("2026/11/01") }
    var clientName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Text("البرامج السياحية المتوفرة حالياً", fontWeight = FontWeight.Bold, color = ShajeenHeadingText, fontSize = 15.sp)
        }

        items(tours) { tour ->
            val isExpanded = expandedTour == tour
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, if (isExpanded) ShajeenSkyBlue else Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(tour.title, fontWeight = FontWeight.Bold, color = ShajeenDarkBlue, fontSize = 15.sp)
                            Text("${tour.destination} • ${tour.duration}", color = ShajeenSecondaryText, fontSize = 11.5.sp)
                        }
                        Text(tour.price, fontWeight = FontWeight.ExtraBold, color = ShajeenSkyBlue, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(tour.description, fontSize = 12.sp, color = ShajeenHeadingText, lineHeight = 17.sp)

                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Text("برنامج الرحلة التفصيلي:", fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = ShajeenDarkBlue)
                            Text(tour.itinerary, fontSize = 11.5.sp, color = ShajeenHeadingText, lineHeight = 16.sp)

                            Text("المشمولات:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF10B981))
                            Text(tour.included, fontSize = 11.sp, color = Color(0xFF0F766E))

                            Text("غير المشمول:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFFEF4444))
                            Text(tour.excluded, fontSize = 11.sp, color = Color(0xFF991B1B))

                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = clientName,
                                onValueChange = { clientName = it },
                                label = { Text("اسم الحاجز") },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = phone,
                                onValueChange = { phone = it },
                                label = { Text("رقم الهاتف (واتساب)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = travelDate,
                                    onValueChange = { travelDate = it },
                                    label = { Text("تاريخ السفر") },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = travelersCount.toString(),
                                    onValueChange = { travelersCount = it.toIntOrNull() ?: 1 },
                                    label = { Text("عدد الأفراد") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Button(
                                onClick = {
                                    val details = "برنامج: ${tour.title} (${tour.destination}) | مدة: ${tour.duration} | تاريخ: $travelDate | أفراد: $travelersCount | سعر: ${tour.price}"
                                    onSubmit("رحلة سياحية: ${tour.title}", "سياحة", clientName, phone, travelDate, travelersCount, details)
                                    onClose()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("احجز هذه الرحلة الآن", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = { expandedTour = if (isExpanded) null else tour },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(if (isExpanded) "إخفاء التفاصيل" else "عرض تفاصيل البرنامج والحجز ▾", color = ShajeenSkyBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ========================================================
// 6. HAJJ & UMRAH FLOW
// ========================================================
data class UmrahPackage(
    val title: String,
    val type: String,
    val hotel: String,
    val transport: String,
    val price: String,
    val duration: String,
    val features: String
)

@Composable
private fun HajjUmrahFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val packages = listOf(
        UmrahPackage("باقة العمرة الذهبية VIP (جواً)", "VIP", "فنادق 5 نجوم مطلة على الحرم (أبراج الساعة)", "طيران مباشر + استقبال خاص VIP", "750$", "10 أيام", "تأشيرة عمرة، تذكرة طيران، سكن راقي، مواصلات خاصة، تفويج وإرشاد ديني"),
        UmrahPackage("باقة العمرة الميسرة (براً)", "اقتصادي", "فنادق 4 نجوم قريبة مع باصات الحرم", "باصات مرسيدس VIP موديل حديث", "320$", "14 يوم", "تأشيرة، سكن، مواصلات دولية مريحة، مشرف مرافق"),
        UmrahPackage("باقة موسم العمرة لشهر رمضان", "رمضان", "فنادق في محبس الجن والعزيزية", "طيران أو باصات VIP", "550$", "عشر أواخر / شهر كامل", "برنامج معتمرين متكامل لروحانية الشهر الفضيل")
    )

    var selectedPkg by remember { mutableStateOf(packages[0]) }
    var pilgrimsCount by remember { mutableIntStateOf(1) }
    var startDate by remember { mutableStateOf("2026/11/10") }
    var clientName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }
    var notes by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("باقات الحج والعمرة المعتمدة", fontWeight = FontWeight.Bold, color = ShajeenHeadingText, fontSize = 15.sp)

        packages.forEach { pkg ->
            val isSelected = selectedPkg == pkg
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFF0F9FF) else Color.White),
                border = BorderStroke(1.5.dp, if (isSelected) ShajeenSkyBlue else Color(0xFFE2E8F0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedPkg = pkg }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(pkg.title, fontWeight = FontWeight.Bold, color = ShajeenDarkBlue)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ShajeenGold.copy(alpha = 0.2f)
                        ) {
                            Text(pkg.price, color = ShajeenHeadingText, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text("• الفندق: ${pkg.hotel}", fontSize = 11.5.sp, color = ShajeenHeadingText)
                    Text("• النقل: ${pkg.transport}", fontSize = 11.5.sp, color = ShajeenHeadingText)
                    Text("• المدة: ${pkg.duration}", fontSize = 11.5.sp, color = ShajeenSecondaryText)
                    Text("• المزايا: ${pkg.features}", fontSize = 11.sp, color = Color(0xFF0F766E), lineHeight = 15.sp)
                }
            }
        }

        // Reservation Form
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("بيانات حجز الباقة المختارة", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("اسم المعتمر / المفوض") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف (واتساب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text("تاريخ السفر المتوقع") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = pilgrimsCount.toString(),
                        onValueChange = { pilgrimsCount = it.toIntOrNull() ?: 1 },
                        label = { Text("عدد المعتمرين") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("ملاحظات خاصة (أعمار المعتمرين، غرفة خاصة...)") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        }

        Button(
            onClick = {
                val details = "باقة: ${selectedPkg.title} | فندق: ${selectedPkg.hotel} | نقل: ${selectedPkg.transport} | معتمرين: $pilgrimsCount | تاريخ: $startDate | سعر الباقة: ${selectedPkg.price} | ملاحظات: $notes"
                onSubmit("عمرة: ${selectedPkg.title}", "حج وعمرة", clientName, phone, startDate, pilgrimsCount, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.Mosque, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("طلب حجز باقة العمرة", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

// ========================================================
// 7. CAR RENTAL FLOW
// ========================================================
data class RentalCar(
    val model: String,
    val type: String,
    val price: String,
    val specs: String
)

@Composable
private fun CarRentalFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val cars = listOf(
        RentalCar("لكزس ES VIP موديل 2024", "سيدان فاخر", "120$ / يوم", "VIP، مقاعد جلد، تكييف مركزي، شاشات، مع سائق خاص محترف"),
        RentalCar("تويوتا لاندكروزر برادو V6", "دفع رباعي SUV", "100$ / يوم", "7 مقاعد، مناسبة للسفريات بين المحافظات والطرقات الوعرة"),
        RentalCar("باص تويوتا هايس حديث 2023", "فان عائلي 14 راكب", "80$ / مشوار", "سفريات ومجموعات عائلية مريحة ومكيفة"),
        RentalCar("تويوتا كامري / هيونداي النترا", "سيدان اقتصادي", "45$ / يوم", "مشاوير داخلية سريعة ومريحة")
    )

    var selectedCar by remember { mutableStateOf(cars[0]) }
    var pickupCity by remember { mutableStateOf("صنعاء") }
    var destinationCity by remember { mutableStateOf("عدن") }
    var pickupDate by remember { mutableStateOf("2026/10/22") }
    var daysCount by remember { mutableIntStateOf(1) }
    var withDriver by remember { mutableStateOf(true) }
    var clientName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }
    var notes by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("السيارات ومركبات النقل المتاحة", fontWeight = FontWeight.Bold, color = ShajeenHeadingText, fontSize = 15.sp)

        cars.forEach { car ->
            val isSelected = selectedCar == car
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFF0F9FF) else Color.White),
                border = BorderStroke(1.5.dp, if (isSelected) ShajeenSkyBlue else Color(0xFFE2E8F0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedCar = car }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = ShajeenSkyBlue,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(car.model, fontWeight = FontWeight.Bold, color = ShajeenDarkBlue)
                        Text(car.specs, fontSize = 11.5.sp, color = ShajeenSecondaryText)
                        Text(car.price, color = ShajeenSkyBlue, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // Reservation Details
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("تفاصيل الاستئجار والتوصيل", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = pickupCity,
                        onValueChange = { pickupCity = it },
                        label = { Text("مدينة الاستلام") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = destinationCity,
                        onValueChange = { destinationCity = it },
                        label = { Text("مدينة الوصول / المسار") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = pickupDate,
                        onValueChange = { pickupDate = it },
                        label = { Text("تاريخ الاستلام") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = daysCount.toString(),
                        onValueChange = { daysCount = it.toIntOrNull() ?: 1 },
                        label = { Text("المدة (أيام)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("استئجار مع سائق محترف", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    androidx.compose.material3.Switch(checked = withDriver, onCheckedChange = { withDriver = it })
                }

                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("اسم العميل") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف (واتساب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("ملاحظات المشوار / الوقت المطلوب") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Button(
            onClick = {
                val details = "سيارة: ${selectedCar.model} | مسار: من $pickupCity إلى $destinationCity | تاريخ: $pickupDate | مدة: $daysCount يوم | مع سائق: $withDriver | سعر: ${selectedCar.price} | ملاحظات: $notes"
                onSubmit("استئجار سيارة (${selectedCar.model})", "نقل وسيارات", clientName, phone, pickupDate, 1, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("طلب استئجار السيارة", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

// ========================================================
// 8. TRAVEL INSURANCE FLOW
// ========================================================
@Composable
private fun TravelInsuranceFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    var fullName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }
    var destination by remember { mutableStateOf("دول الشنغن وأوروبا") }
    var depDate by remember { mutableStateOf("2026/11/01") }
    var retDate by remember { mutableStateOf("2026/11/20") }
    var travelersCount by remember { mutableIntStateOf(1) }
    var insuranceType by remember { mutableStateOf("تأمين شامل معتمد للسفارات (طبي + إلغاء رحلات)") }
    var passportAttached by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("بيانات وثيقة التأمين على السفر", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("اسم المسافر (مطابق للجواز)") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف (واتساب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = destination,
                    onValueChange = { destination = it },
                    label = { Text("وجهة السفر (الدولة / المنطقة)") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = depDate,
                        onValueChange = { depDate = it },
                        label = { Text("تاريخ بدء التأمين") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = retDate,
                        onValueChange = { retDate = it },
                        label = { Text("تاريخ الانتهاء") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = travelersCount.toString(),
                    onValueChange = { travelersCount = it.toIntOrNull() ?: 1 },
                    label = { Text("عدد المسافرين المشمولين") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = insuranceType,
                    onValueChange = { insuranceType = it },
                    label = { Text("نوع التغطية التأمينية") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedButton(
                    onClick = { passportAttached = !passportAttached },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (passportAttached) Icons.Default.CheckCircle else Icons.Default.UploadFile,
                        contentDescription = null,
                        tint = if (passportAttached) Color(0xFF10B981) else ShajeenSkyBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (passportAttached) "تم إرفاق صورة الجواز ✓" else "إرفاق صورة الجواز لإصدار الوثيقة")
                }
            }
        }

        Button(
            onClick = {
                val details = "وجهة: $destination | من: $depDate إلى: $retDate | نوع: $insuranceType | مسافرين: $travelersCount | مرفق: $passportAttached"
                onSubmit("تأمين سفر ($destination)", "تأمين", fullName, phone, depDate, travelersCount, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("طلب إصدار وثيقة التأمين", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

// ========================================================
// 9. CARGO & LUGGAGE SHIPPING FLOW
// ========================================================
@Composable
private fun CargoShippingFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    var senderName by remember { mutableStateOf(client?.fullName ?: "") }
    var senderPhone by remember { mutableStateOf(client?.phone ?: "") }
    var originCity by remember { mutableStateOf("صنعاء") }
    var destCity by remember { mutableStateOf("الرياض") }
    var cargoType by remember { mutableStateOf("أمتعة شخصية وحقائب") }
    var weightKg by remember { mutableStateOf("30") }
    var contentsDesc by remember { mutableStateOf("") }
    var recipientName by remember { mutableStateOf("") }
    var recipientPhone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("بيانات الشحنة والأمتعة", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = originCity,
                        onValueChange = { originCity = it },
                        label = { Text("مدينة الإرسال") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = destCity,
                        onValueChange = { destCity = it },
                        label = { Text("مدينة الوصول") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = cargoType,
                        onValueChange = { cargoType = it },
                        label = { Text("نوع الشحنة") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1.2f)
                    )
                    OutlinedTextField(
                        value = weightKg,
                        onValueChange = { weightKg = it },
                        label = { Text("الوزن (كجم)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(0.8f)
                    )
                }

                OutlinedTextField(
                    value = contentsDesc,
                    onValueChange = { contentsDesc = it },
                    label = { Text("وصف محتويات الشحنة / الطرد") },
                    placeholder = { Text("مثال: ملابس، أوراق رسمية، هدايا...") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(color = Color(0xFFF1F5F9))

                Text("بيانات المرسل والمستلم", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                OutlinedTextField(
                    value = senderName,
                    onValueChange = { senderName = it },
                    label = { Text("اسم المرسل") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = senderPhone,
                    onValueChange = { senderPhone = it },
                    label = { Text("هاتف المرسل") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = recipientName,
                    onValueChange = { recipientName = it },
                    label = { Text("اسم المستلم في دولة الوصول") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = recipientPhone,
                    onValueChange = { recipientPhone = it },
                    label = { Text("هاتف المستلم") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Button(
            onClick = {
                val details = "مسار: من $originCity إلى $destCity | نوع: $cargoType ($weightKg كجم) | وصف: $contentsDesc | مستلم: $recipientName ($recipientPhone)"
                onSubmit("شحن أمتعة: $cargoType", "شحن وطرود", senderName, senderPhone, "فوري", 1, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("طلب الشحن", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

// ========================================================
// 10. TRANSLATION & ATTESTATION FLOW
// ========================================================
@Composable
private fun TranslationServiceFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    var docType by remember { mutableStateOf("شهادات ومؤهلات علمية") }
    var languages by remember { mutableStateOf("من العربية إلى الإنجليزية") }
    var serviceType by remember { mutableStateOf("ترجمة وتصديق معاً") }
    var applicantName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }
    var docAttached by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("طلب ترجمة وتصديق الوثائق الرسمية", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                OutlinedTextField(
                    value = docType,
                    onValueChange = { docType = it },
                    label = { Text("نوع الوثيقة (شهادة، عقد، هوية، توكيل...)") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = languages,
                    onValueChange = { languages = it },
                    label = { Text("اللغة المصدر واللغة المطلوبة") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = serviceType,
                    onValueChange = { serviceType = it },
                    label = { Text("نوع الخدمة (ترجمة فقط، تصديق، كلاهما)") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedButton(
                    onClick = { docAttached = !docAttached },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (docAttached) Icons.Default.CheckCircle else Icons.Default.UploadFile,
                        contentDescription = null,
                        tint = if (docAttached) Color(0xFF10B981) else ShajeenSkyBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (docAttached) "تم إرفاق ملف الوثيقة بنجاح ✓" else "رفع صورة / ملف الوثيقة")
                }

                OutlinedTextField(
                    value = applicantName,
                    onValueChange = { applicantName = it },
                    label = { Text("اسم صاحب الطلب") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف (واتساب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("ملاحظات أو سفارات محددة للتصديق") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        }

        Button(
            onClick = {
                val details = "وثيقة: $docType | لغات: $languages | خدمة: $serviceType | مرفقة: $docAttached | ملاحظات: $notes"
                onSubmit("ترجمة وتصديق ($docType)", "وثائق وترجمة", applicantName, phone, "عاجل", 1, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.Translate, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("إرسال طلب الترجمة والتصديق", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

// ========================================================
// 11. AIRPORT MEET & GREET FLOW
// ========================================================
@Composable
private fun AirportMeetGreetFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    var passengerName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }
    var flightNumber by remember { mutableStateOf("IY 601") }
    var airportName by remember { mutableStateOf("مطار صنعاء الدولي") }
    var flightDate by remember { mutableStateOf("2026/10/25") }
    var flightTime by remember { mutableStateOf("11:30 صباحاً") }
    var passengersCount by remember { mutableIntStateOf(1) }
    var serviceType by remember { mutableStateOf("استقبال وترحيب عند الوصول + نقل خاص") }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("بيانات الاستقبال والتوديع في المطار", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                OutlinedTextField(
                    value = passengerName,
                    onValueChange = { passengerName = it },
                    label = { Text("اسم المسافر الرئيسي") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف (واتساب)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = airportName,
                        onValueChange = { airportName = it },
                        label = { Text("المطار") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1.2f)
                    )
                    OutlinedTextField(
                        value = flightNumber,
                        onValueChange = { flightNumber = it },
                        label = { Text("رقم الرحلة") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(0.8f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = flightDate,
                        onValueChange = { flightDate = it },
                        label = { Text("تاريخ الوصول / الإقلاع") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = flightTime,
                        onValueChange = { flightTime = it },
                        label = { Text("وقت الرحلة") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = serviceType,
                    onValueChange = { serviceType = it },
                    label = { Text("نوع الخدمة (استقبال، توديع، VIP مع سيارة)") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("ملاحظات خاصة (عدد الحقائب، لوحة باسم المسافر...)") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        }

        Button(
            onClick = {
                val details = "مطار: $airportName | رحلة: $flightNumber | موعد: $flightDate ($flightTime) | نوع الخدمة: $serviceType | ركاب: $passengersCount | ملاحظات: $notes"
                onSubmit("خدمة مطار: $airportName", "خدمات مطار", passengerName, phone, flightDate, passengersCount, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.Luggage, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("طلب خدمة الاستقبال / التوديع", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

// ========================================================
// 12. BUSINESS & VIP SERVICES FLOW
// ========================================================
@Composable
private fun BusinessVipFlow(
    client: ClientEntity?,
    onSubmit: (String, String, String, String, String, Int, String) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    var companyName by remember { mutableStateOf("") }
    var contactName by remember { mutableStateOf(client?.fullName ?: "") }
    var phone by remember { mutableStateOf(client?.phone ?: "") }
    var requestedService by remember { mutableStateOf("حجز طيران درجة رجال أعمال / طيران خاص") }
    var travelDate by remember { mutableStateOf("2026/11/05") }
    var vipGuestsCount by remember { mutableIntStateOf(1) }
    var requirementsDesc by remember { mutableStateOf("") }

    val vipOptions = listOf(
        "حجز طيران درجة رجال أعمال / طيران خاص",
        "حجوزات أجنحة فندقية رئاسية VIP",
        "سيارات ليموزين ولكزس VIP بسائق خاص",
        "صالات كبار الشخصيات بالمطار (CIP / Al Fursan)",
        "تنظيم اجتماعات وسفر وفود الأعمال"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("خدمات كبار الشخصيات ورجال الأعمال (Executive VIP)", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    vipOptions.forEach { opt ->
                        val selected = requestedService == opt
                        FilterChip(
                            selected = selected,
                            onClick = { requestedService = opt },
                            label = { Text(opt, fontSize = 12.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ShajeenDarkBlue,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                OutlinedTextField(
                    value = companyName,
                    onValueChange = { companyName = it },
                    label = { Text("اسم الشركة / الجهة (اختياري)") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = contactName,
                    onValueChange = { contactName = it },
                    label = { Text("اسم الشخص المسؤول / المسافر") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم هاتف التواصل المباشر") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = travelDate,
                        onValueChange = { travelDate = it },
                        label = { Text("تاريخ الخدمة المطلوب") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = vipGuestsCount.toString(),
                        onValueChange = { vipGuestsCount = it.toIntOrNull() ?: 1 },
                        label = { Text("عدد الأفراد VIP") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = requirementsDesc,
                    onValueChange = { requirementsDesc = it },
                    label = { Text("تفاصيل ومتطلبات الخدمة بدقة") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        }

        Button(
            onClick = {
                val details = "خدمة رجال أعمال: $requestedService | جهة/شركة: $companyName | تاريخ: $travelDate | أفراد: $vipGuestsCount | متطلبات: $requirementsDesc"
                onSubmit("رجال أعمال: $requestedService", "رجال أعمال", contactName, phone, travelDate, vipGuestsCount, details)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ShajeenDarkBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.BusinessCenter, contentDescription = null, tint = ShajeenGold)
            Spacer(modifier = Modifier.width(8.dp))
            Text("طلب خدمة رجال الأعمال VIP", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
        }
    }
}
