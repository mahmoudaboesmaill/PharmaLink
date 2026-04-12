package com.pharma.link.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pharma.link.app.data.entities.AccountStatementEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AccountStatementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountStatement(accountStatement: AccountStatementEntity)

    @Query("SELECT * FROM account_statement_table WHERE pharmacyId = :pharmacyId")
    suspend fun getAccountStatementByPharmacyId(pharmacyId: String): AccountStatementEntity?

    @Query("SELECT * FROM account_statement_table")
    fun getAllAccountStatements(): Flow< List<AccountStatementEntity>>

    @Update
    suspend fun updateAccountStatement(accountStatement: AccountStatementEntity)

    @Delete
    suspend fun deleteAccountStatement(accountStatement: AccountStatementEntity)
}
