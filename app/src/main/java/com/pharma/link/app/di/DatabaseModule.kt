package com.pharma.link.app.di

import android.content.Context
import androidx.room.Room
import com.pharma.link.app.data.local.dao.AccountStatementDao
import com.pharma.link.app.data.local.dao.CreditAccountDao
import com.pharma.link.app.data.local.dao.InvoiceDao
import com.pharma.link.app.data.local.dao.NotificationDao
import com.pharma.link.app.data.local.dao.PaymentDao
import com.pharma.link.app.data.local.PharmaDatabase
import com.pharma.link.app.data.local.dao.PharmacyDao
import com.pharma.link.app.data.local.dao.ReturnDao
import com.pharma.link.app.data.local.dao.SalesRepDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PharmaDatabase {
        return Room.databaseBuilder(
            context,
            PharmaDatabase::class.java,
            "pharma_database"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun providePharmacyDao(database: PharmaDatabase) : PharmacyDao {
        return database.pharmacyDao()
    }


    @Provides
    @Singleton
    fun provideSalesRepDao(database: PharmaDatabase) : SalesRepDao {
        return database.salesRepDao()
    }

    @Provides
    @Singleton
    fun provideInvoiceDao(database: PharmaDatabase) : InvoiceDao {
        return database.invoiceDao()
    }

    @Provides
    @Singleton
    fun providePaymentDao(database: PharmaDatabase) : PaymentDao {
        return database.paymentDao()
    }

    @Provides
    @Singleton
    fun provideReturnDao(database: PharmaDatabase) : ReturnDao {
        return database.returnDao()
    }

    @Provides
    @Singleton
    fun provideAccountStatementDao(database: PharmaDatabase) : AccountStatementDao {
        return database.accountStatementDao()
    }

    @Provides
    @Singleton
    fun provideCreditAccountDao(database: PharmaDatabase) : CreditAccountDao {
        return database.creditAccountDao()
    }

    @Provides
    @Singleton
    fun provideNotificationDao(database: PharmaDatabase) : NotificationDao {
        return database.notificationDao()

    }

}
