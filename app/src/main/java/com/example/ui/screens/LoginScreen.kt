package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.LocationData
import com.example.data.entity.AgencySettingsEntity
import com.example.data.entity.ClientEntity

// User-specified brand colors matching the Reference Design
private val ShajeenDarkBlue = Color(0xFF0B2545)
private val ShajeenPrimaryBlue = Color(0xFF159BD3)
private val ShajeenLightBlue = Color(0xFFEAF7FC)
private val ShajeenWhite = Color(0xFFFFFFFF)
private val ShajeenGold = Color(0xFFD6A84F)
private val ShajeenMutedText = Color(0xFF64748B)
private val ShajeenBorderColor = Color(0xFFE2E8F0)

enum class AuthMode {
    LOGIN,
    REGISTER
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    agencySettings: AgencySettingsEntity?,
    allClients: List<ClientEntity> = emptyList(),
    currentLanguage: com.example.ui.locale.AppLanguage = com.example.ui.locale.LocalAppLanguage.current,
    onLanguageSelected: (com.example.ui.locale.AppLanguage) -> Unit = {},
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
    onOpenService: ((category: String) -> Unit)? = null,
    onOpenElectronicBooking: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val strings = com.example.ui.locale.LocalAppStrings.current
    val scrollState = rememberScrollState()

    // Smooth entry animation state
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    // Toggle between Login & Register forms
    var currentAuthMode by remember { mutableStateOf(AuthMode.LOGIN) }

    // Quick Login by Phone & Password states
    var loginPhoneNumber by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var loginPhoneError by remember { mutableStateOf(false) }

    // Registration Form states
    var regFullName by remember { mutableStateOf("") }
    var regPhoneNumber by remember { mutableStateOf("") }
    val idTypes = listOf("بطاقة شخصية", "جواز سفر", "بطاقة عائلية")
    var regSelectedIdType by remember { mutableStateOf(idTypes[0]) }
    var regIdTypeExpanded by remember { mutableStateOf(false) }
    var regIdNumber by remember { mutableStateOf("") }
    var regPassword by remember { mutableStateOf("") }
    var regConfirmPassword by remember { mutableStateOf("") }
    var regPasswordVisible by remember { mutableStateOf(false) }

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

    // Tech Support Dialog state
    var showSupportDialog by remember { mutableStateOf(false) }

    val agencyPhone = agencySettings?.phone1?.ifBlank { "770038009" } ?: "770038009"
    val agencyWhatsApp = agencySettings?.whatsappNumber?.ifBlank { "770038009" } ?: "770038009"
    val agencyAddress = agencySettings?.address ?: "صنعاء - شارع خولان - جوار السلامي لمواد البناء"

    fun openWhatsApp() {
        try {
            val clean = agencyWhatsApp.replace("+", "").replace(" ", "").trim()
            val url = if (clean.startsWith("967")) "https://wa.me/$clean" else "https://wa.me/967$clean"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "الواتساب: $agencyWhatsApp", Toast.LENGTH_SHORT).show()
        }
    }

    fun makePhoneCall() {
        try {
            val clean = agencyPhone.replace(" ", "").trim()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$clean"))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "الاتصال: $agencyPhone", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        ShajeenLightBlue,
                        Color(0xFFF6FBFE),
                        ShajeenWhite
                    )
                )
            )
    ) {
        // High-fidelity Travel & Tourism Illustration Background (Transparent & Elegant)
        Image(
            painter = painterResource(id = R.drawable.img_login_travel_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.85f)
        )

        // Soft ambient vignette gradient to ensure perfect contrast for the form
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.25f),
                            Color.White.copy(alpha = 0.45f),
                            Color.White.copy(alpha = 0.75f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ==========================================
            // TOP BAR: "بوابة الإدارة" 🔒
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Interactive Language Switcher (1-tap toggle between Arabic and English)
                com.example.ui.components.LanguageSwitcherPill(
                    currentLanguage = currentLanguage,
                    onLanguageSelected = onLanguageSelected
                )

                // Dedicated "بوابة الإدارة 🔒" button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = ShajeenWhite,
                    border = BorderStroke(1.2.dp, ShajeenGold),
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { showAdminDialog = true }
                        .testTag("admin_login_header_btn")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "قفل بوابة الإدارة",
                            tint = ShajeenGold,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "بوابة الإدارة",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = ShajeenDarkBlue,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ==========================================
            // HEADER & OFFICIAL LOGO
            // ==========================================
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { -30 }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Agency Official Logo Card (Exact Official Brand Emblem with Arabic Text)
                    Surface(
                        shape = RoundedCornerShape(28.dp),
                        color = Color.Transparent,
                        shadowElevation = 6.dp,
                        modifier = Modifier
                            .size(175.dp)
                            .testTag("official_logo_card")
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_shajeen_logo),
                            contentDescription = "شعار وكالة شجين للسفريات والسياحة",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Agency Slogan
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ShajeenLightBlue,
                            border = BorderStroke(1.dp, ShajeenPrimaryBlue.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "✨  رحلتك تبدأ معنا  ✨",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = ShajeenPrimaryBlue,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.5.sp
                                ),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ==========================================
            // CARD: LOGIN OR REGISTRATION
            // ==========================================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_main_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = ShajeenWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(1.2.dp, ShajeenBorderColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                ) {
                    if (currentAuthMode == AuthMode.LOGIN) {
                        // ------------------------------------------
                        // LOGIN FORM
                        // ------------------------------------------
                        Text(
                            text = "مرحبًا بك 👋",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = ShajeenDarkBlue,
                                fontSize = 21.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "سجّل دخولك للوصول إلى حسابك وحجوزاتك",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ShajeenMutedText,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Phone Number Field
                        Text(
                            text = "رقم الهاتف *",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = ShajeenDarkBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = loginPhoneNumber,
                            onValueChange = {
                                loginPhoneNumber = it
                                loginPhoneError = false
                            },
                            placeholder = {
                                Text(
                                    text = "أدخل رقم هاتفك",
                                    color = ShajeenMutedText.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = "أيقونة الهاتف",
                                    tint = ShajeenPrimaryBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            isError = loginPhoneError,
                            supportingText = {
                                if (loginPhoneError) {
                                    Text(
                                        text = "يرجى إدخال رقم هاتف صحيح للمتابعة",
                                        color = MaterialTheme.colorScheme.error,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = ShajeenDarkBlue,
                                unfocusedTextColor = ShajeenDarkBlue,
                                focusedBorderColor = ShajeenPrimaryBlue,
                                unfocusedBorderColor = ShajeenBorderColor,
                                focusedContainerColor = ShajeenWhite,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_phone_input")
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Password Field
                        Text(
                            text = "كلمة المرور",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = ShajeenDarkBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = loginPassword,
                            onValueChange = { loginPassword = it },
                            placeholder = {
                                Text(
                                    text = "أدخل كلمة المرور",
                                    color = ShajeenMutedText.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "أيقونة كلمة المرور",
                                    tint = ShajeenPrimaryBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                        contentDescription = if (isPasswordVisible) "إخفاء كلمة المرور" else "إظهار كلمة المرور",
                                        tint = ShajeenPrimaryBlue,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = ShajeenDarkBlue,
                                unfocusedTextColor = ShajeenDarkBlue,
                                focusedBorderColor = ShajeenPrimaryBlue,
                                unfocusedBorderColor = ShajeenBorderColor,
                                focusedContainerColor = ShajeenWhite,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_password_input")
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Big Primary Blue Login Button: "تسجيل الدخول →"
                        val interactionSource = remember { MutableInteractionSource() }
                        val isPressed by interactionSource.collectIsPressedAsState()
                        val scale by animateFloatAsState(
                            targetValue = if (isPressed) 0.98f else 1f,
                            animationSpec = tween(100, easing = FastOutSlowInEasing),
                            label = "loginButtonScale"
                        )

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
                                containerColor = ShajeenPrimaryBlue,
                                contentColor = ShajeenWhite
                            ),
                            shape = RoundedCornerShape(14.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                            interactionSource = interactionSource,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .scale(scale)
                                .testTag("login_submit_btn")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "تسجيل الدخول",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 16.sp,
                                        color = ShajeenWhite
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "دخول",
                                    tint = ShajeenWhite,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // "ليس لديك حساب؟ إنشاء حساب جديد"
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ليس لديك حساب؟ ",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = ShajeenMutedText,
                                    fontSize = 13.5.sp
                                )
                            )
                            Text(
                                text = "إنشاء حساب جديد",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = ShajeenPrimaryBlue,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable { currentAuthMode = AuthMode.REGISTER }
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                                    .testTag("switch_to_register_btn")
                            )
                        }
                    } else {
                        // ------------------------------------------
                        // REGISTRATION FORM
                        // ------------------------------------------
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "إنشاء حساب جديد 👤",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = ShajeenDarkBlue,
                                    fontSize = 20.sp
                                )
                            )
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ShajeenLightBlue,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { currentAuthMode = AuthMode.LOGIN }
                            ) {
                                Text(
                                    text = "العودة للدخول",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ShajeenPrimaryBlue,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.5.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "سجّل بياناتك للاستمتاع بجميع خدمات وكالة شجين وحجز رحلاتك",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ShajeenMutedText,
                                fontSize = 12.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Full Name
                        OutlinedTextField(
                            value = regFullName,
                            onValueChange = {
                                regFullName = it
                                regNameError = false
                            },
                            label = { Text("الاسم الكامل *") },
                            placeholder = { Text("أدخل اسمك كما في الهوية") },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null, tint = ShajeenPrimaryBlue)
                            },
                            isError = regNameError,
                            supportingText = {
                                if (regNameError) {
                                    Text("يرجى إدخال الاسم كاملاً", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenPrimaryBlue,
                                unfocusedBorderColor = ShajeenBorderColor,
                                focusedContainerColor = ShajeenWhite,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_full_name_input"),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Phone Number
                        OutlinedTextField(
                            value = regPhoneNumber,
                            onValueChange = {
                                regPhoneNumber = it
                                regPhoneError = false
                            },
                            label = { Text("رقم الهاتف *") },
                            placeholder = { Text("مثال: 777777777") },
                            leadingIcon = {
                                Icon(Icons.Default.Phone, contentDescription = null, tint = ShajeenPrimaryBlue)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            isError = regPhoneError,
                            supportingText = {
                                if (regPhoneError) {
                                    Text("يرجى إدخال رقم هاتف صحيح", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenPrimaryBlue,
                                unfocusedBorderColor = ShajeenBorderColor,
                                focusedContainerColor = ShajeenWhite,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_phone_input"),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // ID Type Dropdown
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
                                    Icon(Icons.Default.CreditCard, contentDescription = null, tint = ShajeenPrimaryBlue)
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = regIdTypeExpanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ShajeenPrimaryBlue,
                                    unfocusedBorderColor = ShajeenBorderColor,
                                    focusedContainerColor = ShajeenWhite,
                                    unfocusedContainerColor = Color(0xFFF8FAFC)
                                ),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
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

                        Spacer(modifier = Modifier.height(8.dp))

                        // ID Number
                        OutlinedTextField(
                            value = regIdNumber,
                            onValueChange = {
                                regIdNumber = it
                                regIdError = false
                            },
                            label = { Text("رقم الهوية أو الجواز *") },
                            placeholder = { Text("أدخل رقم الهوية أو الجواز") },
                            leadingIcon = {
                                Icon(Icons.Default.Badge, contentDescription = null, tint = ShajeenPrimaryBlue)
                            },
                            isError = regIdError,
                            supportingText = {
                                if (regIdError) {
                                    Text("يرجى إدخال رقم الهوية", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenPrimaryBlue,
                                unfocusedBorderColor = ShajeenBorderColor,
                                focusedContainerColor = ShajeenWhite,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reg_id_number_input"),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Password
                        OutlinedTextField(
                            value = regPassword,
                            onValueChange = {
                                regPassword = it
                                regPasswordError = false
                            },
                            label = { Text("كلمة المرور *") },
                            placeholder = { Text("اختر كلمة مرور لحسابك") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ShajeenPrimaryBlue)
                            },
                            trailingIcon = {
                                IconButton(onClick = { regPasswordVisible = !regPasswordVisible }) {
                                    Icon(
                                        imageVector = if (regPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                        contentDescription = null,
                                        tint = ShajeenPrimaryBlue
                                    )
                                }
                            },
                            visualTransformation = if (regPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            isError = regPasswordError,
                            supportingText = {
                                if (regPasswordError) {
                                    Text("كلمة المرور يجب أن لا تقل عن 4 خانات", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenPrimaryBlue,
                                unfocusedBorderColor = ShajeenBorderColor,
                                focusedContainerColor = ShajeenWhite,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Confirm Password
                        OutlinedTextField(
                            value = regConfirmPassword,
                            onValueChange = {
                                regConfirmPassword = it
                                regConfirmPasswordError = false
                            },
                            label = { Text("تأكيد كلمة المرور *") },
                            placeholder = { Text("أعد إدخال كلمة المرور") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ShajeenPrimaryBlue)
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
                                focusedBorderColor = ShajeenPrimaryBlue,
                                unfocusedBorderColor = ShajeenBorderColor,
                                focusedContainerColor = ShajeenWhite,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Country Dropdown
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
                                    Icon(Icons.Default.Public, contentDescription = null, tint = ShajeenPrimaryBlue)
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = regCountryExpanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ShajeenPrimaryBlue,
                                    unfocusedBorderColor = ShajeenBorderColor,
                                    focusedContainerColor = ShajeenWhite,
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

                        Spacer(modifier = Modifier.height(8.dp))

                        // City Dropdown
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
                                    Icon(Icons.Default.LocationCity, contentDescription = null, tint = ShajeenPrimaryBlue)
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = regCityExpanded)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ShajeenPrimaryBlue,
                                    unfocusedBorderColor = ShajeenBorderColor,
                                    focusedContainerColor = ShajeenWhite,
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

                        Spacer(modifier = Modifier.height(8.dp))

                        // Area Name
                        OutlinedTextField(
                            value = regAreaName,
                            onValueChange = { regAreaName = it },
                            label = { Text("الحي أو الشارع السكني") },
                            placeholder = { Text("مثال: شارع خولان") },
                            leadingIcon = {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = ShajeenPrimaryBlue)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenPrimaryBlue,
                                unfocusedBorderColor = ShajeenBorderColor,
                                focusedContainerColor = ShajeenWhite,
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Submit Registration Button
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
                                containerColor = ShajeenPrimaryBlue,
                                contentColor = ShajeenWhite
                            ),
                            shape = RoundedCornerShape(14.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .testTag("reg_submit_btn")
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ShajeenWhite)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "إنشاء الحساب والمتابعة",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Link back to Login
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "لديك حساب بالفعل؟ ",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = ShajeenMutedText,
                                    fontSize = 13.5.sp
                                )
                            )
                            Text(
                                text = "تسجيل الدخول",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = ShajeenPrimaryBlue,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable { currentAuthMode = AuthMode.LOGIN }
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // ==========================================
            // SECTION: "خدماتنا" (3 INTERACTIVE CARDS)
            // ==========================================
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(ShajeenPrimaryBlue)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "خدماتنا",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = ShajeenDarkBlue,
                            fontSize = 18.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Card 1: ✈️ رحلات طيران
                    ServiceFeatureTile(
                        iconEmoji = "✈️",
                        title = "رحلات طيران",
                        subtitle = "حجوزات دولية ومحلية",
                        onClick = {
                            if (onOpenService != null) {
                                onOpenService("طيران")
                            } else {
                                Toast.makeText(context, "استعراض رحلات وحجوزات الطيران", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        testTag = "service_tile_flights"
                    )

                    // Card 2: 🕋 حج وعمرة
                    ServiceFeatureTile(
                        iconEmoji = "🕋",
                        title = "حج وعمرة",
                        subtitle = "برامج ميسرة ومميزة",
                        onClick = {
                            if (onOpenService != null) {
                                onOpenService("عمرة")
                            } else {
                                Toast.makeText(context, "استعراض برامج الحج والعمرة", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        testTag = "service_tile_umrah"
                    )

                    // Card 3: 🛂 جوازات وتأشيرات
                    ServiceFeatureTile(
                        iconEmoji = "🛂",
                        title = "جوازات وتأشيرات",
                        subtitle = "معاملات وتخليص سريع",
                        onClick = {
                            if (onOpenElectronicBooking != null) {
                                onOpenElectronicBooking()
                            } else if (onOpenService != null) {
                                onOpenService("تأشيرات")
                            } else {
                                Toast.makeText(context, "استعراض خدمات التأشيرات والجوازات", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        testTag = "service_tile_visas"
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // ==========================================
            // SECTION: "تحتاج إلى مساعدة؟" (CONTACT)
            // ==========================================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("contact_help_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = ShajeenWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.2.dp, ShajeenBorderColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "تحتاج إلى مساعدة؟",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = ShajeenDarkBlue,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "فريق خدمة العملاء متواجد لمساعدتكم وإتمام حجوزاتكم بكل سرعة",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ShajeenMutedText,
                            fontSize = 11.5.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // 1. WhatsApp Button
                        ContactChannelButton(
                            title = "واتساب",
                            subtitle = "محادثة فورية",
                            icon = Icons.Default.Chat,
                            iconTint = Color(0xFF16A34A),
                            badgeColor = Color(0xFFDCFCE7),
                            borderColor = Color(0xFF86EFAC),
                            onClick = { openWhatsApp() },
                            modifier = Modifier.weight(1f),
                            testTag = "contact_btn_whatsapp"
                        )

                        // 2. Call Button
                        ContactChannelButton(
                            title = "اتصل بنا",
                            subtitle = "مباشر",
                            icon = Icons.Default.Call,
                            iconTint = ShajeenPrimaryBlue,
                            badgeColor = ShajeenLightBlue,
                            borderColor = ShajeenPrimaryBlue.copy(alpha = 0.35f),
                            onClick = { makePhoneCall() },
                            modifier = Modifier.weight(1f),
                            testTag = "contact_btn_call"
                        )

                        // 3. Technical Support Button
                        ContactChannelButton(
                            title = "الدعم الفني",
                            subtitle = "استفسارات",
                            icon = Icons.Default.HeadsetMic,
                            iconTint = ShajeenGold,
                            badgeColor = Color(0xFFFEF3C7),
                            borderColor = ShajeenGold.copy(alpha = 0.4f),
                            onClick = { showSupportDialog = true },
                            modifier = Modifier.weight(1f),
                            testTag = "contact_btn_support"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ==========================================
            // FOOTER: APP UPDATE & VERSION BADGE
            // ==========================================
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = ShajeenWhite.copy(alpha = 0.9f),
                border = BorderStroke(1.dp, ShajeenPrimaryBlue.copy(alpha = 0.25f)),
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "وكالة شجين للسفريات والسياحة • الإصدار 3.0 (تحديث جديد)",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ShajeenDarkBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.5.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // ==========================================
        // DIALOG: ADMIN PORTAL LOGIN
        // ==========================================
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
                            color = ShajeenGold.copy(alpha = 0.15f),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = ShajeenGold,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "بوابة الإدارة / المالك",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = ShajeenDarkBlue
                            )
                        )
                    }
                },
                text = {
                    Column {
                        Text(
                            text = "أدخل رمز المرور السري الخاص بإدارة وكالة شجين للمتابعة والتحكم في الحجوزات والخدمات والمحافظ.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ShajeenMutedText,
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
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ShajeenPrimaryBlue)
                            },
                            trailingIcon = {
                                IconButton(onClick = { adminPinVisible = !adminPinVisible }) {
                                    Icon(
                                        imageVector = if (adminPinVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                        contentDescription = "إظهار الرمز",
                                        tint = ShajeenPrimaryBlue
                                    )
                                }
                            },
                            visualTransformation = if (adminPinVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            singleLine = true,
                            isError = adminPinError,
                            supportingText = {
                                if (adminPinError) {
                                    Text("رمز المرور غير صحيح، يرجى إعادة المحاولة", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenPrimaryBlue,
                                unfocusedBorderColor = ShajeenBorderColor
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_pin_input")
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
                            contentColor = ShajeenWhite
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
                        Text("إلغاء", color = ShajeenMutedText)
                    }
                }
            )
        }

        // ==========================================
        // DIALOG: TECH SUPPORT & CONTACT INFO
        // ==========================================
        if (showSupportDialog) {
            AlertDialog(
                onDismissRequest = { showSupportDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.HeadsetMic, contentDescription = null, tint = ShajeenPrimaryBlue)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "الدعم الفني وخدمة العملاء",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShajeenDarkBlue
                            )
                        )
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "وكالة شجين للسفريات والسياحة في خدمتكم يومياً على مدار الساعة.",
                            style = MaterialTheme.typography.bodyMedium.copy(color = ShajeenDarkBlue)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "📍 العنوان: $agencyAddress",
                            style = MaterialTheme.typography.bodySmall.copy(color = ShajeenMutedText)
                        )
                        Text(
                            text = "📞 هاتف التواصل: $agencyPhone",
                            style = MaterialTheme.typography.bodySmall.copy(color = ShajeenMutedText)
                        )
                        Text(
                            text = "💬 واتساب: $agencyWhatsApp",
                            style = MaterialTheme.typography.bodySmall.copy(color = ShajeenMutedText)
                        )
                        Text(
                            text = "⏰ أوقات الدوام: يومياً من 8 صباحاً حتى 10 مساءً",
                            style = MaterialTheme.typography.bodySmall.copy(color = ShajeenMutedText)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSupportDialog = false
                            openWhatsApp()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ShajeenPrimaryBlue)
                    ) {
                        Text("محادثة واتساب", color = ShajeenWhite)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSupportDialog = false }) {
                        Text("إغلاق", color = ShajeenMutedText)
                    }
                }
            )
        }
    }
}

// ==========================================
// COMPONENT: SERVICE FEATURE TILE (الخدمات)
// ==========================================
@Composable
private fun ServiceFeatureTile(
    iconEmoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = tween(100),
        label = "tileScale"
    )

    Surface(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(18.dp))
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
            .testTag(testTag),
        color = ShajeenWhite,
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.2.dp, ShajeenBorderColor),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = ShajeenLightBlue,
                border = BorderStroke(1.dp, ShajeenPrimaryBlue.copy(alpha = 0.25f)),
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = iconEmoji,
                        fontSize = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = ShajeenDarkBlue,
                    fontSize = 12.5.sp
                ),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ShajeenMutedText,
                    fontSize = 10.sp,
                    lineHeight = 13.sp
                ),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

// ==========================================
// COMPONENT: CONTACT CHANNEL BUTTON
// ==========================================
@Composable
private fun ContactChannelButton(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    badgeColor: Color,
    borderColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag(testTag),
        color = ShajeenWhite,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.2.dp, borderColor),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = badgeColor,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconTint,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = ShajeenDarkBlue,
                    fontSize = 12.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ShajeenMutedText,
                    fontSize = 10.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
