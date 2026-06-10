package com.tiffles.labrat.ui.dashboard

import com.tiffles.labrat.domain.model.PinnedBiomarkerSummary

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data object Empty : DashboardUiState
    data class Success(val summaries: List<PinnedBiomarkerSummary>) : DashboardUiState
}