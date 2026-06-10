package com.tiffles.labrat.ui.biomarkers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BiomarkersViewModel @Inject constructor(
    private val biomarkerRepository: BiomarkerRepository,
) : ViewModel() {

    val uiState: StateFlow<BiomarkersUiState> = biomarkerRepository.getAll()
        .map { all -> BiomarkersUiState.Success(all.filter { !it.isArchived }) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), BiomarkersUiState.Loading)

    fun togglePin(biomarkerId: Long) {
        val biomarkers = (uiState.value as? BiomarkersUiState.Success)?.biomarkers ?: return
        val biomarker = biomarkers.find { it.id == biomarkerId } ?: return
        viewModelScope.launch {
            biomarkerRepository.update(biomarker.copy(isPinned = !biomarker.isPinned))
        }
    }
}