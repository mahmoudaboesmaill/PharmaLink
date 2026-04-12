package com.pharma.link.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pharma.link.app.data.entities.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: PaymentEntity)

    @Query("SELECT * FROM payments_table WHERE pharmacyId = :pharmacyId")
    fun getPaymentsByPharmacyId(pharmacyId: String): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM payments_table WHERE invoiceId = :invoiceId")
    fun getPaymentsByInvoiceId(invoiceId: Int): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM payments_table WHERE paymentId = :paymentId")
    suspend fun getPaymentById(paymentId: Long): PaymentEntity?

    @Query("SELECT * FROM payments_table")
    fun getAllPayments(): Flow<List<PaymentEntity>>

    @Update
    suspend fun updatePayment(payment: PaymentEntity)

    @Delete
    suspend fun deletePayment(payment: PaymentEntity)
}