package com.tiffles.labrat.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tiffles.labrat.data.local.entity.BiomarkerEntryEntity
import com.tiffles.labrat.data.local.entity.BiomarkerEntryWithDate
import kotlinx.coroutines.flow.Flow

@Dao
interface BiomarkerEntryDao {

    @Query("""
        SELECT be.value, lr.dateEpochDay
        FROM biomarker_entries be
        INNER JOIN lab_results lr ON be.labResultId = lr.id
        WHERE be.biomarkerId = :biomarkerId
        ORDER BY lr.dateEpochDay DESC, be.id DESC
    """)
    fun getAllEntriesWithDate(biomarkerId: Long): Flow<List<BiomarkerEntryWithDate>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: BiomarkerEntryEntity): Long

    @Delete
    suspend fun delete(entry: BiomarkerEntryEntity)

    @Query("SELECT * FROM biomarker_entries WHERE biomarkerId = :biomarkerId ORDER BY id ASC")
    fun getEntriesForBiomarker(biomarkerId: Long): Flow<List<BiomarkerEntryEntity>>

    @Query("SELECT * FROM biomarker_entries WHERE labResultId = :labResultId")
    suspend fun getEntriesForLabResult(labResultId: Long): List<BiomarkerEntryEntity>

    @Query("""
        SELECT be.value, lr.dateEpochDay
        FROM biomarker_entries be
        INNER JOIN lab_results lr ON be.labResultId = lr.id
        WHERE be.biomarkerId = :biomarkerId
        ORDER BY lr.dateEpochDay DESC, be.id DESC
        LIMIT :limit
    """)
    suspend fun getRecentEntriesWithDate(biomarkerId: Long, limit: Int): List<BiomarkerEntryWithDate>
}