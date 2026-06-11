package com.tiffles.labrat.domain.usecase

import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.computeBiomarkerStatus
import com.tiffles.labrat.domain.model.BiomarkerTrend
import com.tiffles.labrat.domain.model.PinnedBiomarkerSummary
import com.tiffles.labrat.domain.repository.BiomarkerEntryRepository
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPinnedBiomarkerSummariesUseCase @Inject constructor(
    private val biomarkerRepository: BiomarkerRepository,
    private val entryRepository: BiomarkerEntryRepository,
) {
    operator fun invoke(): Flow<List<PinnedBiomarkerSummary>> =
        biomarkerRepository.getAll().map { biomarkers ->
            biomarkers
                .filter { it.isPinned && !it.isArchived }
                .mapNotNull { biomarker ->
                    val entries = entryRepository.getRecentEntries(biomarker.id, limit = 2)
                    if (entries.isEmpty()) return@mapNotNull null
                    val latest = entries[0]
                    val previous = entries.getOrNull(1)
                    PinnedBiomarkerSummary(
                        biomarkerId = biomarker.id,
                        name = biomarker.name,
                        latestValue = latest.value,
                        unit = biomarker.unit,
                        date = latest.date,
                        status = computeBiomarkerStatus(biomarker, latest.value),
                        trend = previous?.let { computeTrend(latest.value, it.value) },
                    )
                }
        }

    private fun computeTrend(latest: Double, previous: Double): BiomarkerTrend = when {
        latest > previous -> BiomarkerTrend.UP
        latest < previous -> BiomarkerTrend.DOWN
        else -> BiomarkerTrend.STABLE
    }
}