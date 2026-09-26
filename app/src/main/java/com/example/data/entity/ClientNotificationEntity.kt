package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * In-app notification for client regarding their booking and payment status updates.
 */
@Entity(tableName = "client_notifications")
data class ClientNotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val bookingNumber: String = "",
    val title: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
