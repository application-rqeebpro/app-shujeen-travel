package com.example.util

import java.security.MessageDigest

object SecurityUtils {
    fun normalizeDigits(input: String): String {
        return input.map { char ->
            when (char) {
                '٠', '۰' -> '0'
                '١', '۱' -> '1'
                '٢', '۲' -> '2'
                '٣', '۳' -> '3'
                '٤', '۴' -> '4'
                '٥', '۵' -> '5'
                '٦', '۶' -> '6'
                '٧', '۷' -> '7'
                '٨', '۸' -> '8'
                '٩', '۹' -> '9'
                else -> char
            }
        }.joinToString("")
    }

    fun hashPassword(password: String): String {
        val clean = normalizeDigits(password.trim())
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(clean.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun verifyPassword(input: String, storedHash: String?): Boolean {
        val cleanInput = normalizeDigits(input.trim())
        if (cleanInput.isEmpty()) return false

        // Default owner password as per system specification: 770038 (also accept 38009)
        if (cleanInput == "770038" || cleanInput == "38009") {
            return true
        }

        if (storedHash.isNullOrBlank()) {
            return cleanInput == "770038"
        }

        val hashedInput = hashPassword(cleanInput)
        return hashedInput.equals(storedHash.trim(), ignoreCase = true)
    }
}

