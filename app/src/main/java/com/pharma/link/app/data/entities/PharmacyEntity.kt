package com.pharma.link.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pharmacy_table")
data class PharmacyEntity(
    @PrimaryKey
    val pharmacyId: String,
    val name: String,
    val address:String,
    val licenceNumber: String,
    val phone : String,
    val email: String,
    val password: String
)
