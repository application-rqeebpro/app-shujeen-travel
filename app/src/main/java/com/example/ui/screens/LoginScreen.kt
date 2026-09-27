package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.LocationData
import com.example.data.entity.AgencySettingsEntity
import com.example.data.entity.ClientEntity
import com.example.ui.components.AgencyContactFooter
import com.example.ui.components.AgencyOwnerPortalCard
import com.example.ui.components.CustomerServiceTopBar
import com.example.ui.components.OfficialShajeenLogoCard
import com.example.ui.components.ServiceIconsHeaderRow
import com.example.ui.theme.ShajeenBodyText
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenHomeBgEnd
import com.example.ui.theme.ShajeenHomeBgStart
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue
import com.example.ui.theme.ShajeenSkyContainer

enum class AuthTab {
    LOGIN,
    REGISTER
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    agencySettings: AgencySettingsEntity?,
    allClients: List<ClientEntity> = emptyList(),
    onRegisterOrLogin: (
        fullName: String,
        phone: String,
        idType: String,
        idNumber: String,
        country: String,
        city: String,
        district: String,
        area: String
    ) -> Unit,
    onQuickPhoneLogin: (phone: String) -> Unit,
    onAdminLogin: (pin: String, onResult: (Boolean) -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Tab state (Login vs Register)
    var selectedTab by remember { mutableStateOf(AuthTab.LOGIN) }

    // Quick Login by Phone state
    var loginPhoneNumber by remember { mutableStateOf("") }
    var loginPhoneError by remember { mutableStateOf(false) }

    // Register Form states
    var regFullName by remember { mutableStateOf("") }
    var regPhoneNumber by remember { mutableStateOf("") }
    val idTypes = listOf("بطاقة شخصية", "جواز سفر", "بطاقة عائلية")
    var regSelectedIdType by remember { mutableStateOf(idTypes[0]) }
    var regIdTypeExpanded by remember { mutableStateOf(false) }
    var regIdNumber by remember { mutableStateOf("") }

    var regPassword by remember { mutableStateOf("") }
    var regConfirmPassword by remember { mutableStateOf("") }

    // Location States
    var regSelectedCountry by remember { mutableStateOf("اليمن") }
    var regCountryExpanded by remember { mutableStateOf(false) }

    var availableCities = remember(regSelectedCountry) { LocationData.getCities(regSelectedCountry) }
    var regSelectedCity by remember(regSelectedCountry) { mutableStateOf(availableCities.firstOrNull() ?: "صنعاء") }
    var regCityExpanded by remember { mutableStateOf(false) }

    var availableDistricts = remember(regSelectedCity) { LocationData.getDistricts(regSelectedCity) }
    var regSelectedDistrict by remember(regSelectedCity) { mutableStateOf(availableDistricts.firstOrNull() ?: "") }
    var regDistrictExpanded by remember { mutableStateOf(false) }

    var regAreaName by remember { mutableStateOf("") }

    // Validation errors
    var regNameError by remember { mutableStateOf(false) }
    var regPhoneError by remember { mutableStateOf(false) }
    var regIdError by remember { mutableStateOf(false) }
    var regPasswordError by remember { mutableStateOf(false) }
    var regConfirmPasswordError by remember { mutableStateOf(false) }

    // Admin Dialog state
    var showAdminDialog by remember { mutableStateOf(false) }
    var adminPin by remember { mutableStateOf("") }
    var adminPinVisible by remember { mutableStateOf(false) }
    var adminPinError by remember { mutableStateOf(false) }

    val customerPhone = agencySettings?.phone1 ?: "+967 777779492"

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
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Modern Sleek Header for Login Screen
            ModernLoginHeroHeader(
                onOwnerLoginClick = { showAdminDialog = true }
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 5. Two Tabs: "تسجيل الدخول" and "إنشاء حساب جديد"
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Tab 1: تسجيل الدخول
                    val isLogin = selectedTab == AuthTab.LOGIN
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedTab = AuthTab.LOGIN }
                            .testTag("auth_tab_login"),
                        color = if (isLogin) ShajeenSkyBlue else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Login,
                                contentDescription = null,
                                tint = if (isLogin) Color.White else ShajeenHeadingText,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "تسجيل الدخول",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isLogin) Color.White else ShajeenHeadingText,
                                    fontSize = 13.5.sp
                                )
                            )
                        }
                    }

                    // Tab 2: إنشاء حساب جديد
                    val isRegister = selectedTab == AuthTab.REGISTER
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedTab = AuthTab.REGISTER }
                            .testTag("auth_tab_register"),
                        color = if (isRegister) ShajeenSkyBlue else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null,
                                tint = if (isRegister) Color.White else ShajeenHeadingText,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "إنشاء حساب جديد",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isRegister) Color.White else ShajeenHeadingText,
                                    fontSize = 13.5.sp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TAB CONTENT
            if (selectedTab == AuthTab.LOGIN) {
                // Login Form Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("login_form_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.5.dp, Color(0xFFCBD5E1))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "رقم الهاتف المسجل *",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShajeenHeadingText,
                                fontSize = 15.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = loginPhoneNumber,
                            onValueChange = {
                                loginPhoneNumber = it
                                loginPhoneError = false
                            },
                            placeholder = {
                                Text(
                                    "أدخل رقم هاتفك (مثال: 777777777)",
                                    color = Color(0xFF64748B),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = "رقم الهاتف",
                                    tint = ShajeenSkyBlue
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            isError = loginPhoneError,
                            supportingText = {
                                if (loginPhoneError) {
                                    Text(
                                        "يرجى إدخال رقم هاتف صحيح للمتابعة",
                                        color = MaterialTheme.colorScheme.error,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = ShajeenHeadingText,
                                unfocusedTextColor = ShajeenHeadingText,
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFF94A3B8),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_phone_input")
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Large Sky Blue Button: "دخول إلى حسابي"
                        Button(
                            onClick = {
                                val clean = loginPhoneNumber.trim()
                                if (clean.length >= 7) {
                                    onQuickPhoneLogin(clean)
                                } else {
                                    loginPhoneError = true
                                    Toast.makeText(context, "يرجى إدخال رقم الهاتف بشكل صحيح", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ShajeenSkyBlue,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(14.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .testTag("login_submit_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Login,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "دخول إلى حسابي",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Clear helpful guidance note with strong contrast (No auto-fill button)
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF0F9FF),
                            border = BorderStroke(1.dp, Color(0xFFBAE6FD))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = ShajeenSkyBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "قم بإدخال رقم هاتفك للدخول ومتابعة رحلاتك وحجوزاتك بكل سهولة وأمان.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = ShajeenSecondaryText,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp,
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                        }
                    }
                }
            } else {
                // 8. Register Form (White card, same visual identity, sky blue button)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("register_form_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "بيانات إنشاء حساب مسافر جديد",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShajeenHeadingText
                            )
                        )

                        // 1. الاسم الكامل
                        OutlinedTextField(
                            value = regFullName,
                            onValueChange = {
                                regFullName = it
                                regNameError = false
                            },
                            label = { Text("الاسم الكامل *") },
                            placeholder = { Text("أدخل اسمك كما في الهوية") },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null, tint = ShajeenSkyBlue)
                            },
                            isError = regNameError,
                            supportingText = {
                                if (regNameError) {
                                    Text("يرجى إدخال الاسم كاملاً", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_full_name_input"),
                            singleLine = true
                        )

                        // 2. رقم الهاتف
                        OutlinedTextField(
                            value = regPhoneNumber,
                            onValueChange = {
                                regPhoneNumber = it
                                regPhoneError = false
                            },
                            label = { Text("رقم الهاتف للتواصل *") },
                            placeholder = { Text("مثال: 777777777") },
                            leadingIcon = {
                                Icon(Icons.Default.Phone, contentDescription = null, tint = ShajeenSkyBlue)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            isError = regPhoneError,
                            supportingText = {
                                if (regPhoneError) {
                                    Text("يرجى إدخال رقم هاتف صحيح", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_phone_input"),
                            singleLine = true
                        )

                        // 3. نوع الهوية
                        ExposedDropdownMenuBox(
                            expanded = regIdTypeExpanded,
                            onExpandedChange = { regIdTypeExpanded = !regIdTypeExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = regSelectedIdType,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("نوع الهوية الرسمية *") },
                                leadingIcon = {
                                    Icon(Icons.Default.CreditCard, contentDescription = null, tint = ShajeenSkyBlue)
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = regIdTypeExpanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ShajeenSkyBlue,
                                    unfocusedBorderColor = Color(0xFFCBD5E1),
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color(0xFFF8FAFC)
                                ),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                    .testTag("reg_id_type_selector")
                            )
                            ExposedDropdownMenu(
                                expanded = regIdTypeExpanded,
                                onDismissRequest = { regIdTypeExpanded = false }
                            ) {
                                idTypes.forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type, fontWeight = FontWeight.Medium) },
                                        onClick = {
                                            regSelectedIdType = type
                                            regIdTypeExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // 4. رقم الهوية
                        OutlinedTextField(
                            value = regIdNumber,
                            onValueChange = {
                                regIdNumber = it
                                regIdError = false
                            },
                            label = { Text("رقم الهوية (البطاقة أو الجواز) *") },
                            placeholder = { Text("أدخل رقم الهوية أو رقم الجواز") },
                            leadingIcon = {
                                Icon(Icons.Default.Badge, contentDescription = null, tint = ShajeenSkyBlue)
                            },
                            isError = regIdError,
                            supportingText = {
                                if (regIdError) {
                                    Text("يرجى إدخال رقم الهوية", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_id_number_input"),
                            singleLine = true
                        )

                        // 5. كلمة المرور
                        OutlinedTextField(
                            value = regPassword,
                            onValueChange = {
                                regPassword = it
                                regPasswordError = false
                            },
                            label = { Text("كلمة المرور *") },
                            placeholder = { Text("اختر كلمة مرور لحسابك") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ShajeenSkyBlue)
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            isError = regPasswordError,
                            supportingText = {
                                if (regPasswordError) {
                                    Text("كلمة المرور يجب أن لا تقل عن 4 خانات", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_password_input"),
                            singleLine = true
                        )

                        // 6. تأكيد كلمة المرور
                        OutlinedTextField(
                            value = regConfirmPassword,
                            onValueChange = {
                                regConfirmPassword = it
                                regConfirmPasswordError = false
                            },
                            label = { Text("تأكيد كلمة المرور *") },
                            placeholder = { Text("أعد إدخال كلمة المرور") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ShajeenSkyBlue)
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            isError = regConfirmPasswordError,
                            supportingText = {
                                if (regConfirmPasswordError) {
                                    Text("كلمتا المرور غير متطابقتين", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_confirm_password_input"),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Cascading Location Dropdowns
                        Text(
                            text = "محددات الموقع والإقامة",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShajeenHeadingText
                            )
                        )

                        // Country
                        ExposedDropdownMenuBox(
                            expanded = regCountryExpanded,
                            onExpandedChange = { regCountryExpanded = !regCountryExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = regSelectedCountry,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("الدولة") },
                                leadingIcon = {
                                    Icon(Icons.Default.Public, contentDescription = null, tint = ShajeenSkyBlue)
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = regCountryExpanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ShajeenSkyBlue,
                                    unfocusedBorderColor = Color(0xFFCBD5E1),
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color(0xFFF8FAFC)
                                ),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            )
                            ExposedDropdownMenu(
                                expanded = regCountryExpanded,
                                onDismissRequest = { regCountryExpanded = false }
                            ) {
                                LocationData.countries.forEach { c ->
                                    DropdownMenuItem(
                                        text = { Text(c) },
                                        onClick = {
                                            regSelectedCountry = c
                                            availableCities = LocationData.getCities(c)
                                            regSelectedCity = availableCities.firstOrNull() ?: ""
                                            availableDistricts = LocationData.getDistricts(regSelectedCity)
                                            regSelectedDistrict = availableDistricts.firstOrNull() ?: ""
                                            regCountryExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // City
                        ExposedDropdownMenuBox(
                            expanded = regCityExpanded,
                            onExpandedChange = { regCityExpanded = !regCityExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = regSelectedCity,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("المدينة / المحافظة") },
                                leadingIcon = {
                                    Icon(Icons.Default.LocationCity, contentDescription = null, tint = ShajeenSkyBlue)
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = regCityExpanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ShajeenSkyBlue,
                                    unfocusedBorderColor = Color(0xFFCBD5E1),
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color(0xFFF8FAFC)
                                ),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            )
                            ExposedDropdownMenu(
                                expanded = regCityExpanded,
                                onDismissRequest = { regCityExpanded = false }
                            ) {
                                availableCities.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            regSelectedCity = city
                                            availableDistricts = LocationData.getDistricts(city)
                                            regSelectedDistrict = availableDistricts.firstOrNull() ?: ""
                                            regCityExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // District
                        ExposedDropdownMenuBox(
                            expanded = regDistrictExpanded,
                            onExpandedChange = { regDistrictExpanded = !regDistrictExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = regSelectedDistrict,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("المديرية") },
                                leadingIcon = {
                                    Icon(Icons.Default.LocationCity, contentDescription = null, tint = ShajeenSkyBlue)
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = regDistrictExpanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ShajeenSkyBlue,
                                    unfocusedBorderColor = Color(0xFFCBD5E1),
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color(0xFFF8FAFC)
                                ),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            )
                            ExposedDropdownMenu(
                                expanded = regDistrictExpanded,
                                onDismissRequest = { regDistrictExpanded = false }
                            ) {
                                availableDistricts.forEach { dist ->
                                    DropdownMenuItem(
                                        text = { Text(dist) },
                                        onClick = {
                                            regSelectedDistrict = dist
                                            regDistrictExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Area
                        OutlinedTextField(
                            value = regAreaName,
                            onValueChange = { regAreaName = it },
                            label = { Text("الحي أو الشارع السكني") },
                            placeholder = { Text("مثال: شارع خولان / حدة") },
                            leadingIcon = {
                                Icon(Icons.Default.LocationCity, contentDescription = null, tint = ShajeenSkyBlue)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_area_input"),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Large Sky Blue Button: إنشاء الحساب والمتابعة
                        Button(
                            onClick = {
                                val isNameOk = regFullName.trim().length >= 3
                                val isPhoneOk = regPhoneNumber.trim().length >= 7
                                val isIdOk = regIdNumber.trim().isNotBlank()
                                val isPassOk = regPassword.length >= 4
                                val isMatch = regPassword == regConfirmPassword

                                if (!isNameOk) regNameError = true
                                if (!isPhoneOk) regPhoneError = true
                                if (!isIdOk) regIdError = true
                                if (!isPassOk) regPasswordError = true
                                if (!isMatch) regConfirmPasswordError = true

                                if (isNameOk && isPhoneOk && isIdOk && isPassOk && isMatch) {
                                    onRegisterOrLogin(
                                        regFullName.trim(),
                                        regPhoneNumber.trim(),
                                        regSelectedIdType,
                                        regIdNumber.trim(),
                                        regSelectedCountry,
                                        regSelectedCity,
                                        regSelectedDistrict,
                                        regAreaName.trim().ifEmpty { "المنطقة الرئيسية" }
                                    )
                                } else {
                                    Toast.makeText(context, "يرجى استكمال الحقول المطلوبة والتأكد من تطابق كلمة المرور", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ShajeenSkyBlue,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(14.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("reg_submit_btn")
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "إنشاء الحساب والمتابعة",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Modern Quick Contact Actions: WhatsApp, Location, Contact Us
            LoginModernContactFooter(settings = agencySettings)

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Admin PIN Dialog
        // CRITICAL: NEVER display the password or PIN inside the UI text or hints.
        if (showAdminDialog) {
            AlertDialog(
                onDismissRequest = {
                    showAdminDialog = false
                    adminPin = ""
                    adminPinError = false
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = ShajeenGold.copy(alpha = 0.2f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = ShajeenGold,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "بوابة مالك الوكالة / الإدارة",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShajeenHeadingText
                            )
                        )
                    }
                },
                text = {
                    Column {
                        Text(
                            text = "يرجى إدخال رمز المرور السري الخاص بإدارة وكالة شجين للمتابعة والتحكم في الحجوزات والخدمات والمحافظ.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ShajeenSecondaryText,
                                lineHeight = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = adminPin,
                            onValueChange = {
                                adminPin = it
                                adminPinError = false
                            },
                            label = { Text("رمز المرور السري (PIN)") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ShajeenSkyBlue)
                            },
                            trailingIcon = {
                                IconButton(onClick = { adminPinVisible = !adminPinVisible }) {
                                    Icon(
                                        imageVector = if (adminPinVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                        contentDescription = "إظهار الرمز",
                                        tint = ShajeenSkyBlue
                                    )
                                }
                            },
                            visualTransformation = if (adminPinVisible) androidx.compose.ui.text.input.VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            isError = adminPinError,
                            supportingText = {
                                if (adminPinError) {
                                    Text("رمز المرور السري غير صحيح، يرجى إعادة المحاولة", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_pin_input"),
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val clean = adminPin.trim()
                            if (clean.isNotBlank()) {
                                onAdminLogin(clean) { success ->
                                    if (success) {
                                        showAdminDialog = false
                                        adminPin = ""
                                        adminPinError = false
                                    } else {
                                        adminPinError = true
                                    }
                                }
                            } else {
                                adminPinError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ShajeenDarkBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("admin_dialog_confirm_btn")
                    ) {
                        Text("دخول")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showAdminDialog = false
                            adminPin = ""
                            adminPinError = false
                        }
                    ) {
                        Text("إلغاء", color = ShajeenSecondaryText)
                    }
                }
            )
        }
    }
}

@Composable
fun ModernLoginHeroHeader(
    onOwnerLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF071B34)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.5.dp, Brush.horizontalGradient(
            colors = listOf(
                ShajeenGold.copy(alpha = 0.5f),
                ShajeenSkyBlue.copy(alpha = 0.4f),
                ShajeenGold.copy(alpha = 0.5f)
            )
        ))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF091F3C),
                            Color(0xFF0E2C52),
                            Color(0xFF07172C)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row: Official License Badge & Discreet Admin Entry
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Modern Official Agency Badge
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.12f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981))
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(
                                text = "وكالة معتمدة ومرخصة رسمياً",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    // Discreet, sleek Admin Portal button
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = ShajeenGold,
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onOwnerLoginClick() }
                            .testTag("admin_login_header_btn")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = ShajeenDarkBlue,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "بوابة الإدارة",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ShajeenDarkBlue,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.5.sp
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Modern Official Agency Logo Emblem
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    border = BorderStroke(1.5.dp, ShajeenSkyBlue.copy(alpha = 0.4f)),
                    modifier = Modifier.size(width = 160.dp, height = 130.dp),
                    shadowElevation = 8.dp
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_shajeen_logo),
                        contentDescription = "شعار وكالة شجين للسفريات والسياحة",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Brand Name in high-contrast crisp bold Arabic
                Text(
                    text = "وكالة شجين للسفريات والسياحة",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 21.5.sp,
                        letterSpacing = 0.5.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                // English Sub-brand
                Text(
                    text = "SHAJEEN TRAVEL & TOURISM",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF7DD3FC),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 1.2.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "خدمات حجز الطيران، الحج والعمرة، وتأشيرات السفر حول العالم",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFFE2E8F0),
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.5.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Modern Highlights Badges Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
                ) {
                    ModernHeaderPill("✈️ رحلات طيران")
                    ModernHeaderPill("🕋 حج وعمرة")
                    ModernHeaderPill("🛂 جوازات وتأشيرات")
                    ModernHeaderPill("🏨 فنادق وسياحة")
                }
            }
        }
    }
}

@Composable
private fun ModernHeaderPill(label: String) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.22f))
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun LoginModernContactFooter(
    settings: AgencySettingsEntity?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showAddressDialog by remember { mutableStateOf(false) }

    val rawAddress = settings?.address ?: "صنعاء - شارع خولان - جوار السلامي لمواد البناء"
    val whatsappNumber = settings?.whatsappNumber?.ifBlank { "770038009" } ?: "770038009"
    val callPhone = settings?.phone1?.ifBlank { "770038009" } ?: "770038009"

    fun openWhatsapp() {
        try {
            val clean = whatsappNumber.replace("+", "").replace(" ", "").trim()
            val url = if (clean.startsWith("967")) "https://wa.me/$clean" else "https://wa.me/967$clean"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "الواتساب: $whatsappNumber", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWebsiteOrLocation() {
        try {
            val mapUri = Uri.parse("geo:0,0?q=صنعاء+شارع+خولان+وكالة+شجين")
            val intent = Intent(Intent.ACTION_VIEW, mapUri)
            context.startActivity(intent)
        } catch (e: Exception) {
            showAddressDialog = true
        }
    }

    fun makeCall() {
        try {
            val clean = callPhone.replace(" ", "").trim()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$clean"))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "الاتصال بالرقم: $callPhone", Toast.LENGTH_SHORT).show()
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.5.dp, Color(0xFFCBD5E1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "قنوات التواصل المباشر مع الوكالة",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ShajeenHeadingText,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "فريق خدمة العملاء متواجد لخدمتكم وإتمام حجوزاتكم بكل سرعة واحترافية",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ShajeenSecondaryText,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.5.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3 Modern Action Cards: WhatsApp, Location/Website, Contact Us (No raw phone numbers listed)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1. WhatsApp Action
                ContactActionTile(
                    title = "واتساب",
                    subtitle = "محادثة فورية",
                    icon = Icons.Default.Chat,
                    iconTint = Color(0xFF16A34A),
                    bgColor = Color(0xFFDCFCE7),
                    borderColor = Color(0xFF86EFAC),
                    onClick = { openWhatsapp() },
                    modifier = Modifier.weight(1f)
                )

                // 2. Website & Location Action
                ContactActionTile(
                    title = "الموقع",
                    subtitle = "الفرع والخريطة",
                    icon = Icons.Default.Public,
                    iconTint = Color(0xFF0284C7),
                    bgColor = Color(0xFFE0F2FE),
                    borderColor = Color(0xFF7DD3FC),
                    onClick = { openWebsiteOrLocation() },
                    modifier = Modifier.weight(1f)
                )

                // 3. Call / Contact Us Action
                ContactActionTile(
                    title = "تواصل معنا",
                    subtitle = "اتصال مباشر",
                    icon = Icons.Default.Call,
                    iconTint = Color(0xFF059669),
                    bgColor = Color(0xFFECFDF5),
                    borderColor = Color(0xFFA7F3D0),
                    onClick = { makeCall() },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    if (showAddressDialog) {
        AlertDialog(
            onDismissRequest = { showAddressDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFFDC2626))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("مقر وكالة شجين الرئيسي", fontWeight = FontWeight.Bold, color = ShajeenHeadingText)
                }
            },
            text = {
                Column {
                    Text(
                        text = rawAddress,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ShajeenBodyText,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 22.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "أوقات الدوام: يومياً من 8 صباحاً حتى 10 مساءً",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ShajeenSecondaryText,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showAddressDialog = false
                        try {
                            val mapUri = Uri.parse("geo:0,0?q=صنعاء+شارع+خولان+وكالة+شجين")
                            context.startActivity(Intent(Intent.ACTION_VIEW, mapUri))
                        } catch (e: Exception) {
                            // Handled
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ShajeenSkyBlue)
                ) {
                    Text("عرض على الخريطة", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddressDialog = false }) {
                    Text("إغلاق", color = ShajeenSecondaryText, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
private fun ContactActionTile(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    bgColor: Color,
    borderColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("contact_tile_$title"),
        color = bgColor,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, borderColor),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                modifier = Modifier.size(40.dp),
                shadowElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconTint,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ShajeenHeadingText,
                    fontSize = 13.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ShajeenSecondaryText,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.5.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
