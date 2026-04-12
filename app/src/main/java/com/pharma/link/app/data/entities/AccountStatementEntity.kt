package com.pharma.link.app.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "account_statement_table",
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

data class AccountStatementEntity(
    @PrimaryKey(autoGenerate=true)
    val accountStatementId: Int = 0,
    val pharmacyId: String,
    val periodFrom: Long,
    val periodTo: Long,
    val openingBalance: Long,
    val totalCredit: Long,
    val closingBalance: Long,
)
