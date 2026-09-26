package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Electronic Booking Entity representing online visa and travel booking applications.
 */
@Entity(tableName = "electronic_bookings")
data class ElectronicBookingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "booking_number")
    val bookingNumber: String,
    @ColumnInfo(name = "user_id")
    val userId: Long,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    val phone: String,
    val email: String = "",
    val country: String,
    val destination: String,
    @ColumnInfo(name = "travel_date")
    val travelDate: String = "",
    @ColumnInfo(name = "visa_type")
    val visaType: String,
    @ColumnInfo(name = "other_visa_type")
    val otherVisaType: String = "",
    val notes: String = "",
    @ColumnInfo(name = "booking_status")
    val bookingStatus: String = "جديد", // جديد، قيد المراجعة، بانتظار مستندات، بانتظار التحقق من الدفع، تم تأكيد الحجز، قيد التنفيذ، مكتمل، مرفوض، ملغي
    @ColumnInfo(name = "payment_method")
    val paymentMethod: String = "", // جيب، ون كاش، جوالي
    @ColumnInfo(name = "payment_status")
    val paymentStatus: String = "لم يتم الدفع", // لم يتم الدفع، بانتظار التحقق، تم التحقق، غير صالح، مسترد
    @ColumnInfo(name = "transaction_number")
    val transactionNumber: String = "",
    @ColumnInfo(name = "payment_receipt")
    val paymentReceipt: String = "", // Local file URI or base64/path
    @ColumnInfo(name = "admin_notes")
    val adminNotes: String = "",
    @ColumnInfo(name = "requested_additional_docs")
    val requestedAdditionalDocs: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
