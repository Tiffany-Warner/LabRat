package com.tiffles.labrat.ui.biomarkers

import com.tiffles.labrat.domain.model.Biomarker

sealed interface BiomarkersUiState {
    data object Loading : BiomarkersUiState
    data class Success(val biomarkers: List<Biomarker>) : BiomarkersUiState
}