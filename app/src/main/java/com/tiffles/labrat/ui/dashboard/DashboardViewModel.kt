package com.tiffles.labrat.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiffles.labrat.domain.usecase.GetPinnedBiomarkerSummariesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getPinnedBiomarkerSummaries: GetPinnedBiomarkerSummariesUseCase,
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = getPinnedBiomarkerSummaries()
        .map { summaries ->
            if (summaries.isEmpty()) DashboardUiState.Empty
            else DashboardUiState.Success(summaries)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DashboardUiState.Loading)
}