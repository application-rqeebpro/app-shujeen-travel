package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.BookingDocumentEntity
import com.example.data.entity.ClientNotificationEntity
import com.example.data.entity.ElectronicBookingEntity
import com.example.data.entity.PaymentWalletEntity
import com.example.data.entity.VisaRequirementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ElectronicBookingDao {

    // Electronic Bookings
    @Query("SELECT * FROM electronic_bookings ORDER BY created_at DESC")
    fun getAllElectronicBookings(): Flow<List<ElectronicBookingEntity>>

    @Query("SELECT * FROM electronic_bookings WHERE user_id = :userId ORDER BY created_at DESC")
    fun getBookingsByUserId(userId: Long): Flow<List<ElectronicBookingEntity>>

    @Query("SELECT * FROM electronic_bookings WHERE id = :id")
    suspend fun getBookingById(id: Long): ElectronicBookingEntity?

    @Query("SELECT * FROM electronic_bookings WHERE booking_number = :bookingNumber")
    suspend fun getBookingByNumber(bookingNumber: String): ElectronicBookingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: ElectronicBookingEntity): Long

    @Update
    suspend fun updateBooking(booking: ElectronicBookingEntity)

    @Query("UPDATE electronic_bookings SET booking_status = :status, updated_at = :updatedAt WHERE id = :bookingId")
    suspend fun updateBookingStatus(bookingId: Long, status: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE electronic_bookings SET payment_status = :status, updated_at = :updatedAt WHERE id = :bookingId")
    suspend fun updatePaymentStatus(bookingId: Long, status: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE electronic_bookings SET requested_additional_docs = :docs, booking_status = 'بانتظار مستندات', updated_at = :updatedAt WHERE id = :bookingId")
    suspend fun requestAdditionalDocs(bookingId: Long, docs: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE electronic_bookings SET admin_notes = :notes, updated_at = :updatedAt WHERE id = :bookingId")
    suspend fun updateAdminNotes(bookingId: Long, notes: String, updatedAt: Long = System.currentTimeMillis())

    @Delete
    suspend fun deleteBooking(booking: ElectronicBookingEntity)

    // Booking Documents
    @Query("SELECT * FROM booking_documents WHERE booking_id = :bookingId ORDER BY uploaded_at ASC")
    fun getDocumentsByBookingId(bookingId: Long): Flow<List<BookingDocumentEntity>>

    @Query("SELECT * FROM booking_documents WHERE booking_id = :bookingId")
    suspend fun getDocumentsListOnce(bookingId: Long): List<BookingDocumentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: BookingDocumentEntity): Long

    @Delete
    suspend fun deleteDocument(document: BookingDocumentEntity)

    @Query("DELETE FROM booking_documents WHERE booking_id = :bookingId AND document_type = :docType")
    suspend fun deleteDocumentByType(bookingId: Long, docType: String)

    // Payment Wallets
    @Query("SELECT * FROM payment_wallets WHERE isActive = 1")
    fun getActiveWallets(): Flow<List<PaymentWalletEntity>>

    @Query("SELECT * FROM payment_wallets")
    fun getAllWallets(): Flow<List<PaymentWalletEntity>>

    @Query("SELECT COUNT(*) FROM payment_wallets")
    suspend fun getWalletsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: PaymentWalletEntity): Long

    @Update
    suspend fun updateWallet(wallet: PaymentWalletEntity)

    @Delete
    suspend fun deleteWallet(wallet: PaymentWalletEntity)

    // Visa Requirements
    @Query("SELECT * FROM visa_requirement_settings")
    fun getAllVisaRequirements(): Flow<List<VisaRequirementEntity>>

    @Query("SELECT * FROM visa_requirement_settings WHERE visaType = :visaType")
    suspend fun getVisaRequirement(visaType: String): VisaRequirementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVisaRequirement(requirement: VisaRequirementEntity)

    // In-App Notifications
    @Query("SELECT * FROM client_notifications WHERE userId = :userId ORDER BY timestamp DESC")
    fun getNotificationsByUserId(userId: Long): Flow<List<ClientNotificationEntity>>

    @Query("SELECT * FROM client_notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<ClientNotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: ClientNotificationEntity): Long

    @Query("UPDATE client_notifications SET isRead = 1 WHERE id = :notificationId")
    suspend fun markNotificationAsRead(notificationId: Long)

    @Delete
    suspend fun deleteNotification(notification: ClientNotificationEntity)

    @Query("DELETE FROM client_notifications WHERE id = :id")
    suspend fun deleteNotificationById(id: Long)
}
