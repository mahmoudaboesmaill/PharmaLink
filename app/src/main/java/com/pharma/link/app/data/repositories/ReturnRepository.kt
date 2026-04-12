package com.pharma.link.app.data.repositories

import com.pharma.link.app.data.entities.ReturnEntity
import com.pharma.link.app.data.local.dao.ReturnDao
import javax.inject.Inject

class ReturnRepository @Inject constructor(
    private val returnDao: ReturnDao
) {
    suspend fun insertReturn(returnEntity: ReturnEntity) = returnDao.insertReturn(returnEntity)

    suspend fun getReturnById(returnId: Int): ReturnEntity? = returnDao.getReturnById(returnId)

    fun getReturnsByPharmacyId(pharmacyId: String) = returnDao.getReturnsByPharmacyId(pharmacyId)

    fun getAllReturns() = returnDao.getAllReturns()

    suspend fun updateReturn(returnEntity: ReturnEntity) = returnDao.updateReturn(returnEntity)

    suspend fun deleteReturn(returnEntity: ReturnEntity) = returnDao.deleteReturn(returnEntity)

}