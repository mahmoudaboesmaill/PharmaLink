package com.pharma.link.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pharma.link.app.data.entities.PharmacyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PharmacyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPharmacy(pharmacy: PharmacyEntity)

    @Query("SELECT * FROM pharmacy_table WHERE pharmacyId = :pharmacyId")
    suspend fun getPharmacyById(pharmacyId: String): PharmacyEntity?

    @Query("SELECT * FROM pharmacy_table")
    fun getAllPharmacies(): Flow<List<PharmacyEntity>>

    @Update
    suspend fun updatePharmacy(pharmacy: PharmacyEntity)

    @Delete
    suspend fun deletePharmacy (pharmacy: PharmacyEntity)

}