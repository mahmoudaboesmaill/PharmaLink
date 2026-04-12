package com.pharma.link.app.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "payments_table",
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


data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val paymentId: Long = 0,
    val invoiceId: Int?,
    val pharmacyId: String,
    val amount: Long,
    val paymentDate: Long,
    val paymentMethod: String,
    val referenceNumber: String,
    val recordedBy: String
)
