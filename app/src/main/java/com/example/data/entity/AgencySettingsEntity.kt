package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.util.SecurityUtils

@Entity(tableName = "agency_settings")
data class AgencySettingsEntity(
    @PrimaryKey
    val id: Int = 1,
    val agencyName: String = "وكالة شجين للسفريات والسياحة",
    val address: String = "صنعاء - شارع خولان - جوار السلامي لمواد البناء",
    val phone1: String = "+967 777779492",
    val phone2: String = "+966 551160835",
    val phone3: String = "+967 774191789",
    val phone4: String = "+967 770038009",
    val announcement: String = "عروض خاصة لموسم العمرة ورحلات النقل الدولي المباشر إلى المملكة العربية السعودية.",
    val adminPasswordHash: String = SecurityUtils.hashPassword("770038"),
    val whatsappNumber: String = "+967 770038009",
    val email: String = "info@shajeen-travel.com",
    val workingHours: String = "يومياً من 8 صباحاً حتى 10 مساءً",
    val socialLinks: String = "facebook.com/shajeentravel, instagram.com/shajeentravel",
    val aboutDescription: String = "وكالة شجين للسفريات والسياحة - خدمات الحجز الإلكتروني، التأشيرات، رحلات الطيران، وبرامج الحج والعمرة والنقل الدولي.",
    val homeTagline: String = "وجهتك الأولى لرحلات الطيران، الحج والعمرة، والنقل الدولي",
    val maintenanceMode: Boolean = false,
    val showAnnouncement: Boolean = true
)
