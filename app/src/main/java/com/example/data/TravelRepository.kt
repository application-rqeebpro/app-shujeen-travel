package com.example.data

import com.example.data.entity.AgencyNewsEntity
import com.example.data.entity.AgencySettingsEntity
import com.example.data.entity.BookingEntity
import com.example.data.entity.ClientEntity
import com.example.data.entity.TravelServiceEntity
import kotlinx.coroutines.flow.Flow

class TravelRepository(private val db: AppDatabase) {

    // Clients
    val allClients: Flow<List<ClientEntity>> = db.clientDao().getAllClients()
    suspend fun getClientById(id: Long) = db.clientDao().getClientById(id)
    suspend fun getClientByPhone(phone: String) = db.clientDao().getClientByPhone(phone)
    suspend fun insertOrUpdateClient(client: ClientEntity): Long = db.clientDao().insertClient(client)
    suspend fun deleteClient(client: ClientEntity) = db.clientDao().deleteClient(client)

    // Travel Services
    val allServices: Flow<List<TravelServiceEntity>> = db.travelServiceDao().getAllServices()
    val featuredServices: Flow<List<TravelServiceEntity>> = db.travelServiceDao().getFeaturedServices()
    fun getServicesByCategory(category: String): Flow<List<TravelServiceEntity>> =
        db.travelServiceDao().getServicesByCategory(category)

    suspend fun insertService(service: TravelServiceEntity) = db.travelServiceDao().insertService(service)
    suspend fun updateService(service: TravelServiceEntity) = db.travelServiceDao().updateService(service)
    suspend fun deleteService(service: TravelServiceEntity) = db.travelServiceDao().deleteService(service)

    // Bookings
    val allBookings: Flow<List<BookingEntity>> = db.bookingDao().getAllBookings()
    fun getBookingsByClient(clientId: Long): Flow<List<BookingEntity>> = db.bookingDao().getBookingsByClient(clientId)
    fun getBookingsByPhone(phone: String): Flow<List<BookingEntity>> = db.bookingDao().getBookingsByPhone(phone)
    suspend fun insertBooking(booking: BookingEntity) = db.bookingDao().insertBooking(booking)
    suspend fun updateBookingStatus(id: Long, status: String) = db.bookingDao().updateStatus(id, status)
    suspend fun updateBookingStatusAndNotes(id: Long, status: String, adminNotes: String) =
        db.bookingDao().updateStatusAndNotes(id, status, adminNotes)
    suspend fun deleteBooking(booking: BookingEntity) = db.bookingDao().deleteBooking(booking)
    suspend fun updateClient(client: ClientEntity) = db.clientDao().updateClient(client)

    // Agency Settings & News
    val agencySettings: Flow<AgencySettingsEntity?> = db.agencyDao().getAgencySettings()
    suspend fun getAgencySettingsOnce(): AgencySettingsEntity? = db.agencyDao().getAgencySettingsOnce()
    suspend fun saveAgencySettings(settings: AgencySettingsEntity) = db.agencyDao().saveAgencySettings(settings)

    val allNews: Flow<List<AgencyNewsEntity>> = db.agencyDao().getAllNews()
    suspend fun insertNews(news: AgencyNewsEntity) = db.agencyDao().insertNews(news)
    suspend fun updateNews(news: AgencyNewsEntity) = db.agencyDao().insertNews(news)
    suspend fun deleteNews(news: AgencyNewsEntity) = db.agencyDao().deleteNews(news)

    // Admin Activity Audit Log
    val allAdminLogs: Flow<List<com.example.data.entity.AdminLogEntity>> = db.adminLogDao().getAllLogs()
    suspend fun logAdminAction(actionType: String, details: String, targetUser: String = "") {
        db.adminLogDao().insertLog(
            com.example.data.entity.AdminLogEntity(
                actionType = actionType,
                details = details,
                targetUser = targetUser
            )
        )
    }
    suspend fun clearAdminLogs() = db.adminLogDao().clearLogs()

    // Electronic Bookings
    val allElectronicBookings: Flow<List<com.example.data.entity.ElectronicBookingEntity>> =
        db.electronicBookingDao().getAllElectronicBookings()

    fun getElectronicBookingsByUserId(userId: Long): Flow<List<com.example.data.entity.ElectronicBookingEntity>> =
        db.electronicBookingDao().getBookingsByUserId(userId)

    suspend fun getElectronicBookingById(id: Long) = db.electronicBookingDao().getBookingById(id)
    suspend fun getElectronicBookingByNumber(bookingNumber: String) = db.electronicBookingDao().getBookingByNumber(bookingNumber)
    suspend fun insertElectronicBooking(booking: com.example.data.entity.ElectronicBookingEntity) =
        db.electronicBookingDao().insertBooking(booking)
    suspend fun updateElectronicBooking(booking: com.example.data.entity.ElectronicBookingEntity) =
        db.electronicBookingDao().updateBooking(booking)
    suspend fun updateElectronicBookingStatus(id: Long, status: String) =
        db.electronicBookingDao().updateBookingStatus(id, status)
    suspend fun updateElectronicPaymentStatus(id: Long, status: String) =
        db.electronicBookingDao().updatePaymentStatus(id, status)
    suspend fun requestAdditionalDocs(id: Long, docs: String) =
        db.electronicBookingDao().requestAdditionalDocs(id, docs)
    suspend fun updateBookingAdminNotes(id: Long, notes: String) =
        db.electronicBookingDao().updateAdminNotes(id, notes)
    suspend fun deleteElectronicBooking(booking: com.example.data.entity.ElectronicBookingEntity) =
        db.electronicBookingDao().deleteBooking(booking)

    // Booking Documents
    fun getDocumentsForBooking(bookingId: Long): Flow<List<com.example.data.entity.BookingDocumentEntity>> =
        db.electronicBookingDao().getDocumentsByBookingId(bookingId)
    suspend fun getDocumentsForBookingOnce(bookingId: Long) =
        db.electronicBookingDao().getDocumentsListOnce(bookingId)
    suspend fun insertBookingDocument(document: com.example.data.entity.BookingDocumentEntity) =
        db.electronicBookingDao().insertDocument(document)
    suspend fun deleteBookingDocument(document: com.example.data.entity.BookingDocumentEntity) =
        db.electronicBookingDao().deleteDocument(document)

    // Payment Wallets
    val activeWallets: Flow<List<com.example.data.entity.PaymentWalletEntity>> =
        db.electronicBookingDao().getActiveWallets()
    val allWallets: Flow<List<com.example.data.entity.PaymentWalletEntity>> =
        db.electronicBookingDao().getAllWallets()
    suspend fun insertWallet(wallet: com.example.data.entity.PaymentWalletEntity) =
        db.electronicBookingDao().insertWallet(wallet)
    suspend fun updateWallet(wallet: com.example.data.entity.PaymentWalletEntity) =
        db.electronicBookingDao().updateWallet(wallet)
    suspend fun deleteWallet(wallet: com.example.data.entity.PaymentWalletEntity) =
        db.electronicBookingDao().deleteWallet(wallet)

    // Visa Requirements
    val allVisaRequirements: Flow<List<com.example.data.entity.VisaRequirementEntity>> =
        db.electronicBookingDao().getAllVisaRequirements()
    suspend fun getVisaRequirement(type: String) =
        db.electronicBookingDao().getVisaRequirement(type)
    suspend fun saveVisaRequirement(requirement: com.example.data.entity.VisaRequirementEntity) =
        db.electronicBookingDao().saveVisaRequirement(requirement)

    // Notifications
    val allNotifications: Flow<List<com.example.data.entity.ClientNotificationEntity>> =
        db.electronicBookingDao().getAllNotifications()
    fun getNotificationsForUser(userId: Long): Flow<List<com.example.data.entity.ClientNotificationEntity>> =
        db.electronicBookingDao().getNotificationsByUserId(userId)
    suspend fun addNotification(notification: com.example.data.entity.ClientNotificationEntity) =
        db.electronicBookingDao().insertNotification(notification)
    suspend fun markNotificationAsRead(id: Long) =
        db.electronicBookingDao().markNotificationAsRead(id)
    suspend fun deleteNotification(id: Long) =
        db.electronicBookingDao().deleteNotificationById(id)

    // Pre-populate default services and agency settings
    suspend fun ensureDefaultData() {
        // 1. Settings
        val currentSettings = db.agencyDao().getAgencySettingsOnce()
        if (currentSettings == null) {
            db.agencyDao().saveAgencySettings(
                AgencySettingsEntity(
                    id = 1,
                    agencyName = "وكالة شجين للسفريات والسياحة",
                    address = "صنعاء - شارع خولان - جوار السلامي لمواد البناء",
                    phone1 = "+967 777779492",
                    phone2 = "+966 551160835",
                    phone3 = "+967 774191789",
                    phone4 = "+967 770038009",
                    announcement = "مرحباً بكم في وكالة شجين! خصومات حصرية لرحلات العمرة وتذاكر الطيران لجميع الوجهات العالمية."
                )
            )
        }

        // 2. Services (The 12 Core Services matching reference image and user specification)
        if (db.travelServiceDao().getCount() == 0) {
            val defaultServices = listOf(
                TravelServiceEntity(
                    title = "حجز تذاكر الطيران",
                    category = "طيران",
                    subtitle = "أفضل الأسعار على جميع خطوط الطيران العالمية",
                    description = "نوفر حجوزات مؤكدة على طيران اليمنية، الخطوط السعودية، مصر للطيران، طيران الإمارات وفلاي دبي مع إمكانية تعديل وتأكيد المواعيد فوراً.",
                    price = "حسب الوجهة",
                    iconType = "flight",
                    badge = "الأكثر طلباً",
                    sortOrder = 1
                ),
                TravelServiceEntity(
                    title = "جوازات السفر",
                    category = "جوازات",
                    subtitle = "إصدار وتجديد جوازات السفر الرسمية",
                    description = "خدمة تخليص ومتابعة معاملات الجوازات الرسمية بدقة وسرعة قياسية ومتابعة مستمرة حتى الاستلام.",
                    price = "شامل الرسوم",
                    iconType = "passport",
                    badge = "خدمة فورية",
                    sortOrder = 2
                ),
                TravelServiceEntity(
                    title = "التأشيرات (الفيزا)",
                    category = "تأشيرات",
                    subtitle = "تأشيرات عمل، زيارة، سياحة، وعمرة",
                    description = "تخليص ومعاملة تأشيرات السعودية (زيارة شخصية، عائلية، تجارية، سياحية) ومصر، الإمارات، سلطنة عمان، الأردن، وغيرها بدقة وسرعة قياسية.",
                    price = "تبدأ من 150$",
                    iconType = "visa",
                    badge = "شامل الإجراءات",
                    sortOrder = 3
                ),
                TravelServiceEntity(
                    title = "حجوزات الفنادق",
                    category = "فنادق",
                    subtitle = "فنادق مختارة 4 و 5 نجوم بأسعار مخفضة",
                    description = "حجوزات فندقية عالمية ومحلية تشمل الإفطار والاستقبال من وإلى المطار مع إطلالات مباشرة على الحرمين وأبرز المعالم.",
                    price = "خصم حتى 25%",
                    iconType = "hotel",
                    badge = "عروض خاصة",
                    sortOrder = 4
                ),
                TravelServiceEntity(
                    title = "الرحلات السياحية",
                    category = "سياحة",
                    subtitle = "سقطرى، خريف صلالة، وبرامج استكشافية",
                    description = "برامج سياحية متكاملة للأفراد والعائلات تشمل النقل المريح، حجوزات الفنادق، جولات يومية، ومرشدين سياحيين محترفين.",
                    price = "برامج موسمية",
                    iconType = "tour",
                    badge = "وجهات ساحرة",
                    sortOrder = 5
                ),
                TravelServiceEntity(
                    title = "الحج والعمرة",
                    category = "حج وعمرة",
                    subtitle = "برامج VIP واقتصادية براً وجواً",
                    description = "يشمل التأشيرة، تذاكر الطيران أو حافلات VIP الفاخرة، الإقامة في مكة المكرمة والمدينة المنورة، زيارات المزارات الدينية مع مرشدين متخصصين.",
                    price = "تبدأ من 280$",
                    iconType = "kaaba",
                    badge = "VIP متميز",
                    sortOrder = 6
                ),
                TravelServiceEntity(
                    title = "تأجير السيارات",
                    category = "نقل وسيارات",
                    subtitle = "سيارات VIP وسيارات صالون وسفريات خاصة",
                    description = "خدمات التوصيل والنقل الخاص والمشاوير بين المدن والمطارات والمنافذ البرية بأقصى درجات الراحة والأمان وسائقين ذوي خبرة.",
                    price = "حسب المشوار",
                    iconType = "car",
                    badge = "سريع ومريح",
                    sortOrder = 7
                ),
                TravelServiceEntity(
                    title = "التأمين على السفر",
                    category = "تأمين",
                    subtitle = "تأمين طبي وتأمين سفر معتمد لكافة السفارات",
                    description = "إصدار وثائق تأمين السفر المعتمدة للشنغن والدول الأوروبية والخليجية لتغطية الطوارئ الطبية وفقدان الأمتعة وإلغاء الرحلات.",
                    price = "تبدأ من 35$",
                    iconType = "insurance",
                    badge = "معتمد رسمياً",
                    sortOrder = 8
                ),
                TravelServiceEntity(
                    title = "شحن الأمتعة والبضائع",
                    category = "شحن وطرود",
                    subtitle = "شحن أمتعة وطرود سريعة ومستندات مؤمنة",
                    description = "نقل الطرود السريعة والمستندات والوثائق المهمة والأمتعة الشخصية بكل أمان وسرعة مع التتبع المستمر حتى التسليم لليد.",
                    price = "حسب الوزن",
                    iconType = "cargo",
                    badge = "أمان وسرعة",
                    sortOrder = 9
                ),
                TravelServiceEntity(
                    title = "ترجمة وتصديق الوثائق",
                    category = "وثائق وترجمة",
                    subtitle = "ترجمة معتمدة وتصديق خارجية وسفارات",
                    description = "ترجمة قانونية وأكاديمية معتمدة لجميع الوثائق والشهادات والعقود وإنهاء تصديقات وزارة الخارجية والسفارات والقنصليات.",
                    price = "حسب الوثيقة",
                    iconType = "translation",
                    badge = "معتمد دولياً",
                    sortOrder = 10
                ),
                TravelServiceEntity(
                    title = "الاستقبال والتوديع في المطارات",
                    category = "خدمات مطار",
                    subtitle = "استقبال وترحيب وتسهيل إجراءات السفر",
                    description = "خدمة استقبال وتوديع المسافرين في صالات المطار ومساعدتهم في إنهاء إجراءات الحقائب والجوازات والتوصيل الفندقي الراقي.",
                    price = "خدمة مريحة",
                    iconType = "airport",
                    badge = "خدمة 24/7",
                    sortOrder = 11
                ),
                TravelServiceEntity(
                    title = "خدمات رجال الأعمال",
                    category = "رجال أعمال",
                    subtitle = "طيران خاص وحجوزات أجنحة واستقبال VIP",
                    description = "باقات استثنائية مخصصة للشركات والوفود ورجال الأعمال تشمل الطيران الخاص، صالات كبار الشخصيات، وسيارات ليموزين فاخرة.",
                    price = "خدمات نخبة",
                    iconType = "business",
                    badge = "VIP فخم",
                    sortOrder = 12
                )
            )
            db.travelServiceDao().insertAll(defaultServices)
        }

        // 3. News
        if (db.agencyDao().getNewsCount() == 0) {
            val defaultNews = listOf(
                AgencyNewsEntity(
                    title = "بدء التسجيل لرحلات العمرة لشهر رجب وشعبان",
                    content = "تعلن وكالة شجين للسفريات والسياحة عن فتح باب التسجيل لرحلات العمرة المباركة بأسعار خاصة وخدمات فندقية راقية.",
                    dateText = "مستمر حالياً",
                    tag = "موسم العمرة"
                ),
                AgencyNewsEntity(
                    title = "تدشين خطوط نقل حديثة VIP إلى مكة والرياض",
                    content = "تم تعزيز أسطول النقل بباصات VIP جديدة موديل العام مزودة بإنترنت فضائي ومقاعد تدليك لراحة المسافرين.",
                    dateText = "تحديث جديد",
                    tag = "خدمات النقل"
                )
            )
            db.agencyDao().insertAllNews(defaultNews)
        }

        // 4. Default Wallets (Jib, OneCash, Jawali - all editable by admin)
        if (db.electronicBookingDao().getWalletsCount() == 0) {
            val defaultWallets = listOf(
                com.example.data.entity.PaymentWalletEntity(
                    walletName = "محفظة جيب (Jib)",
                    accountNumber = "770038009",
                    instructions = "قم بالتحويل عبر تطبيق جيب إلى حساب وكالة شجين رقم 770038009 ثم احتفظ برقم العملية وصورة الإشعار."
                ),
                com.example.data.entity.PaymentWalletEntity(
                    walletName = "محفظة ون كاش (OneCash)",
                    accountNumber = "770038009",
                    instructions = "قم بالتحويل عبر تطبيق ون كاش إلى الحساب رقم 770038009 ثم أدخل رقم العملية وصورة الإشعار."
                ),
                com.example.data.entity.PaymentWalletEntity(
                    walletName = "محفظة جوالي (Jawali)",
                    accountNumber = "770038009",
                    instructions = "قم بالتحويل عبر تطبيق جوالي إلى الحساب رقم 770038009 ثم قم برفع إشعار التحويل."
                )
            )
            for (w in defaultWallets) {
                db.electronicBookingDao().insertWallet(w)
            }
        }

        // 5. Default Visa Requirements
        val defaultRequirements = listOf(
            com.example.data.entity.VisaRequirementEntity(
                visaType = "تأشيرة عمل",
                requiredDocsCsv = "صورة الجواز ساري المفعول,صورة شخصية بخلفية بيضاء,المؤهل العلمي / شهادة الخبرة,الفحص الطبي المعتمد,عقد العمل أو تفويض التأشيرة",
                description = "متطلبات تأشيرات العمل الرسمية للمملكة والدول الأخرى"
            ),
            com.example.data.entity.VisaRequirementEntity(
                visaType = "تأشيرة سياحة",
                requiredDocsCsv = "صورة الجواز ساري المفعول,صورة شخصية بخلفية بيضاء,حجز طيران مبدئي,حجز فندقي",
                description = "متطلبات التأشيرات السياحية العالمية"
            ),
            com.example.data.entity.VisaRequirementEntity(
                visaType = "تأشيرة زيارة",
                requiredDocsCsv = "صورة الجواز ساري المفعول,صورة شخصية بخلفية بيضاء,مستند التأشيرة / طلب الزيارة,إثبات صلة القرابة / الهوية",
                description = "متطلبات الزيارات الشخصية والعائلية"
            ),
            com.example.data.entity.VisaRequirementEntity(
                visaType = "تأشيرة عمرة",
                requiredDocsCsv = "صورة الجواز ساري المفعول,صورة شخصية بخلفية بيضاء,شهادة التحصين المعتمدة",
                description = "متطلبات إصدار تأشيرات العمرة للمعتمرين"
            ),
            com.example.data.entity.VisaRequirementEntity(
                visaType = "أخرى",
                requiredDocsCsv = "صورة الجواز ساري المفعول,صورة شخصية بخلفية بيضاء,المستندات المؤيدة للطلب",
                description = "متطلبات التأشيرات والمعاملات الخاصة"
            )
        )
        for (req in defaultRequirements) {
            val existing = db.electronicBookingDao().getVisaRequirement(req.visaType)
            if (existing == null) {
                db.electronicBookingDao().saveVisaRequirement(req)
            }
        }
    }
}
