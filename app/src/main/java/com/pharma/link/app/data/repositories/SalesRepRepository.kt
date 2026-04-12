package com.pharma.link.app.data.repositories

import com.pharma.link.app.data.entities.SalesRepEntity
import com.pharma.link.app.data.local.dao.SalesRepDao
import javax.inject.Inject

class SalesRepRepository @Inject constructor(
    private val salesDao: SalesRepDao
) {
    suspend fun insertSalesRep(salesRep: SalesRepEntity) = salesDao.insertSalesRep(salesRep)

    suspend fun getSalesRepById(salesRepId: String): SalesRepEntity? = salesDao.getSalesRepById(salesRepId)

    fun getAllSalesReps() = salesDao.getAllSalesReps()

    suspend fun updateSalesRep(salesRep: SalesRepEntity) = salesDao.updateSalesRep(salesRep)

    suspend fun deleteSalesRep(salesRep: SalesRepEntity) = salesDao.deleteSalesRep(salesRep)

}