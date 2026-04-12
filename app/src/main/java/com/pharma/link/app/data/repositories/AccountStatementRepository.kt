package com.pharma.link.app.data.repositories

import com.pharma.link.app.data.entities.AccountStatementEntity
import com.pharma.link.app.data.local.dao.AccountStatementDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountStatementRepository @Inject constructor(
    private val accountStatementDao: AccountStatementDao
){
    suspend fun insertAccountStatement(accountStatement: AccountStatementEntity) = accountStatementDao.insertAccountStatement(accountStatement)

    suspend fun getAccountStatementByPharmacyId(pharmacyId: String): AccountStatementEntity? = accountStatementDao.getAccountStatementByPharmacyId(pharmacyId)

    fun getAllAccountStatements(): Flow<List<AccountStatementEntity>> = accountStatementDao.getAllAccountStatements()

    suspend fun updateAccountStatement(accountStatement: AccountStatementEntity) = accountStatementDao.updateAccountStatement(accountStatement)

    suspend fun deleteAccountStatement(accountStatement: AccountStatementEntity) = accountStatementDao.deleteAccountStatement(accountStatement)



}