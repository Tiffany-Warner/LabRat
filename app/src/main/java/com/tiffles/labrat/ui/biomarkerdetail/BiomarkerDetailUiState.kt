package com.tiffles.labrat.ui.biomarkerdetail

import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerDetailEntry
import com.tiffles.labrat.domain.model.DateRange

sealed interface BiomarkerDetailUiState {
    data object Loading : BiomarkerDetailUiState
    data object NotFound : BiomarkerDetailUiState
    data class Success(
        val biomarker: Biomarker,
        val entries: List<BiomarkerDetailEntry>,
        val selectedRange: DateRange,
    ) : BiomarkerDetailUiState
}