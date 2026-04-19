package com.pharma.link.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pharma.link.app.data.entities.AccountStatementEntity
import com.pharma.link.app.data.entities.CreditAccountEntity
import com.pharma.link.app.data.entities.InvoiceEntity
import com.pharma.link.app.data.entities.NotificationEntity
import com.pharma.link.app.data.entities.PaymentEntity
import com.pharma.link.app.data.entities.PharmacyAuthEntity
import com.pharma.link.app.data.entities.PharmacyEntity
import com.pharma.link.app.data.entities.ReturnEntity
import com.pharma.link.app.data.entities.SalesRepEntity
import com.pharma.link.app.data.local.dao.AccountStatementDao
import com.pharma.link.app.data.local.dao.CreditAccountDao
import com.pharma.link.app.data.local.dao.InvoiceDao
import com.pharma.link.app.data.local.dao.NotificationDao
import com.pharma.link.app.data.local.dao.PaymentDao
import com.pharma.link.app.data.local.dao.PharmacyDao
import com.pharma.link.app.data.local.dao.ReturnDao
import com.pharma.link.app.data.local.dao.SalesRepDao


@Database(
    entities = [
        PharmacyEntity::class,
        PharmacyAuthEntity::class,
        SalesRepEntity::class,
        InvoiceEntity::class,
        PaymentEntity::class,
        ReturnEntity::class,
        AccountStatementEntity::class,
        CreditAccountEntity::class,
        NotificationEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class PharmaDatabase : RoomDatabase() {
    abstract fun pharmacyDao(): PharmacyDao
    abstract fun salesRepDao(): SalesRepDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun paymentDao(): PaymentDao
    abstract fun returnDao(): ReturnDao
    abstract fun accountStatementDao(): AccountStatementDao
    abstract fun creditAccountDao(): CreditAccountDao
    abstract fun notificationDao(): NotificationDao

}