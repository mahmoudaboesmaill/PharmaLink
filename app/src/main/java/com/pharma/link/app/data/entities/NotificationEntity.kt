package com.pharma.link.app.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity (tableName = "notifications_table",
    foreignKeys = [
        ForeignKey(
            entity = PharmacyEntity::class,
            parentColumns = ["pharmacyId"],
            childColumns = ["pharmacyId"],
            onDelete = ForeignKey.RESTRICT,
        )
    ],
    indices = [Index(value = ["pharmacyId"])]
)
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val notificationId: Int = 0,
    val pharmacyId: String,
    val notificationText: String,
    val notificationTime: Long,
    val notificationType: String,
    val isRead: Boolean = false
)
