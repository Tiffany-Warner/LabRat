package com.tiffles.labrat.domain.repository

import com.tiffles.labrat.domain.model.BiomarkerEntry
import kotlinx.coroutines.flow.Flow

interface BiomarkerEntryRepository {
    fun getEntriesForBiomarker(biomarkerId: Long): Flow<List<BiomarkerEntry>>
    suspend fun getEntriesForLabResult(labResultId: Long): List<BiomarkerEntry>
    suspend fun save(entry: BiomarkerEntry)
    suspend fun delete(entry: BiomarkerEntry)
}