package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Payment Wallet Setting entity that can be modified by the agency owner/admin.
 */
@Entity(tableName = "payment_wallets")
data class PaymentWalletEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val walletName: String, // جيب، ون كاش، جوالي، etc.
    val accountNumber: String, // 770038009
    val accountHolder: String = "وكالة شجين للسفريات والسياحة",
    val instructions: String = "يرجى التحويل إلى هذا الرقم ثم إدخال رقم العملية ورفع صورة الإشعار.",
    val isActive: Boolean = true
)
