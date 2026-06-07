package com.tiffles.labrat.domain.usecase

import com.tiffles.labrat.domain.model.BiomarkerEntry
import com.tiffles.labrat.domain.model.LabResult
import com.tiffles.labrat.domain.repository.LabResultRepository
import javax.inject.Inject

class SaveLabResultUseCase @Inject constructor(
    private val labResultRepository: LabResultRepository,
) {
    suspend operator fun invoke(labResult: LabResult, entries: List<BiomarkerEntry>): Result<Unit> =
        runCatching {
            labResultRepository.saveWithEntries(labResult, entries)
        }
}