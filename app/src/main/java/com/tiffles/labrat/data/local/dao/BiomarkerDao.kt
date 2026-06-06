package com.tiffles.labrat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tiffles.labrat.data.local.entity.BiomarkerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BiomarkerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(biomarker: BiomarkerEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(biomarkers: List<BiomarkerEntity>)

    @Update
    suspend fun update(biomarker: BiomarkerEntity)

    @Query("SELECT * FROM biomarkers WHERE isArchived = 0 ORDER BY name ASC")
    fun getAll(): Flow<List<BiomarkerEntity>>

    @Query("SELECT * FROM biomarkers WHERE id = :id")
    suspend fun getById(id: Long): BiomarkerEntity?

    @Query("SELECT * FROM biomarkers WHERE isPinned = 1 AND isArchived = 0 ORDER BY name ASC")
    suspend fun getPinned(): List<BiomarkerEntity>

    @Query("SELECT COUNT(*) FROM biomarkers")
    suspend fun getCount(): Int
}
