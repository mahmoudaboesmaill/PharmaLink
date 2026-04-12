package com.pharma.link.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pharma.link.app.data.entities.ReturnEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReturnDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReturn(returnEntity: ReturnEntity)

    @Query("SELECT * FROM return_table WHERE returnId = :returnId")
    suspend fun getReturnById(returnId: Int): ReturnEntity?

    @Query("SELECT * FROM return_table WHERE invoiceId = :invoiceId")
    fun getReturnsByInvoiceId(invoiceId: Int): Flow<List<ReturnEntity>>

    @Query("SELECT * FROM return_table WHERE pharmacyId = :pharmacyId")
    fun getReturnsByPharmacyId(pharmacyId: String): Flow<List<ReturnEntity>>

    @Query("SELECT * FROM return_table")
    fun getAllReturns(): Flow<List<ReturnEntity>>

    @Update
    suspend fun updateReturn(returnEntity: ReturnEntity)

    @Delete
    suspend fun deleteReturn(returnEntity: ReturnEntity)

}