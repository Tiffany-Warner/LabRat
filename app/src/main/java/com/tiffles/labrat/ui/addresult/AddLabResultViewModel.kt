package com.tiffles.labrat.ui.addresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiffles.labrat.domain.model.BiomarkerEntry
import com.tiffles.labrat.domain.model.LabResult
import com.tiffles.labrat.domain.usecase.SaveLabResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddLabResultViewModel @Inject constructor(
    private val saveLabResultUseCase: SaveLabResultUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddLabResultUiState())
    val uiState: StateFlow<AddLabResultUiState> = _uiState.asStateFlow()

    private val _navigateUp = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateUp: SharedFlow<Unit> = _navigateUp.asSharedFlow()

    fun updateDate(date: LocalDate) = _uiState.update { it.copy(date = date) }
    fun updateLabName(name: String) = _uiState.update { it.copy(labName = name) }
    fun updateNotes(notes: String) = _uiState.update { it.copy(notes = notes) }

    @Suppress("unused") // called by BiomarkerPickerScreen (Phase 2.2)
    fun addEntry(draft: BiomarkerEntryDraft) =
        _uiState.update { it.copy(pendingEntries = it.pendingEntries + draft) }

    fun removeEntry(draft: BiomarkerEntryDraft) =
        _uiState.update { it.copy(pendingEntries = it.pendingEntries - draft) }

    fun saveLabResult() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = null) }
            val state = _uiState.value
            val labResult = LabResult(
                id = 0,
                date = state.date,
                labName = state.labName.takeIf { it.isNotBlank() },
                notes = state.notes.takeIf { it.isNotBlank() },
            )
            val entries = state.pendingEntries.map { draft ->
                BiomarkerEntry(id = 0, labResultId = 0, biomarkerId = draft.biomarkerId, value = draft.value)
            }
            saveLabResultUseCase(labResult, entries)
                .onSuccess { _navigateUp.emit(Unit) }
                .onFailure { e ->
                    _uiState.update { it.copy(isSaving = false, error = e.message) }
                }
        }
    }
}