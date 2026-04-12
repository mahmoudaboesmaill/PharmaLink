package com.pharma.link.app.data.repositories

import com.pharma.link.app.data.entities.PaymentEntity
import com.pharma.link.app.data.local.dao.PaymentDao
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val paymentDao: PaymentDao
){
    suspend fun insertPayment(payment: PaymentEntity) = paymentDao.insertPayment(payment)

    suspend fun getPaymentById(paymentId: Long): PaymentEntity? = paymentDao.getPaymentById(paymentId)

    fun getPaymentsByPharmacyId(pharmacyId: String) = paymentDao.getPaymentsByPharmacyId(pharmacyId)

    fun getPaymentsByInvoiceId(invoiceId: Int) = paymentDao.getPaymentsByInvoiceId(invoiceId)

    fun getAllPayments() = paymentDao.getAllPayments()

    suspend fun updatePayment(payment: PaymentEntity) = paymentDao.updatePayment(payment)

    suspend fun deletePayment(payment: PaymentEntity) = paymentDao.deletePayment(payment)

}