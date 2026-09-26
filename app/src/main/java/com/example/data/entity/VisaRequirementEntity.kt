package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Required documents definition per visa type, manageable by Admin.
 */
@Entity(tableName = "visa_requirement_settings")
data class VisaRequirementEntity(
    @PrimaryKey
    val visaType: String, // تأشيرة عمل، تأشيرة سياحة، تأشيرة زيارة، تأشيرة عمرة، تأشيرة أخرى
    val requiredDocsCsv: String, // comma separated document names
    val description: String = ""
)
