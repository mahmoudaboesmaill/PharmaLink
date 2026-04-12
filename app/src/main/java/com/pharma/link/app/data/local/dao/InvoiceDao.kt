package com.pharma.link.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pharma.link.app.data.entities.InvoiceEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface InvoiceDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoice: InvoiceEntity)

    @Query("SELECT * FROM invoice_table WHERE invoiceId = :invoiceId")
    suspend fun getInvoiceById(invoiceId: Int): InvoiceEntity?

    @Query("SELECT * FROM invoice_table WHERE pharmacyId = :pharmacyId")
    fun getInvoicesByPharmacyId(pharmacyId: String): Flow<List<InvoiceEntity>>

    @Query("SELECT * FROM invoice_table")
    fun getAllInvoices(): Flow<List<InvoiceEntity>>

    @Update
    suspend fun updateInvoice(invoice: InvoiceEntity)

    @Delete
    suspend fun deleteInvoice(invoice: InvoiceEntity)
}