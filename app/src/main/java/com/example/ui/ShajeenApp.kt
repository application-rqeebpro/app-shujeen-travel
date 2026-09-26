package com.example.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.ElectronicBookingScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MyBookingsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue
import kotlinx.coroutines.launch

@Composable
fun ShajeenApp(
    viewModel: ShajeenViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val currentClient by viewModel.currentClient.collectAsStateWithLifecycle()
    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsStateWithLifecycle()

    val agencySettings by viewModel.agencySettings.collectAsStateWithLifecycle()
    val agencyNews by viewModel.agencyNews.collectAsStateWithLifecycle()
    val filteredServices by viewModel.filteredServices.collectAsStateWithLifecycle()
    val allServices by viewModel.allServices.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val clientBookings by viewModel.currentClientBookings.collectAsStateWithLifecycle()
    val allClients by viewModel.allClients.collectAsStateWithLifecycle()
    val allBookings by viewModel.allBookings.collectAsStateWithLifecycle()
    val allElectronicBookings by viewModel.allElectronicBookings.collectAsStateWithLifecycle()
    val clientElectronicBookings by viewModel.currentClientElectronicBookings.collectAsStateWithLifecycle()
    val activeWallets by viewModel.activePaymentWallets.collectAsStateWithLifecycle()
    val allWallets by viewModel.allPaymentWallets.collectAsStateWithLifecycle()
    val visaRequirements by viewModel.allVisaRequirements.collectAsStateWithLifecycle()
    val clientNotifications by viewModel.clientNotifications.collectAsStateWithLifecycle()

    // Global Admin PIN Dialog state (Password: 770038)
    var showGlobalAdminDialog by remember { mutableStateOf(false) }
    var adminPinInput by remember { mutableStateOf("") }
    var adminPinHasError by remember { mutableStateOf(false) }

    // Mandatory RTL (Right-to-Left) Arabic layout direction support
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                // Show 5-item bottom navigation bar matching the Reference Image when logged in
                if (currentScreen != AppScreen.LOGIN) {
                    NavigationBar(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .testTag("main_bottom_nav_bar"),
                        containerColor = Color.White,
                        tonalElevation = 8.dp
                    ) {
                        // 1. الحساب (Account / Profile)
                        NavigationBarItem(
                            selected = (currentScreen == AppScreen.PROFILE),
                            onClick = { viewModel.navigateTo(AppScreen.PROFILE) },
                            icon = {
                                Icon(
                                    imageVector = if (currentScreen == AppScreen.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                                    contentDescription = "الحساب",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = "الحساب",
                                    fontSize = 11.sp,
                                    fontWeight = if (currentScreen == AppScreen.PROFILE) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ShajeenSkyBlue,
                                selectedTextColor = ShajeenSkyBlue,
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B),
                                indicatorColor = com.example.ui.theme.ShajeenSkyContainer
                            ),
                            modifier = Modifier.testTag("nav_item_profile")
                        )

                        // 2. حجوزاتي (My Bookings)
                        NavigationBarItem(
                            selected = (currentScreen == AppScreen.MY_BOOKINGS),
                            onClick = { viewModel.navigateTo(AppScreen.MY_BOOKINGS) },
                            icon = {
                                Icon(
                                    imageVector = if (currentScreen == AppScreen.MY_BOOKINGS) Icons.Filled.Bookmarks else Icons.Outlined.BookmarkBorder,
                                    contentDescription = "حجوزاتي",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = "حجوزاتي",
                                    fontSize = 11.sp,
                                    fontWeight = if (currentScreen == AppScreen.MY_BOOKINGS) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ShajeenSkyBlue,
                                selectedTextColor = ShajeenSkyBlue,
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B),
                                indicatorColor = com.example.ui.theme.ShajeenSkyContainer
                            ),
                            modifier = Modifier.testTag("nav_item_bookings")
                        )

                        // 3. الرئيسية (Home) with Center Elevated Circular Blue Button matching image
                        val isHomeSelected = (currentScreen == AppScreen.DASHBOARD)
                        NavigationBarItem(
                            selected = isHomeSelected,
                            onClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
                            icon = {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isHomeSelected) ShajeenSkyBlue else Color(0xFFE0F2FE),
                                    shadowElevation = if (isHomeSelected) 4.dp else 1.dp,
                                    modifier = Modifier.size(46.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Filled.Home,
                                            contentDescription = "الرئيسية",
                                            tint = if (isHomeSelected) Color.White else ShajeenSkyBlue,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                }
                            },
                            label = {
                                Text(
                                    text = "الرئيسية",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = ShajeenSkyBlue
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = ShajeenSkyBlue,
                                unselectedTextColor = ShajeenSkyBlue,
                                indicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.testTag("nav_item_home")
                        )

                        // 4. العروض (Offers)
                        NavigationBarItem(
                            selected = false,
                            onClick = {
                                viewModel.navigateTo(AppScreen.DASHBOARD)
                                viewModel.setCategory("البرامج السياحية")
                                Toast.makeText(context, "استعراض أحدث عروض وكالة شجين", Toast.LENGTH_SHORT).show()
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Star,
                                    contentDescription = "العروض",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = "العروض",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B),
                                indicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.testTag("nav_item_offers")
                        )

                        // 5. المزيد / إدارة (More / Admin)
                        val isAdminScreen = (currentScreen == AppScreen.ADMIN)
                        NavigationBarItem(
                            selected = isAdminScreen,
                            onClick = {
                                if (isAdminLoggedIn) {
                                    viewModel.navigateTo(AppScreen.ADMIN)
                                } else {
                                    showGlobalAdminDialog = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (isAdminLoggedIn) Icons.Filled.AdminPanelSettings else Icons.Outlined.MoreHoriz,
                                    contentDescription = "المزيد",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = if (isAdminLoggedIn) "لوحة المالك" else "المزيد",
                                    fontSize = 11.sp,
                                    fontWeight = if (isAdminScreen) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ShajeenGold,
                                selectedTextColor = ShajeenGold,
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B),
                                indicatorColor = ShajeenGold.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.testTag("nav_item_more")
                        )
                    }
                }
            }
        ) { innerPadding ->
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    AppScreen.LOGIN -> {
                        LoginScreen(
                            agencySettings = agencySettings,
                            allClients = allClients,
                            onRegisterOrLogin = { name, phone, idType, idNum, country, city, dist, area ->
                                viewModel.registerOrLoginClient(
                                    name, phone, idType, idNum, country, city, dist, area
                                ) {
                                    Toast.makeText(context, "أهلاً بك يا $name في وكالة شجين", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onQuickPhoneLogin = { phone ->
                                viewModel.quickLoginByPhone(phone) { found ->
                                    if (found) {
                                        Toast.makeText(context, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "الرقم غير مسجل، يرجى التوجه لتبويب \"إنشاء حساب جديد\"", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            onAdminLogin = { pin, onResult ->
                                viewModel.loginAdmin(pin) { success ->
                                    onResult(success)
                                    if (success) {
                                        Toast.makeText(context, "تم الدخول إلى لوحة تحكم المالك", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "رمز المرور غير صحيح", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        )
                    }

                    AppScreen.DASHBOARD -> {
                        DashboardScreen(
                            currentClient = currentClient,
                            agencySettings = agencySettings,
                            newsList = agencyNews,
                            services = filteredServices,
                            selectedCategory = selectedCategory,
                            searchQuery = searchQuery,
                            onCategorySelected = { viewModel.setCategory(it) },
                            onSearchChanged = { viewModel.setSearchQuery(it) },
                            onBookService = { service, date, passengers, notes ->
                                viewModel.createBooking(service, date, passengers, notes) { success ->
                                    if (success) {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("تم إرسال طلب حجز (${service.title}) بنجاح! سيتم التواصل معك قريباً.")
                                        }
                                    }
                                }
                            },
                            onServiceRequestSubmitted = { title, category, name, phone, date, count, details ->
                                viewModel.submitServiceRequest(title, category, name, phone, date, count, details) { success, id, code ->
                                    if (success) {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("تم استلام طلبك ($title) برقم $code بنجاح!")
                                        }
                                    }
                                }
                            },
                            onNavigateToElectronicBooking = {
                                viewModel.navigateTo(AppScreen.ELECTRONIC_BOOKING)
                            },
                            onOwnerLoginClick = {
                                if (isAdminLoggedIn) {
                                    viewModel.navigateTo(AppScreen.ADMIN)
                                } else {
                                    showGlobalAdminDialog = true
                                }
                            },
                            onLogout = { viewModel.logoutClient() }
                        )
                    }

                    AppScreen.ELECTRONIC_BOOKING -> {
                        ElectronicBookingScreen(
                            currentClient = currentClient,
                            wallets = activeWallets,
                            visaRequirements = visaRequirements,
                            onBack = { viewModel.navigateTo(AppScreen.DASHBOARD) },
                            onSubmitBooking = { fullName, phone, email, country, dest, tDate, vType, oVType, notes, pMethod, txNum, receipt, docs, onDone ->
                                viewModel.submitElectronicBooking(
                                    fullName = fullName,
                                    phone = phone,
                                    email = email,
                                    country = country,
                                    destination = dest,
                                    travelDate = tDate,
                                    visaType = vType,
                                    otherVisaType = oVType,
                                    notes = notes,
                                    paymentMethod = pMethod,
                                    transactionNumber = txNum,
                                    paymentReceipt = receipt,
                                    documents = docs
                                ) { success: Boolean, bNum: String ->
                                    onDone(success, bNum)
                                    if (success) {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("تم استلام طلبك بنجاح برقم: $bNum")
                                        }
                                    }
                                }
                            }
                        )
                    }

                    AppScreen.MY_BOOKINGS -> {
                        MyBookingsScreen(
                            bookings = clientBookings,
                            electronicBookings = clientElectronicBookings,
                            notifications = clientNotifications,
                            onCancelBooking = { booking ->
                                viewModel.cancelBooking(booking)
                                Toast.makeText(context, "تم إلغاء طلب الحجز", Toast.LENGTH_SHORT).show()
                            },
                            onBrowseServices = { viewModel.navigateTo(AppScreen.DASHBOARD) },
                            onNavigateToElectronicBooking = { viewModel.navigateTo(AppScreen.ELECTRONIC_BOOKING) }
                        )
                    }

                    AppScreen.PROFILE -> {
                        ProfileScreen(
                            currentClient = currentClient,
                            agencySettings = agencySettings,
                            onLogout = { viewModel.logoutClient() }
                        )
                    }

                    AppScreen.ADMIN -> {
                        AdminScreen(
                            services = allServices,
                            clients = allClients,
                            bookings = allBookings,
                            newsList = agencyNews,
                            agencySettings = agencySettings,
                            electronicBookings = allElectronicBookings,
                            paymentWallets = allWallets,
                            visaRequirements = visaRequirements,
                            onUpdateElectronicBookingStatus = { bId, uId, bNum, status, notes ->
                                viewModel.updateElectronicBookingStatus(bId, uId, bNum, status, notes)
                            },
                            onUpdateElectronicPaymentStatus = { bId, uId, bNum, pStatus ->
                                viewModel.updateElectronicPaymentStatus(bId, uId, bNum, pStatus)
                            },
                            onRequestAdditionalDocs = { bId, uId, bNum, docDesc ->
                                viewModel.requestAdditionalDocuments(bId, uId, bNum, docDesc)
                            },
                            onDeleteElectronicBooking = { eBooking ->
                                viewModel.deleteElectronicBooking(eBooking)
                            },
                            onSavePaymentWallet = { wallet ->
                                viewModel.savePaymentWallet(wallet)
                            },
                            onDeletePaymentWallet = { wallet ->
                                viewModel.deletePaymentWallet(wallet)
                            },
                            onSaveService = { service -> viewModel.saveService(service) {} },
                            onDeleteService = { service -> viewModel.deleteService(service) },
                            onDeleteClient = { client -> viewModel.deleteClient(client) },
                            onUpdateBookingStatus = { id, status -> viewModel.updateBookingStatus(id, status) },
                            onUpdateAgencySettings = { addr, p1, p2, p3, p4, ann ->
                                viewModel.updateAgencySettings(addr, p1, p2, p3, p4, ann) {}
                            },
                            onAddNews = { title, content, date, tag ->
                                viewModel.addNews(title, content, date, tag) {}
                            },
                            onDeleteNews = { news -> viewModel.deleteNews(news) },
                            onLogoutAdmin = { viewModel.logoutAdmin() }
                        )
                    }
                }
            }
        }

        // Global Owner PIN Authentication Dialog (Password: 770038)
        if (showGlobalAdminDialog) {
            AlertDialog(
                onDismissRequest = {
                    showGlobalAdminDialog = false
                    adminPinInput = ""
                    adminPinHasError = false
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
                            text = "بوابة مالك الوكالة",
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
                            text = "أدخل رمز المرور السري للدخول إلى لوحة تحكم المالك وإدارة الحجوزات والخدمات والطلبات.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ShajeenSecondaryText,
                                lineHeight = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        OutlinedTextField(
                            value = adminPinInput,
                            onValueChange = {
                                adminPinInput = it
                                adminPinHasError = false
                            },
                            label = { Text("رمز المرور السري") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ShajeenSkyBlue)
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            isError = adminPinHasError,
                            supportingText = {
                                if (adminPinHasError) {
                                    Text("رمز المرور غير صحيح، يرجى المحاولة مجدداً", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ShajeenSkyBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("global_admin_pin_input"),
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val clean = adminPinInput.trim()
                            if (clean.isNotBlank()) {
                                viewModel.loginAdmin(clean) { success ->
                                    if (success) {
                                        showGlobalAdminDialog = false
                                        adminPinInput = ""
                                        adminPinHasError = false
                                        Toast.makeText(context, "تم الدخول إلى لوحة تحكم المالك", Toast.LENGTH_SHORT).show()
                                    } else {
                                        adminPinHasError = true
                                    }
                                }
                            } else {
                                adminPinHasError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ShajeenDarkBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("دخول")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showGlobalAdminDialog = false
                            adminPinInput = ""
                            adminPinHasError = false
                        }
                    ) {
                        Text("إلغاء", color = ShajeenSecondaryText)
                    }
                }
            )
        }
    }
}
