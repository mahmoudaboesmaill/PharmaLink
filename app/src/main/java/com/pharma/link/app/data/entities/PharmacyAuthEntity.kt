package com.pharma.link.app.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "pharmacy_auth_table",
    foreignKeys = [
        ForeignKey(entity = PharmacyEntity::class,
            parentColumns = ["pharmacyId"],
            childColumns = ["pharmacyId"],
            onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(value = ["pharmacyId"], unique = true)]
)

data class PharmacyAuthEntity(
    @PrimaryKey(autoGenerate = true)
    val authId: Int = 0,
    val pharmacyId: String,
    val password: String,
    val lastLogin: Long? = null,
    
)