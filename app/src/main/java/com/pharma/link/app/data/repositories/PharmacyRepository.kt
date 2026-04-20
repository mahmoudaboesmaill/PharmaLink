package com.pharma.link.app.data.repositories

import com.pharma.link.app.data.entities.PharmacyEntity
import com.pharma.link.app.data.local.dao.PharmacyDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PharmacyRepository @Inject constructor(
    private val pharmacyDao: PharmacyDao
) {
    fun getAllPharmacies(): Flow<List<PharmacyEntity>> = pharmacyDao.getAllPharmacies()

    suspend fun insertPharmacy(pharmacy: PharmacyEntity) = pharmacyDao.insertPharmacy(pharmacy)

    suspend fun updatePharmacy(pharmacy: PharmacyEntity) = pharmacyDao.updatePharmacy(pharmacy)

    suspend fun deletePharmacy(pharmacy: PharmacyEntity) = pharmacyDao.deletePharmacy(pharmacy)

    suspend fun getPharmacyById(pharmacyId: String): PharmacyEntity? = pharmacyDao.getPharmacyById(pharmacyId)

    suspend fun getPharmacyByPhone(phone: String) = pharmacyDao.getPharmacyByPhone(phone)

    suspend fun getPharmacyByEmail(email: String) = pharmacyDao.getPharmacyByEmail(email)

    suspend fun getPharmacyByLicenceNumber(licenceNumber: String) = pharmacyDao.getPharmacyByLicenceNumber(licenceNumber)


}