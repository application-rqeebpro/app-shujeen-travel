package com.example.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.ClientEntity
import com.example.data.entity.PaymentWalletEntity
import com.example.data.entity.VisaRequirementEntity
import com.example.ui.theme.ShajeenBodyText
import com.example.ui.theme.ShajeenCardBg
import com.example.ui.theme.ShajeenCardBorderSoft
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenHomeBgEnd
import com.example.ui.theme.ShajeenHomeBgStart
import com.example.ui.theme.ShajeenLightSky
import com.example.ui.theme.ShajeenPrimaryButton
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * 6-Step Electronic Booking Service (خدمة الحجز الإلكتروني والتأشيرات)
 * Step 1: Client Data (بيانات العميل)
 * Step 2: Visa Type (نوع التأشيرة)
 * Step 3: Documents Upload (رفع المستندات)
 * Step 4: Payment Info & Receipt (معلومات الدفع والإشعار)
 * Step 5: Review Details (مراجعة البيانات)
 * Step 6: Submission & WhatsApp Send (تأكيد الإرسال والواتساب)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElectronicBookingScreen(
    currentClient: ClientEntity?,
    wallets: List<PaymentWalletEntity>,
    visaRequirements: List<VisaRequirementEntity>,
    onBack: () -> Unit,
    onSubmitBooking: (
        fullName: String,
        phone: String,
        email: String,
        country: String,
        destination: String,
        travelDate: String,
        visaType: String,
        otherVisaType: String,
        notes: String,
        paymentMethod: String,
        transactionNumber: String,
        paymentReceipt: String,
        documents: List<Pair<String, String>>,
        onDone: (Boolean, String) -> Unit
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentStep by remember { mutableIntStateOf(1) }

    // Step 1 State: Client Data
    var fullName by remember { mutableStateOf(currentClient?.fullName ?: "") }
    var phone by remember { mutableStateOf(currentClient?.phone ?: "") }
    var email by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("اليمن") }
    var destination by remember { mutableStateOf("المملكة العربية السعودية") }
    var travelDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // Step 2 State: Visa Type
    val defaultVisaTypes = listOf("تأشيرة عمل", "تأشيرة سياحة", "تأشيرة زيارة", "تأشيرة عمرة", "أخرى")
    var selectedVisaType by remember { mutableStateOf("تأشيرة عمل") }
    var otherVisaType by remember { mutableStateOf("") }

    // Step 3 State: Uploaded Documents Map (DocType -> Uri string)
    val uploadedDocs = remember { mutableStateMapOf<String, String>() }
    var activeDocumentPickerType by remember { mutableStateOf<String?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            activeDocumentPickerType?.let { docType ->
                uploadedDocs[docType] = it.toString()
                Toast.makeText(context, "تم إرفاق: $docType بنجاح", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Step 4 State: Payment
    var selectedWallet by remember { mutableStateOf(wallets.firstOrNull()?.walletName ?: "محفظة جيب (Jib)") }
    var transactionNumber by remember { mutableStateOf("") }
    var receiptFileUri by remember { mutableStateOf("") }

    val receiptPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            receiptFileUri = it.toString()
            Toast.makeText(context, "تم إرفاق صورة إشعار التحويل", Toast.LENGTH_SHORT).show()
        }
    }

    // Step 6 State: Submission Result
    var submittedBookingNumber by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    // Calendar Picker
    val calendar = Calendar.getInstance()
    val dateFormatter = remember { SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()) }
    val travelDatePicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedCal = Calendar.getInstance()
                selectedCal.set(year, month, dayOfMonth)
                travelDate = dateFormatter.format(selectedCal.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // Determine required docs for selected visa
    val requirement = visaRequirements.firstOrNull { it.visaType == selectedVisaType }
    val requiredDocNames = remember(selectedVisaType, requirement) {
        val csv = requirement?.requiredDocsCsv
        if (!csv.isNullOrBlank()) {
            csv.split(",").map { it.trim() }.filter { it.isNotBlank() }
        } else {
            listOf("صورة جواز السفر", "صورة شخصية حديثة")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            // Header Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ShajeenDarkBlue,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier.testTag("electronic_booking_back_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward, // RTL back
                                contentDescription = "رجوع",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = "خدمة الحجز الإلكتروني والتأشيرات",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            )
                            Text(
                                text = "وكالة شجين للسفريات والسياحة",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = ShajeenLightSky,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "خطوة $currentStep من 6",
                            color = ShajeenGold,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Step Progress Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                for (step in 1..6) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                if (step <= currentStep) ShajeenPrimaryButton else Color(0xFFD1E8F5)
                            )
                    )
                }
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                when (currentStep) {
                    1 -> Step1ClientData(
                        fullName = fullName,
                        onFullNameChange = { fullName = it },
                        phone = phone,
                        onPhoneChange = { phone = it },
                        email = email,
                        onEmailChange = { email = it },
                        country = country,
                        onCountryChange = { country = it },
                        destination = destination,
                        onDestinationChange = { destination = it },
                        travelDate = travelDate,
                        onOpenDatePicker = { travelDatePicker.show() },
                        notes = notes,
                        onNotesChange = { notes = it }
                    )
                    2 -> Step2VisaType(
                        visaTypes = defaultVisaTypes,
                        selectedVisa = selectedVisaType,
                        onVisaSelected = { selectedVisaType = it },
                        otherVisaType = otherVisaType,
                        onOtherVisaChange = { otherVisaType = it },
                        requirement = requirement
                    )
                    3 -> Step3DocumentsUpload(
                        requiredDocs = requiredDocNames,
                        uploadedDocs = uploadedDocs,
                        onPickDoc = { docType ->
                            activeDocumentPickerType = docType
                            photoPickerLauncher.launch("*/*")
                        },
                        onRemoveDoc = { docType ->
                            uploadedDocs.remove(docType)
                        }
                    )
                    4 -> Step4Payment(
                        wallets = wallets,
                        selectedWallet = selectedWallet,
                        onWalletSelected = { selectedWallet = it },
                        transactionNumber = transactionNumber,
                        onTransactionNumberChange = { transactionNumber = it },
                        receiptUri = receiptFileUri,
                        onPickReceipt = { receiptPickerLauncher.launch("image/*") }
                    )
                    5 -> Step5Review(
                        fullName = fullName,
                        phone = phone,
                        email = email,
                        country = country,
                        destination = destination,
                        travelDate = travelDate,
                        visaType = if (selectedVisaType == "أخرى") otherVisaType else selectedVisaType,
                        uploadedDocs = uploadedDocs,
                        selectedWallet = selectedWallet,
                        transactionNumber = transactionNumber,
                        hasReceipt = receiptFileUri.isNotBlank(),
                        notes = notes
                    )
                    6 -> Step6SubmitConfirmation(
                        bookingNumber = submittedBookingNumber,
                        fullName = fullName,
                        phone = phone,
                        visaType = if (selectedVisaType == "أخرى") otherVisaType else selectedVisaType,
                        destination = destination,
                        paymentMethod = selectedWallet,
                        transactionNumber = transactionNumber,
                        onWhatsAppSend = {
                            sendViaWhatsApp(
                                context = context,
                                agencyPhone = "967770038009",
                                bookingNumber = submittedBookingNumber,
                                fullName = fullName,
                                phone = phone,
                                visaType = if (selectedVisaType == "أخرى") otherVisaType else selectedVisaType,
                                destination = destination,
                                travelDate = travelDate,
                                paymentMethod = selectedWallet,
                                transactionNumber = transactionNumber
                            )
                        },
                        onFinish = onBack
                    )
                }
            }

            // Bottom Navigation Controls (Steps 1 - 5)
            if (currentStep < 6) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = ShajeenCardBg,
                    border = BorderStroke(1.dp, ShajeenCardBorderSoft),
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (currentStep > 1) {
                            OutlinedButton(
                                onClick = { currentStep-- },
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, ShajeenCardBorderSoft),
                                modifier = Modifier.testTag("step_prev_btn")
                            ) {
                                Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("السابق", style = MaterialTheme.typography.labelMedium)
                            }
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        if (currentStep < 5) {
                            Button(
                                onClick = {
                                    // Validation per step
                                    if (currentStep == 1) {
                                        if (fullName.isBlank() || phone.isBlank()) {
                                            Toast.makeText(context, "يرجى كتابة الاسم ورقم الهاتف", Toast.LENGTH_SHORT).show()
                                            return@Button
                                        }
                                    }
                                    currentStep++
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ShajeenPrimaryButton,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.testTag("step_next_btn")
                            ) {
                                Text("التالي", style = MaterialTheme.typography.labelLarge)
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        } else if (currentStep == 5) {
                            // Submit Button
                            Button(
                                onClick = {
                                    val docsList = uploadedDocs.map { (k, v) -> Pair(k, v) }
                                    onSubmitBooking(
                                        fullName,
                                        phone,
                                        email,
                                        country,
                                        destination,
                                        travelDate,
                                        selectedVisaType,
                                        otherVisaType,
                                        notes,
                                        selectedWallet,
                                        transactionNumber,
                                        receiptFileUri,
                                        docsList
                                    ) { success, bNum ->
                                        if (success) {
                                            submittedBookingNumber = bNum
                                            isSubmitted = true
                                            currentStep = 6
                                        } else {
                                            Toast.makeText(context, "حدث خطأ أثناء إرسال الطلب", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ShajeenPrimaryButton,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.testTag("step_submit_booking_btn")
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("تأكيد وتقديم الطلب", style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ---------------------- STEP COMPOSABLES ----------------------

@Composable
private fun Step1ClientData(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    country: String,
    onCountryChange: (String) -> Unit,
    destination: String,
    onDestinationChange: (String) -> Unit,
    travelDate: String,
    onOpenDatePicker: () -> Unit,
    notes: String,
    onNotesChange: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = ShajeenSkyBlue.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("الخطوة 1: البيانات الشخصية والوجهة", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("أدخل بيانات مقدم طلب التأشيرة أو الحجز بدقة", style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = onFullNameChange,
                label = { Text("الاسم الرباعي كاملاً كما في الجواز *") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = ShajeenSkyBlue) },
                modifier = Modifier.fillMaxWidth().testTag("eb_full_name_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = onPhoneChange,
                label = { Text("رقم الهاتف / الواتساب للتواصل *") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = ShajeenSkyBlue) },
                modifier = Modifier.fillMaxWidth().testTag("eb_phone_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("البريد الإلكتروني (اختياري)") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = ShajeenSkyBlue) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = country,
                    onValueChange = onCountryChange,
                    label = { Text("بلد الإقامة") },
                    leadingIcon = { Icon(Icons.Default.Public, contentDescription = null, tint = ShajeenSkyBlue) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = destination,
                    onValueChange = onDestinationChange,
                    label = { Text("بلد الوجهة المطلوبة") },
                    leadingIcon = { Icon(Icons.Default.LocationCity, contentDescription = null, tint = ShajeenSkyBlue) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Travel Date Picker Box
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenDatePicker() },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, ShajeenCardBorderSoft)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = ShajeenSkyBlue)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("تاريخ السفر التقديري", style = MaterialTheme.typography.labelSmall.copy(color = ShajeenSecondaryText))
                        Text(
                            text = if (travelDate.isNotEmpty()) travelDate else "اضغط لتحديد تاريخ السفر",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (travelDate.isNotEmpty()) ShajeenHeadingText else Color.Gray
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = onNotesChange,
                label = { Text("ملاحظات إضافية للوكالة (اختياري)") },
                leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null, tint = ShajeenSkyBlue) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
private fun Step2VisaType(
    visaTypes: List<String>,
    selectedVisa: String,
    onVisaSelected: (String) -> Unit,
    otherVisaType: String,
    onOtherVisaChange: (String) -> Unit,
    requirement: VisaRequirementEntity?
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = ShajeenSkyBlue.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.CreditCard, contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("الخطوة 2: تحديد نوع التأشيرة أو الخدمة", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("اختر نوع المعاملة لعرض المتطلبات المحددة لها", style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            visaTypes.forEach { type ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onVisaSelected(type) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (selectedVisa == type) ShajeenHomeBgStart else Color.Transparent,
                    border = BorderStroke(
                        1.dp,
                        if (selectedVisa == type) ShajeenPrimaryButton else ShajeenCardBorderSoft
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (selectedVisa == type),
                            onClick = { onVisaSelected(type) },
                            colors = RadioButtonDefaults.colors(selectedColor = ShajeenPrimaryButton)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = type,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (selectedVisa == type) FontWeight.Bold else FontWeight.Normal,
                                color = ShajeenHeadingText
                            )
                        )
                    }
                }
            }

            if (selectedVisa == "أخرى") {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = otherVisaType,
                    onValueChange = onOtherVisaChange,
                    label = { Text("يرجى توضيح نوع التأشيرة أو المعاملة المطلوبة") },
                    modifier = Modifier.fillMaxWidth().testTag("eb_other_visa_input"),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Visa Requirement Notice Banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = ShajeenHomeBgStart,
                border = BorderStroke(1.dp, ShajeenCardBorderSoft)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = ShajeenPrimaryButton, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "المتطلبات الأساسية لـ ($selectedVisa):",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = requirement?.requiredDocsCsv?.replace(",", " • ")
                                ?: "جواز سفر ساري المفعول • صور شخصية • المستندات الرسمية المؤيدة",
                            style = MaterialTheme.typography.bodySmall.copy(color = ShajeenBodyText, lineHeight = 18.sp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Step3DocumentsUpload(
    requiredDocs: List<String>,
    uploadedDocs: Map<String, String>,
    onPickDoc: (String) -> Unit,
    onRemoveDoc: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = ShajeenSkyBlue.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.UploadFile, contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("الخطوة 3: رفع المستندات المطلوبة", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("الملفات المدعومة: صور JPG, PNG أو مستندات PDF بحجم مناسب", style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            requiredDocs.forEach { docName ->
                val isUploaded = uploadedDocs.containsKey(docName)

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = if (isUploaded) Color(0xFFF0FDF4) else ShajeenHomeBgStart,
                    border = BorderStroke(1.dp, if (isUploaded) Color(0xFF86EFAC) else ShajeenCardBorderSoft)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(
                                imageVector = if (isUploaded) Icons.Default.CheckCircle else Icons.Default.Description,
                                contentDescription = null,
                                tint = if (isUploaded) Color(0xFF16A34A) else ShajeenSkyBlue,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = docName,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ShajeenHeadingText
                                    )
                                )
                                Text(
                                    text = if (isUploaded) "تم إرفاق الملف بنجاح" else "لم يتم الرفع بعد",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isUploaded) Color(0xFF15803D) else ShajeenSecondaryText
                                    )
                                )
                            }
                        }

                        if (isUploaded) {
                            OutlinedButton(
                                onClick = { onRemoveDoc(docName) },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, Color(0xFFFCA5A5))
                            ) {
                                Text("إلغاء", color = Color(0xFFDC2626), fontSize = 11.sp)
                            }
                        } else {
                            Button(
                                onClick = { onPickDoc(docName) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ShajeenPrimaryButton,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(Icons.Default.AttachFile, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("اختيار ملف", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Step4Payment(
    wallets: List<PaymentWalletEntity>,
    selectedWallet: String,
    onWalletSelected: (String) -> Unit,
    transactionNumber: String,
    onTransactionNumberChange: (String) -> Unit,
    receiptUri: String,
    onPickReceipt: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
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
                    Text("الخطوة 4: معلومات الدفع ورسوم المعاملة", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("اختر المحفظة المناسبة ثم أرفق إشعار التحويل", style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("حسابات ومحافظ وكالة شجين المعتمدة:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))

            wallets.forEach { wallet ->
                val isSelected = (selectedWallet == wallet.walletName)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onWalletSelected(wallet.walletName) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) ShajeenHomeBgStart else Color.Transparent,
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) ShajeenPrimaryButton else ShajeenCardBorderSoft
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { onWalletSelected(wallet.walletName) },
                                colors = RadioButtonDefaults.colors(selectedColor = ShajeenPrimaryButton)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = wallet.walletName,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                                )
                                Text(
                                    text = "رقم الحساب / المحفظة: ${wallet.accountNumber}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = ShajeenPrimaryButton,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )
                            }
                        }
                        if (wallet.instructions.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = wallet.instructions,
                                style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText, fontSize = 11.sp),
                                modifier = Modifier.padding(start = 40.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notice about manual verification
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = ShajeenGold.copy(alpha = 0.12f),
                border = BorderStroke(1.dp, ShajeenGold.copy(alpha = 0.4f))
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = ShajeenDarkBlue, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "تنبيه: لا يعتبر التحويل مؤكداً تلقائياً، بل تتم مراجعته والتحقق منه يدوياً من قبل الإدارة قبل اعتماد المعاملة.",
                        style = MaterialTheme.typography.bodySmall.copy(color = ShajeenDarkBlue, lineHeight = 17.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = transactionNumber,
                onValueChange = onTransactionNumberChange,
                label = { Text("رقم الحوالة أو العملية (Transaction ID)") },
                leadingIcon = { Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = ShajeenSkyBlue) },
                modifier = Modifier.fillMaxWidth().testTag("eb_transaction_number_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Upload Receipt Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPickReceipt() },
                shape = RoundedCornerShape(12.dp),
                color = if (receiptUri.isNotBlank()) Color(0xFFF0FDF4) else ShajeenHomeBgStart,
                border = BorderStroke(1.dp, if (receiptUri.isNotBlank()) Color(0xFF86EFAC) else ShajeenCardBorderSoft)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (receiptUri.isNotBlank()) Icons.Default.CheckCircle else Icons.Default.UploadFile,
                            contentDescription = null,
                            tint = if (receiptUri.isNotBlank()) Color(0xFF16A34A) else ShajeenSkyBlue
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "إرفاق صورة إشعار التحويل / السند",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                            )
                            Text(
                                text = if (receiptUri.isNotBlank()) "تم إرفاق صورة السند" else "اضغط لاختيار صورة الإيصال",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (receiptUri.isNotBlank()) Color(0xFF15803D) else ShajeenSecondaryText
                                )
                            )
                        }
                    }

                    Button(
                        onClick = onPickReceipt,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (receiptUri.isNotBlank()) Color(0xFF16A34A) else ShajeenPrimaryButton,
                            contentColor = Color.White
                        )
                    ) {
                        Text(if (receiptUri.isNotBlank()) "تغيير" else "رفع الإشعار", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun Step5Review(
    fullName: String,
    phone: String,
    email: String,
    country: String,
    destination: String,
    travelDate: String,
    visaType: String,
    uploadedDocs: Map<String, String>,
    selectedWallet: String,
    transactionNumber: String,
    hasReceipt: Boolean,
    notes: String
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = ShajeenSkyBlue.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = ShajeenSkyBlue, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("الخطوة 5: مراجعة تفاصيل الطلب", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("يرجى مراجعة كافة البيانات قبل تأكيد التقديم النهائي", style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ReviewItem(label = "الاسم الكامل", value = fullName)
            ReviewItem(label = "رقم الهاتف / الواتساب", value = phone)
            if (email.isNotBlank()) ReviewItem(label = "البريد الإلكتروني", value = email)
            ReviewItem(label = "بلد الإقامة والوجهة", value = "$country ← $destination")
            if (travelDate.isNotBlank()) ReviewItem(label = "تاريخ السفر التقديري", value = travelDate)
            ReviewItem(label = "نوع التأشيرة / المعاملة", value = visaType)
            ReviewItem(label = "المستندات المرفقة", value = "${uploadedDocs.size} مستندات (${uploadedDocs.keys.joinToString("، ")})")
            ReviewItem(label = "طريقة الدفع المختارة", value = selectedWallet)
            ReviewItem(label = "رقم العملية / الحوالة", value = if (transactionNumber.isNotBlank()) transactionNumber else "لم يُحدد بعد")
            ReviewItem(label = "صورة إشعار التحويل", value = if (hasReceipt) "مرفقة بنجاح" else "لم يتم إرفاق إشعار")
            if (notes.isNotBlank()) ReviewItem(label = "ملاحظات إضافية", value = notes)
        }
    }
}

@Composable
private fun ReviewItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 5.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = ShajeenSecondaryText))
        Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = ShajeenHeadingText))
        Divider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
private fun Step6SubmitConfirmation(
    bookingNumber: String,
    fullName: String,
    phone: String,
    visaType: String,
    destination: String,
    paymentMethod: String,
    transactionNumber: String,
    onWhatsAppSend: () -> Unit,
    onFinish: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ShajeenCardBg),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDCFCE7)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(38.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "تم استلام طلبك بنجاح!",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = ShajeenHeadingText
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "رقم الحجز الخاص بك:",
                style = MaterialTheme.typography.bodySmall.copy(color = ShajeenSecondaryText)
            )

            Text(
                text = bookingNumber,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = ShajeenPrimaryButton,
                    fontSize = 24.sp
                ),
                modifier = Modifier.testTag("eb_confirmed_booking_number")
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = ShajeenHomeBgStart,
                border = BorderStroke(1.dp, ShajeenCardBorderSoft)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "الخطوة التالية:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "يمكنك إرسال تفاصيل طلبك مباشرة إلى خدمة عملاء وكالة شجين عبر الواتساب لتسريع عملية المراجعة وتأكيد الدفع.",
                        style = MaterialTheme.typography.bodySmall.copy(color = ShajeenBodyText, lineHeight = 18.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // WhatsApp Share Button
            Button(
                onClick = onWhatsAppSend,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF25D366), // Official WhatsApp Green
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("eb_whatsapp_send_btn")
            ) {
                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "إرسال تفاصيل الحجز عبر واتساب",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onFinish,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, ShajeenCardBorderSoft),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("العودة إلى الرئيسية", color = ShajeenHeadingText)
            }
        }
    }
}

// ---------------------- WHATSAPP SENDER HELPER ----------------------

private fun sendViaWhatsApp(
    context: Context,
    agencyPhone: String,
    bookingNumber: String,
    fullName: String,
    phone: String,
    visaType: String,
    destination: String,
    travelDate: String,
    paymentMethod: String,
    transactionNumber: String
) {
    val message = """
        *طلب حجز إلكتروني جديد - وكالة شجين للسفريات والسياحة*
        ------------------------------------------
        *رقم الطلب:* $bookingNumber
        *الاسم:* $fullName
        *الهاتف:* $phone
        *نوع التأشيرة/الخدمة:* $visaType
        *الوجهة المطلوبة:* $destination
        *تاريخ السفر:* ${if (travelDate.isNotBlank()) travelDate else "غير محدد"}
        ------------------------------------------
        *تفاصيل الدفع:*
        *المحفظة/الحساب:* $paymentMethod
        *رقم العملية:* ${if (transactionNumber.isNotBlank()) transactionNumber else "بانتظار التأكيد"}
        ------------------------------------------
        يرجى مراجعة الطلب والمستندات المرفقة وتأكيد الحجز. شكراً لكم!
    """.trimIndent()

    try {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$agencyPhone&text=${Uri.encode(message)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "تعذر فتح تطبيق واتساب. تم نسخ تفاصيل الطلب.", Toast.LENGTH_LONG).show()
    }
}
