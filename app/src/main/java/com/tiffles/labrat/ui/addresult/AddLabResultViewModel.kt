package com.tiffles.labrat.ui.addresult

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerEntry
import com.tiffles.labrat.domain.model.LabResult
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import com.tiffles.labrat.domain.usecase.SaveLabResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddLabResultViewModel @Inject constructor(
    private val saveLabResultUseCase: SaveLabResultUseCase,
    private val biomarkerRepository: BiomarkerRepository,
) : ViewModel() {

    @VisibleForTesting
    internal var testScope: CoroutineScope? = null
    private val scope get() = testScope ?: viewModelScope

    private val _uiState = MutableStateFlow(AddLabResultUiState())
    val uiState: StateFlow<AddLabResultUiState> = _uiState.asStateFlow()

    private val _navigateUp = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateUp: SharedFlow<Unit> = _navigateUp.asSharedFlow()

    private val _entryAdded = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val entryAdded: SharedFlow<Unit> = _entryAdded.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val filteredBiomarkers: StateFlow<List<Biomarker>> = combine(
        biomarkerRepository.getAll(),
        _searchQuery,
    ) { biomarkers, query ->
        biomarkers
            .filter { !it.isArchived }
            .filter { query.isBlank() || it.name.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun updateDate(date: LocalDate) = _uiState.update { it.copy(date = date) }
    fun updateLabName(name: String) = _uiState.update { it.copy(labName = name) }
    fun updateNotes(notes: String) = _uiState.update { it.copy(notes = notes) }
    fun updateSearchQuery(query: String) = _searchQuery.update { query }

    fun addEntry(draft: BiomarkerEntryDraft) {
        _uiState.update { it.copy(pendingEntries = it.pendingEntries + draft) }
        _searchQuery.update { "" }
        _entryAdded.tryEmit(Unit)
    }

    fun removeEntry(draft: BiomarkerEntryDraft) =
        _uiState.update { it.copy(pendingEntries = it.pendingEntries - draft) }

    fun saveLabResult() {
        scope.launch {
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