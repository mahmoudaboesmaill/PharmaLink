package com.pharma.link.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pharma.link.app.data.entities.SalesRepEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SalesRepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalesRep(salesRep: SalesRepEntity)

    @Query("SELECT * FROM sales_rep_table WHERE repId = :repId")
    suspend fun getSalesRepById(repId: String): SalesRepEntity?

    @Query("SELECT * FROM sales_rep_table")
    fun getAllSalesReps(): Flow<List<SalesRepEntity>>

    @Update
    suspend fun updateSalesRep(salesRep: SalesRepEntity)

    @Delete
    suspend fun deleteSalesRep(salesRep: SalesRepEntity)
}