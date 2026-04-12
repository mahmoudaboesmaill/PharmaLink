package com.pharma.link.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales_rep_table")
data class SalesRepEntity(
    @PrimaryKey
    val repId: String,
    val name: String,
    val phone: String,
    val email: String,
    val isActive: Boolean=true,
)
