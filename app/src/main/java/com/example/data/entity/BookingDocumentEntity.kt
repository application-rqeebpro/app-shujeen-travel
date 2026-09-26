package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Booking Documents Entity representing uploaded files (passports, photos, IDs, receipts).
 */
@Entity(
    tableName = "booking_documents",
    indices = [Index(value = ["booking_id"])]
)
data class BookingDocumentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "booking_id")
    val bookingId: Long,
    @ColumnInfo(name = "document_type")
    val documentType: String, // جواز السفر، صورة شخصية، الهوية الشخصية، مستندات العمل، مستندات الزيارة، مستندات العمرة، إيصال التحويل، مستند إضافي
    @ColumnInfo(name = "file_url")
    val fileUrl: String, // File URI or local content path
    @ColumnInfo(name = "file_name")
    val fileName: String,
    @ColumnInfo(name = "uploaded_at")
    val uploadedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "verification_status")
    val verificationStatus: String = "قيد المراجعة" // قيد المراجعة، مقبول، مرفوض، مطلوب إعادة الرفع
)
