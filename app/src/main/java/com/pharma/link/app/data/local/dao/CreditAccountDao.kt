package com.pharma.link.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pharma.link.app.data.entities.CreditAccountEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CreditAccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreditAccount(creditAccount: CreditAccountEntity)

    @Query("SELECT * FROM credit_account_table WHERE pharmacyId = :pharmacyId")
    suspend fun getCreditAccountByPharmacyId(pharmacyId: String): CreditAccountEntity?

    @Query("SELECT * FROM credit_account_table")
    fun getAllCreditAccounts(): Flow<List<CreditAccountEntity>>

    @Update
    suspend fun updateCreditAccount(creditAccount: CreditAccountEntity)

    @Delete
    suspend fun deleteCreditAccount(creditAccount: CreditAccountEntity)


}