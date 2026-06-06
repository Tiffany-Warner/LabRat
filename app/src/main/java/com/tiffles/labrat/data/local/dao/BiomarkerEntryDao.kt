package com.tiffles.labrat.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tiffles.labrat.data.local.entity.BiomarkerEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BiomarkerEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: BiomarkerEntryEntity): Long

    @Delete
    suspend fun delete(entry: BiomarkerEntryEntity)

    @Query("SELECT * FROM biomarker_entries WHERE biomarkerId = :biomarkerId ORDER BY rowid ASC")
    fun getEntriesForBiomarker(biomarkerId: Long): Flow<List<BiomarkerEntryEntity>>

    @Query("SELECT * FROM biomarker_entries WHERE labResultId = :labResultId")
    suspend fun getEntriesForLabResult(labResultId: Long): List<BiomarkerEntryEntity>
}