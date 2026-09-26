package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.AdminLogDao
import com.example.data.dao.AgencyDao
import com.example.data.dao.BookingDao
import com.example.data.dao.ClientDao
import com.example.data.dao.ElectronicBookingDao
import com.example.data.dao.TravelServiceDao
import com.example.data.entity.AdminLogEntity
import com.example.data.entity.AgencyNewsEntity
import com.example.data.entity.AgencySettingsEntity
import com.example.data.entity.BookingDocumentEntity
import com.example.data.entity.BookingEntity
import com.example.data.entity.ClientEntity
import com.example.data.entity.ClientNotificationEntity
import com.example.data.entity.ElectronicBookingEntity
import com.example.data.entity.PaymentWalletEntity
import com.example.data.entity.TravelServiceEntity
import com.example.data.entity.VisaRequirementEntity

@Database(
    entities = [
        ClientEntity::class,
        TravelServiceEntity::class,
        BookingEntity::class,
        AgencySettingsEntity::class,
        AgencyNewsEntity::class,
        ElectronicBookingEntity::class,
        BookingDocumentEntity::class,
        PaymentWalletEntity::class,
        VisaRequirementEntity::class,
        ClientNotificationEntity::class,
        AdminLogEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun travelServiceDao(): TravelServiceDao
    abstract fun bookingDao(): BookingDao
    abstract fun agencyDao(): AgencyDao
    abstract fun electronicBookingDao(): ElectronicBookingDao
    abstract fun adminLogDao(): AdminLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shajeen_travel.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
