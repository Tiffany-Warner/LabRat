package com.tiffles.labrat.domain.usecase

import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerStatus
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
                        status = computeStatus(biomarker, latest.value),
                        trend = previous?.let { computeTrend(latest.value, it.value) },
                    )
                }
        }

    private fun computeStatus(biomarker: Biomarker, value: Double): BiomarkerStatus {
        val low = biomarker.refRangeLow
        val high = biomarker.refRangeHigh
        if (low == null && high == null) return BiomarkerStatus.NEUTRAL

        val belowLow = low != null && value < low
        val aboveHigh = high != null && value > high
        if (!belowLow && !aboveHigh) return BiomarkerStatus.IN_RANGE

        val borderline = when {
            belowLow -> value >= low!! * 0.90
            aboveHigh -> value <= high!! * 1.10
            else -> false
        }
        return if (borderline) BiomarkerStatus.BORDERLINE else BiomarkerStatus.OUT_OF_RANGE
    }

    private fun computeTrend(latest: Double, previous: Double): BiomarkerTrend = when {
        latest > previous -> BiomarkerTrend.UP
        latest < previous -> BiomarkerTrend.DOWN
        else -> BiomarkerTrend.STABLE
    }
}