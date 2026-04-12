package com.pharma.link.app.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "credit_account_table",
    foreignKeys = [
        ForeignKey(
            entity = PharmacyEntity::class,
            parentColumns = ["pharmacyId"],
            childColumns = ["pharmacyId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["pharmacyId"])]
)
data class CreditAccountEntity(
    @PrimaryKey(autoGenerate = true)
    val accountId:Int=0,
    val pharmacyId: String,
    val creditLimit:Long,
    val creditUsed:Long ,
    val availableCredit: Long,
    val lastUpdate: Long,
    val status: String,
)
