package com.tiffles.labrat.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tiffles.labrat.data.local.entity.LabResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LabResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(labResult: LabResultEntity): Long

    @Update
    suspend fun update(labResult: LabResultEntity)

    @Delete
    suspend fun delete(labResult: LabResultEntity)

    @Query("SELECT * FROM lab_results ORDER BY dateEpochDay DESC")
    fun getAll(): Flow<List<LabResultEntity>>

    @Query("SELECT * FROM lab_results WHERE id = :id")
    suspend fun getById(id: Long): LabResultEntity?
}