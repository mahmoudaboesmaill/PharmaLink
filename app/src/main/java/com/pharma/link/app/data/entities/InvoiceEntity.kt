package com.pharma.link.app.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "invoice_table",
    foreignKeys = [
        ForeignKey(
            entity = SalesRepEntity::class,
            parentColumns = ["repId"],
            childColumns = ["salesRepId"],
            onDelete = ForeignKey.RESTRICT),
        ForeignKey(
            entity = PharmacyEntity::class,
            parentColumns = ["pharmacyId"],
            childColumns = ["pharmacyId"],
            onDelete = ForeignKey.RESTRICT,
        )
    ],
    indices = [Index(value = ["pharmacyId"]),
        Index(value = ["salesRepId"])]
    )

data class InvoiceEntity(
    @PrimaryKey(autoGenerate = true)
    val invoiceId: Int? = 0,
    val pharmacyId: String,
    val salesRepId: String,
    val date: Long,
    val dueDate: Long,
    val totalAmount: Long,
    val status: String,
    val notes: String? = null
)
