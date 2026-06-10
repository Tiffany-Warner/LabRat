package com.tiffles.labrat.domain.repository

import com.tiffles.labrat.domain.model.BiomarkerEntry
import com.tiffles.labrat.domain.model.BiomarkerEntryRecord
import kotlinx.coroutines.flow.Flow

interface BiomarkerEntryRepository {
    fun getEntriesForBiomarker(biomarkerId: Long): Flow<List<BiomarkerEntry>>
    suspend fun getEntriesForLabResult(labResultId: Long): List<BiomarkerEntry>
    /** Lightweight projection for summary views — returns only value and date, ordered most-recent first. */
    suspend fun getRecentEntries(biomarkerId: Long, limit: Int): List<BiomarkerEntryRecord>
    suspend fun save(entry: BiomarkerEntry)
    suspend fun delete(entry: BiomarkerEntry)
}