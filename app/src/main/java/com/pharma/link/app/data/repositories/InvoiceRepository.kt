package com.pharma.link.app.data.repositories

import com.pharma.link.app.data.entities.InvoiceEntity
import com.pharma.link.app.data.local.dao.InvoiceDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class InvoiceRepository @Inject constructor(
    private val invoiceDao: InvoiceDao
) {
    suspend fun insertInvoice(invoice: InvoiceEntity) = invoiceDao.insertInvoice(invoice)

    suspend fun getInvoiceById(invoiceId: Int): InvoiceEntity? = invoiceDao.getInvoiceById(invoiceId)

    fun getInvoicesByPharmacyId(pharmacyId: String): Flow<List<InvoiceEntity>> = invoiceDao.getInvoicesByPharmacyId(pharmacyId)

    fun getAllInvoices(): Flow<List<InvoiceEntity>> = invoiceDao.getAllInvoices()

    suspend fun updateInvoice(invoice: InvoiceEntity) = invoiceDao.updateInvoice(invoice)

    suspend fun deleteInvoice(invoice: InvoiceEntity) = invoiceDao.deleteInvoice(invoice)

}