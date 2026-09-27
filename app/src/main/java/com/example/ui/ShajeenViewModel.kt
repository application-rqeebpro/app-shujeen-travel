package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.TravelRepository
import com.example.data.entity.AgencyNewsEntity
import com.example.data.entity.AgencySettingsEntity
import com.example.data.entity.BookingEntity
import com.example.data.entity.ClientEntity
import com.example.data.entity.TravelServiceEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    SPLASH,
    LOGIN,
    DASHBOARD,
    ELECTRONIC_BOOKING,
    MY_BOOKINGS,
    PROFILE,
    ADMIN
}

class ShajeenViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TravelRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = TravelRepository(db)
        viewModelScope.launch {
            repository.ensureDefaultData()
        }
    }

    // Current Screen & Auth State
    private val _currentScreen = MutableStateFlow(AppScreen.SPLASH)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _currentClient = MutableStateFlow<ClientEntity?>(null)
    val currentClient: StateFlow<ClientEntity?> = _currentClient.asStateFlow()

    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    // Agency Settings
    val agencySettings: StateFlow<AgencySettingsEntity?> = repository.agencySettings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AgencySettingsEntity()
        )

    // Agency News
    val agencyNews: StateFlow<List<AgencyNewsEntity>> = repository.allNews
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Services & Filtering
    val allServices: StateFlow<List<TravelServiceEntity>> = repository.allServices
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedCategory = MutableStateFlow("الكل")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Filtered Services for UI
    val filteredServices: StateFlow<List<TravelServiceEntity>> = combine(
        allServices,
        _selectedCategory,
        _searchQuery
    ) { services, category, query ->
        services.filter { service ->
            val matchesCategory = (category == "الكل" || service.category == category)
            val matchesQuery = query.isBlank() ||
                service.title.contains(query, ignoreCase = true) ||
                service.description.contains(query, ignoreCase = true) ||
                service.subtitle.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Client Bookings
    val currentClientBookings: StateFlow<List<BookingEntity>> = _currentClient.flatMapLatest { client ->
        if (client != null) {
            repository.getBookingsByClient(client.id)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Admin Data
    val allClients: StateFlow<List<ClientEntity>> = repository.allClients
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allBookings: StateFlow<List<BookingEntity>> = repository.allBookings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Electronic Bookings Flows
    val allElectronicBookings: StateFlow<List<com.example.data.entity.ElectronicBookingEntity>> =
        repository.allElectronicBookings
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val currentClientElectronicBookings: StateFlow<List<com.example.data.entity.ElectronicBookingEntity>> =
        _currentClient.flatMapLatest { client ->
            if (client != null) {
                repository.getElectronicBookingsByUserId(client.id)
            } else {
                flowOf(emptyList())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val activePaymentWallets: StateFlow<List<com.example.data.entity.PaymentWalletEntity>> =
        repository.activeWallets
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val allPaymentWallets: StateFlow<List<com.example.data.entity.PaymentWalletEntity>> =
        repository.allWallets
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val allVisaRequirements: StateFlow<List<com.example.data.entity.VisaRequirementEntity>> =
        repository.allVisaRequirements
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val clientNotifications: StateFlow<List<com.example.data.entity.ClientNotificationEntity>> =
        _currentClient.flatMapLatest { client ->
            if (client != null) {
                repository.getNotificationsForUser(client.id)
            } else {
                flowOf(emptyList())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allNotifications: StateFlow<List<com.example.data.entity.ClientNotificationEntity>> =
        repository.allNotifications
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val allAdminLogs: StateFlow<List<com.example.data.entity.AdminLogEntity>> =
        repository.allAdminLogs
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Navigation methods
    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Client Auth / Registration
    fun registerOrLoginClient(
        fullName: String,
        phone: String,
        idType: String,
        idNumber: String,
        country: String,
        city: String,
        district: String,
        area: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val existing = repository.getClientByPhone(phone)
            val client = if (existing != null) {
                existing.copy(
                    fullName = fullName,
                    idType = idType,
                    idNumber = idNumber,
                    country = country,
                    city = city,
                    district = district,
                    area = area
                )
            } else {
                ClientEntity(
                    fullName = fullName,
                    phone = phone,
                    idType = idType,
                    idNumber = idNumber,
                    country = country,
                    city = city,
                    district = district,
                    area = area
                )
            }
            val id = repository.insertOrUpdateClient(client)
            val finalClient = if (existing != null) client else client.copy(id = id)
            _currentClient.value = finalClient
            _currentScreen.value = AppScreen.DASHBOARD
            onSuccess()
        }
    }

    fun quickLoginByPhone(phone: String, onFound: (Boolean) -> Unit) {
        viewModelScope.launch {
            val client = repository.getClientByPhone(phone)
            if (client != null) {
                _currentClient.value = client
                _currentScreen.value = AppScreen.DASHBOARD
                onFound(true)
            } else {
                onFound(false)
            }
        }
    }

    fun logoutClient() {
        _currentClient.value = null
        _currentScreen.value = AppScreen.LOGIN
    }

    // Admin Auth with SHA-256 & Secure Hashing
    fun loginAdmin(pin: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val settings = repository.getAgencySettingsOnce()
            val isValid = com.example.util.SecurityUtils.verifyPassword(pin, settings?.adminPasswordHash)
            if (isValid) {
                _isAdminLoggedIn.value = true
                _currentScreen.value = AppScreen.ADMIN
                repository.logAdminAction("تسجيل دخول", "تم تسجيل دخول المالك بنجاح إلى لوحة التحكم")
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun changeAdminPassword(oldPin: String, newPin: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val current = repository.getAgencySettingsOnce() ?: AgencySettingsEntity()
            if (!com.example.util.SecurityUtils.verifyPassword(oldPin, current.adminPasswordHash)) {
                onResult(false, "كلمة المرور الحالية غير صحيحة")
                return@launch
            }
            if (newPin.trim().length < 4) {
                onResult(false, "كلمة المرور الجديدة يجب أن تكون 4 خانات على الأقل")
                return@launch
            }
            val newHash = com.example.util.SecurityUtils.hashPassword(newPin)
            val updated = current.copy(adminPasswordHash = newHash)
            repository.saveAgencySettings(updated)
            repository.logAdminAction("تغيير كلمة المرور", "تم تغيير كلمة مرور مالك الوكالة بنجاح")
            onResult(true, "تم تحديث كلمة مرور المالك بنجاح")
        }
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        viewModelScope.launch {
            repository.logAdminAction("تسجيل خروج", "تم تسجيل خروج المالك من لوحة التحكم")
        }
        if (_currentClient.value != null) {
            _currentScreen.value = AppScreen.DASHBOARD
        } else {
            _currentScreen.value = AppScreen.LOGIN
        }
    }

    // Push Notifications System (Real Android Native + In-App DB)
    fun sendPushNotification(title: String, message: String, targetUserId: Long? = null, onDone: () -> Unit) {
        viewModelScope.launch {
            // 1. Trigger actual Android device Push Notification
            com.example.util.PushNotificationHelper.sendPushNotification(
                getApplication(),
                title,
                message
            )

            // 2. Persist notification in database
            if (targetUserId != null && targetUserId > 0) {
                repository.addNotification(
                    com.example.data.entity.ClientNotificationEntity(
                        userId = targetUserId,
                        title = title,
                        message = message
                    )
                )
            } else {
                // Broadcast for all users
                repository.addNotification(
                    com.example.data.entity.ClientNotificationEntity(
                        userId = 0L,
                        title = title,
                        message = message
                    )
                )
            }
            repository.logAdminAction("إرسال إشعار", "تم إرسال إشعار: $title")
            onDone()
        }
    }

    fun deleteNotification(id: Long) {
        viewModelScope.launch {
            repository.deleteNotification(id)
            repository.logAdminAction("حذف إشعار", "تم حذف الإشعار رقم #$id")
        }
    }

    fun clearAdminLogs() {
        viewModelScope.launch {
            repository.clearAdminLogs()
        }
    }

    // Booking actions
    fun createBooking(
        service: TravelServiceEntity,
        travelDate: String,
        passengersCount: Int,
        notes: String,
        onComplete: (Boolean) -> Unit
    ) {
        val client = _currentClient.value
        if (client == null) {
            onComplete(false)
            return
        }
        viewModelScope.launch {
            val booking = BookingEntity(
                clientId = client.id,
                clientName = client.fullName,
                clientPhone = client.phone,
                serviceId = service.id,
                serviceTitle = service.title,
                serviceCategory = service.category,
                travelDate = travelDate,
                passengersCount = passengersCount,
                notes = notes,
                status = "جديد"
            )
            repository.insertBooking(booking)
            onComplete(true)
        }
    }

    fun updateBookingStatus(bookingId: Long, status: String) {
        viewModelScope.launch {
            repository.updateBookingStatus(bookingId, status)
            repository.logAdminAction("تحديث حجز", "تم تحديث حالة الحجز #$bookingId إلى ($status)")
        }
    }

    fun updateBookingStatusAndNotes(bookingId: Long, status: String, adminNotes: String) {
        viewModelScope.launch {
            repository.updateBookingStatusAndNotes(bookingId, status, adminNotes)
            repository.logAdminAction("تحديث حجز وملاحظات", "تم تحديث حالة الحجز #$bookingId إلى ($status)")
        }
    }

    fun cancelBooking(booking: BookingEntity) {
        viewModelScope.launch {
            repository.updateBookingStatus(booking.id, "ملغي")
        }
    }

    fun deleteBooking(booking: BookingEntity) {
        viewModelScope.launch {
            repository.deleteBooking(booking)
            repository.logAdminAction("حذف حجز", "تم حذف الحجز #${booking.id} للخدمة: ${booking.serviceTitle}")
        }
    }

    // Unified Service Request submission for all 12 services
    fun submitServiceRequest(
        serviceTitle: String,
        serviceCategory: String,
        clientName: String,
        clientPhone: String,
        travelDate: String,
        passengersCount: Int = 1,
        details: String,
        onComplete: (Boolean, Long, String) -> Unit
    ) {
        viewModelScope.launch {
            val client = _currentClient.value
            val resolvedClientId = client?.id ?: 1L
            val resolvedName = clientName.ifBlank { client?.fullName ?: "عميل وكالة شجين" }
            val resolvedPhone = clientPhone.ifBlank { client?.phone ?: "770038009" }

            val booking = BookingEntity(
                clientId = resolvedClientId,
                clientName = resolvedName,
                clientPhone = resolvedPhone,
                serviceId = 0L,
                serviceTitle = serviceTitle,
                serviceCategory = serviceCategory,
                travelDate = travelDate.ifBlank { "مفتوح / غير محدد" },
                passengersCount = passengersCount,
                notes = details,
                status = "جديد"
            )
            val newId = repository.insertBooking(booking)
            val bookingCode = "SHJ-REQ-$newId"

            // Native push notification
            com.example.util.PushNotificationHelper.sendPushNotification(
                getApplication(),
                "تم استلام طلبك: $serviceTitle",
                "رقم الطلب: $bookingCode - شكراً لاختيارك وكالة شجين للسفريات"
            )

            // Database in-app notification
            repository.addNotification(
                com.example.data.entity.ClientNotificationEntity(
                    userId = resolvedClientId,
                    title = "تم تأكيد طلبك ($serviceTitle)",
                    message = "تم تسجيل طلبك برقم $bookingCode بنجاح في نظام وكالة شجين. سنقوم بمراجعة الطلب والتواصل معك فوراً.",
                    bookingNumber = bookingCode
                )
            )

            repository.logAdminAction("طلب خدمة جديد", "طلب $serviceTitle للعميل $resolvedName ($resolvedPhone)")
            onComplete(true, newId, bookingCode)
        }
    }

    // Service Management (Admin)
    fun saveService(service: TravelServiceEntity, onDone: () -> Unit) {
        viewModelScope.launch {
            if (service.id == 0L) {
                repository.insertService(service)
                repository.logAdminAction("إضافة خدمة", "تم إضافة خدمة جديدة: ${service.title}")
            } else {
                repository.updateService(service)
                repository.logAdminAction("تعديل خدمة", "تم تعديل بيانات الخدمة: ${service.title}")
            }
            onDone()
        }
    }

    fun toggleServiceVisibility(service: TravelServiceEntity) {
        viewModelScope.launch {
            val updated = service.copy(isVisible = !service.isVisible)
            repository.updateService(updated)
            val state = if (updated.isVisible) "إظهار" else "إخفاء"
            repository.logAdminAction("$state خدمة", "تم $state خدمة: ${service.title}")
        }
    }

    fun toggleServiceOffer(service: TravelServiceEntity) {
        viewModelScope.launch {
            val updated = service.copy(isOffer = !service.isOffer)
            repository.updateService(updated)
            val state = if (updated.isOffer) "إضافة إلى العروض" else "إلغاء من العروض"
            repository.logAdminAction("تعديل عروض", "تم $state: ${service.title}")
        }
    }

    fun deleteService(service: TravelServiceEntity) {
        viewModelScope.launch {
            repository.deleteService(service)
            repository.logAdminAction("حذف خدمة", "تم حذف خدمة: ${service.title}")
        }
    }

    // Client Management (Admin)
    fun updateClientInfo(client: ClientEntity, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.updateClient(client)
            repository.logAdminAction("تعديل مستخدم", "تم تعديل بيانات العميل: ${client.fullName}", client.phone)
            onDone()
        }
    }

    fun toggleClientStatus(client: ClientEntity) {
        viewModelScope.launch {
            val updated = client.copy(isActive = !client.isActive)
            repository.updateClient(updated)
            val state = if (updated.isActive) "تفعيل" else "تعطيل"
            repository.logAdminAction("$state حساب", "تم $state حساب العميل: ${client.fullName}", client.phone)
        }
    }

    fun deleteClient(client: ClientEntity) {
        viewModelScope.launch {
            repository.deleteClient(client)
            repository.logAdminAction("حذف مستخدم", "تم حذف حساب العميل: ${client.fullName}", client.phone)
        }
    }

    // Agency Settings (Admin)
    fun updateAgencySettings(
        address: String,
        phone1: String,
        phone2: String,
        phone3: String,
        phone4: String,
        announcement: String,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            val current = repository.getAgencySettingsOnce() ?: AgencySettingsEntity()
            val settings = current.copy(
                address = address,
                phone1 = phone1,
                phone2 = phone2,
                phone3 = phone3,
                phone4 = phone4,
                announcement = announcement
            )
            repository.saveAgencySettings(settings)
            repository.logAdminAction("تحديث بيانات الوكالة", "تم تحديث بيانات التواصل والإعلان")
            onDone()
        }
    }

    fun updateAgencyFullSettings(
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
        maintenance: Boolean,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            val current = repository.getAgencySettingsOnce() ?: AgencySettingsEntity()
            val updated = current.copy(
                agencyName = agencyName,
                address = address,
                phone1 = phone1,
                phone2 = phone2,
                phone3 = phone3,
                phone4 = phone4,
                announcement = announcement,
                whatsappNumber = whatsapp,
                email = email,
                workingHours = hours,
                socialLinks = social,
                aboutDescription = about,
                homeTagline = tagline,
                showAnnouncement = showAnnouncement,
                maintenanceMode = maintenance
            )
            repository.saveAgencySettings(updated)
            repository.logAdminAction("تحديث شامل لإعدادات الوكالة", "تم تحديث كافة بيانات الوكالة ومحتوى الصفحة الرئيسية")
            onDone()
        }
    }

    // News Management (Admin)
    fun addNews(title: String, content: String, dateText: String, tag: String, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.insertNews(
                AgencyNewsEntity(
                    title = title,
                    content = content,
                    dateText = dateText,
                    tag = tag
                )
            )
            repository.logAdminAction("إضافة خبر/إعلان", "تم إضافة إعلان: $title")
            onDone()
        }
    }

    fun updateNews(news: AgencyNewsEntity, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.updateNews(news)
            repository.logAdminAction("تعديل خبر/إعلان", "تم تعديل الإعلان: ${news.title}")
            onDone()
        }
    }

    fun deleteNews(news: AgencyNewsEntity) {
        viewModelScope.launch {
            repository.deleteNews(news)
            repository.logAdminAction("حذف خبر/إعلان", "تم حذف الإعلان: ${news.title}")
        }
    }

    // --- ELECTRONIC BOOKING OPERATIONS ---

    fun submitElectronicBooking(
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
        documents: List<Pair<String, String>>, // Pair(documentType, fileUrl)
        onComplete: (Boolean, String) -> Unit // (success, bookingNumber)
    ) {
        viewModelScope.launch {
            val client = _currentClient.value
            val userId = client?.id ?: 0L
            val bookingNumber = "SHJ-${System.currentTimeMillis().toString().takeLast(6)}"

            val booking = com.example.data.entity.ElectronicBookingEntity(
                bookingNumber = bookingNumber,
                userId = userId,
                fullName = fullName,
                phone = phone,
                email = email,
                country = country,
                destination = destination,
                travelDate = travelDate,
                visaType = visaType,
                otherVisaType = otherVisaType,
                notes = notes,
                bookingStatus = "جديد",
                paymentMethod = paymentMethod,
                paymentStatus = if (transactionNumber.isNotBlank() || paymentReceipt.isNotBlank()) "بانتظار التحقق" else "لم يتم الدفع",
                transactionNumber = transactionNumber,
                paymentReceipt = paymentReceipt,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            val bookingId = repository.insertElectronicBooking(booking)

            // Insert uploaded documents
            documents.forEach { (docType, fileUrl) ->
                if (fileUrl.isNotBlank()) {
                    repository.insertBookingDocument(
                        com.example.data.entity.BookingDocumentEntity(
                            bookingId = bookingId,
                            documentType = docType,
                            fileUrl = fileUrl,
                            fileName = "$docType-$bookingNumber"
                        )
                    )
                }
            }

            // Also add notification for user
            if (userId != 0L) {
                repository.addNotification(
                    com.example.data.entity.ClientNotificationEntity(
                        userId = userId,
                        bookingNumber = bookingNumber,
                        title = "تم تقديم طلبك بنجاح",
                        message = "تم استلام طلب الحجز الإلكتروني رقم $bookingNumber وهو الآن قيد المراجعة لدى فريق وكالة شجين."
                    )
                )
            }

            onComplete(true, bookingNumber)
        }
    }

    fun updateElectronicBookingStatus(
        bookingId: Long,
        userId: Long,
        bookingNumber: String,
        newStatus: String,
        adminNotes: String = ""
    ) {
        viewModelScope.launch {
            repository.updateElectronicBookingStatus(bookingId, newStatus)
            if (adminNotes.isNotBlank()) {
                repository.updateBookingAdminNotes(bookingId, adminNotes)
            }
            if (userId != 0L) {
                repository.addNotification(
                    com.example.data.entity.ClientNotificationEntity(
                        userId = userId,
                        bookingNumber = bookingNumber,
                        title = "تحديث حالة الحجز: $bookingNumber",
                        message = "تم تحديث حالة طلبك إلى: ($newStatus). ${if (adminNotes.isNotBlank()) "ملاحظات الإدارة: $adminNotes" else ""}"
                    )
                )
            }
        }
    }

    fun updateElectronicPaymentStatus(
        bookingId: Long,
        userId: Long,
        bookingNumber: String,
        paymentStatus: String
    ) {
        viewModelScope.launch {
            repository.updateElectronicPaymentStatus(bookingId, paymentStatus)
            if (userId != 0L) {
                val statusText = if (paymentStatus == "تم التحقق") "تم التحقق وتأكيد الدفع بنجاح" else "حالة الدفع: $paymentStatus"
                repository.addNotification(
                    com.example.data.entity.ClientNotificationEntity(
                        userId = userId,
                        bookingNumber = bookingNumber,
                        title = "تحديث حالة الدفع لطلبك $bookingNumber",
                        message = "$statusText من قبل إدارة وكالة شجين."
                    )
                )
            }
        }
    }

    fun requestAdditionalDocuments(
        bookingId: Long,
        userId: Long,
        bookingNumber: String,
        documentsDesc: String
    ) {
        viewModelScope.launch {
            repository.requestAdditionalDocs(bookingId, documentsDesc)
            if (userId != 0L) {
                repository.addNotification(
                    com.example.data.entity.ClientNotificationEntity(
                        userId = userId,
                        bookingNumber = bookingNumber,
                        title = "مطلوب مستندات إضافية لحجزك $bookingNumber",
                        message = "يرجى تزويد الوكالة بالمستندات التالية لاستكمال الإجراءات: $documentsDesc"
                    )
                )
            }
        }
    }

    fun deleteElectronicBooking(booking: com.example.data.entity.ElectronicBookingEntity) {
        viewModelScope.launch {
            repository.deleteElectronicBooking(booking)
        }
    }

    fun getDocumentsForBooking(bookingId: Long) = repository.getDocumentsForBooking(bookingId)

    // Wallet operations (Admin)
    fun savePaymentWallet(wallet: com.example.data.entity.PaymentWalletEntity) {
        viewModelScope.launch {
            if (wallet.id == 0L) {
                repository.insertWallet(wallet)
            } else {
                repository.updateWallet(wallet)
            }
        }
    }

    fun deletePaymentWallet(wallet: com.example.data.entity.PaymentWalletEntity) {
        viewModelScope.launch {
            repository.deleteWallet(wallet)
        }
    }

    // Visa Requirements (Admin)
    fun saveVisaRequirement(requirement: com.example.data.entity.VisaRequirementEntity) {
        viewModelScope.launch {
            repository.saveVisaRequirement(requirement)
        }
    }

    fun markNotificationRead(id: Long) {
        viewModelScope.launch {
            repository.markNotificationAsRead(id)
        }
    }
}
