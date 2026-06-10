package com.tiffles.labrat.data.repository

import com.tiffles.labrat.data.local.dao.BiomarkerEntryDao
import com.tiffles.labrat.data.local.mapper.toDomain
import com.tiffles.labrat.data.local.mapper.toEntity
import com.tiffles.labrat.domain.model.BiomarkerEntry
import com.tiffles.labrat.domain.model.BiomarkerEntryRecord
import com.tiffles.labrat.domain.repository.BiomarkerEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class BiomarkerEntryRepositoryImpl @Inject constructor(
    private val dao: BiomarkerEntryDao
) : BiomarkerEntryRepository {

    override fun getEntriesForBiomarker(biomarkerId: Long): Flow<List<BiomarkerEntry>> =
        dao.getEntriesForBiomarker(biomarkerId).map { entities -> entities.map { it.toDomain() } }

    override suspend fun getEntriesForLabResult(labResultId: Long): List<BiomarkerEntry> =
        dao.getEntriesForLabResult(labResultId).map { it.toDomain() }

    override suspend fun getRecentEntries(biomarkerId: Long, limit: Int): List<BiomarkerEntryRecord> =
        dao.getRecentEntriesWithDate(biomarkerId, limit).map { entry ->
            BiomarkerEntryRecord(
                value = entry.value,
                date = LocalDate.ofEpochDay(entry.dateEpochDay),
            )
        }

    override suspend fun save(entry: BiomarkerEntry) {
        dao.insert(entry.toEntity())
    }

    override suspend fun delete(entry: BiomarkerEntry) =
        dao.delete(entry.toEntity())
}