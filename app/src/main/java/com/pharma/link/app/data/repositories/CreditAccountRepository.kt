package com.pharma.link.app.data.repositories

import com.pharma.link.app.data.entities.CreditAccountEntity
import com.pharma.link.app.data.local.dao.CreditAccountDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreditAccountRepository @Inject constructor(
    private val creditAccountDao: CreditAccountDao
){
    suspend fun insertCreditAccount(creditAccount: CreditAccountEntity) = creditAccountDao.insertCreditAccount(creditAccount)

    suspend fun getCreditAccountByPharmacyId(pharmacyId: String): CreditAccountEntity? = creditAccountDao.getCreditAccountByPharmacyId(pharmacyId)

    fun getAllCreditAccounts(): Flow<List<CreditAccountEntity>> = creditAccountDao.getAllCreditAccounts()

    suspend fun updateCreditAccount(creditAccount: CreditAccountEntity) = creditAccountDao.updateCreditAccount(creditAccount)

    suspend fun deleteCreditAccount(creditAccount: CreditAccountEntity) = creditAccountDao.deleteCreditAccount(creditAccount)




}