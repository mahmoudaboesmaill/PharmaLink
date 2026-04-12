package com.pharma.link.app.data.entities


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "return_table",
    foreignKeys = [
        ForeignKey(
            entity = InvoiceEntity::class,
            parentColumns = ["invoiceId"],
            childColumns = ["invoiceId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = PharmacyEntity::class,
            parentColumns = ["pharmacyId"],
            childColumns = ["pharmacyId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["invoiceId"]),
        Index(value = ["pharmacyId"])]
)


data class ReturnEntity(
    @PrimaryKey(autoGenerate = true)
    val returnId: Long = 0,
    val invoiceId: Int?,
    val pharmacyId: String,
    val totalReturnAmount: Long,
    val returnNumber: String,
    val returnDate: Long,
    val reason: String,
    val returnStatus: String,
    val approvedBy: String,
    val approvedDate: Long,
    val notes: String?=null
)

