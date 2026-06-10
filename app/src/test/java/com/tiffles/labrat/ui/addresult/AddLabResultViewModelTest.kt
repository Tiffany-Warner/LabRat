package com.tiffles.labrat.ui.addresult

import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerCategory
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import com.tiffles.labrat.domain.usecase.SaveLabResultUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class AddLabResultViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val biomarkerRepository: BiomarkerRepository = mockk()
    private val saveLabResultUseCase: SaveLabResultUseCase = mockk()

    private val biomarkerFlow = MutableStateFlow(
        listOf(
            biomarker(id = 1, name = "Glucose (Fasting)", category = BiomarkerCategory.METABOLIC),
            biomarker(id = 2, name = "HbA1c", category = BiomarkerCategory.METABOLIC),
            biomarker(id = 3, name = "LDL Cholesterol", category = BiomarkerCategory.CARDIAC),
            biomarker(id = 4, name = "Archived Marker", isArchived = true),
        )
    )

    private lateinit var viewModel: AddLabResultViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { biomarkerRepository.getAll() } returns biomarkerFlow
        viewModel = AddLabResultViewModel(
            saveLabResultUseCase = saveLabResultUseCase,
            biomarkerRepository = biomarkerRepository,
        )
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }


    @Test
    fun addEntry_addsDraftToPendingEntries() = runTest {
        // Arrange
        val draft = testDraft()

        // Act
        viewModel.addEntry(draft)

        // Assert
        assertEquals(listOf(draft), viewModel.uiState.value.pendingEntries)
    }

    @Test
    fun addEntry_appendsMultipleDrafts() = runTest {
        // Act
        viewModel.addEntry(testDraft(biomarkerId = 1, biomarkerName = "Glucose"))
        viewModel.addEntry(testDraft(biomarkerId = 2, biomarkerName = "HbA1c"))

        // Assert
        assertEquals(2, viewModel.uiState.value.pendingEntries.size)
    }

    @Test
    fun addEntry_emitsOnEntryAdded() = runTest {
        // Arrange
        var emitted = false
        val job = launch { viewModel.entryAdded.collect { emitted = true } }
        advanceUntilIdle()

        // Act
        viewModel.addEntry(testDraft())
        advanceUntilIdle()

        // Assert
        assertTrue(emitted)
        job.cancel()
    }

    @Test
    fun addEntry_resetsSearchQuery() = runTest {
        // Arrange
        viewModel.updateSearchQuery("glucose")

        // Act
        viewModel.addEntry(testDraft())

        // Assert
        assertEquals("", viewModel.searchQuery.value)
    }

    @Test
    fun removeEntry_removesDraftFromPendingEntries() = runTest {
        // Arrange
        val draft = testDraft()
        viewModel.addEntry(draft)

        // Act
        viewModel.removeEntry(draft)

        // Assert
        assertTrue(viewModel.uiState.value.pendingEntries.isEmpty())
    }

    @Test
    fun removeEntry_onlyRemovesTargetEntry() = runTest {
        // Arrange
        val draft1 = testDraft(biomarkerId = 1, biomarkerName = "Glucose")
        val draft2 = testDraft(biomarkerId = 2, biomarkerName = "HbA1c")
        viewModel.addEntry(draft1)
        viewModel.addEntry(draft2)

        // Act
        viewModel.removeEntry(draft1)

        // Assert
        assertEquals(listOf(draft2), viewModel.uiState.value.pendingEntries)
    }

    @Test
    fun saveLabResult_emitsNavigateUp_onSuccess() = runTest {
        // Arrange
        coEvery { saveLabResultUseCase(any(), any()) } returns Result.success(Unit)
        viewModel.addEntry(testDraft())
        var navigated = false
        val job = launch { viewModel.navigateUp.collect { navigated = true } }
        advanceUntilIdle()

        // Act
        viewModel.saveLabResult()
        advanceUntilIdle()

        // Assert
        assertTrue(navigated)
        job.cancel()
    }

    @Test
    fun saveLabResult_setsError_onFailure() = runTest {
        // Arrange
        coEvery { saveLabResultUseCase(any(), any()) } returns Result.failure(Exception("Database error"))
        viewModel.addEntry(testDraft())

        // Act
        viewModel.saveLabResult()
        advanceUntilIdle()

        // Assert
        assertEquals("Database error", viewModel.uiState.value.error)
    }

    @Test
    fun saveLabResult_resetsIsSaving_onFailure() = runTest {
        // Arrange
        coEvery { saveLabResultUseCase(any(), any()) } returns Result.failure(Exception("Database error"))
        viewModel.addEntry(testDraft())

        // Act
        viewModel.saveLabResult()
        advanceUntilIdle()

        // Assert
        assertTrue(!viewModel.uiState.value.isSaving)
    }

    @Test
    fun saveLabResult_clearsPreviousError_onRetry() = runTest {
        // Arrange
        coEvery { saveLabResultUseCase(any(), any()) } returns Result.failure(Exception("Database error"))
        viewModel.addEntry(testDraft())
        viewModel.saveLabResult()
        advanceUntilIdle()

        // Act
        coEvery { saveLabResultUseCase(any(), any()) } returns Result.success(Unit)
        viewModel.saveLabResult()
        advanceUntilIdle()

        // Assert
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun updateDate_updatesUiState() = runTest {
        // Arrange
        val date = LocalDate.of(2026, 6, 7)

        // Act
        viewModel.updateDate(date)

        // Assert
        assertEquals(date, viewModel.uiState.value.date)
    }

    @Test
    fun updateLabName_updatesUiState() = runTest {
        // Act
        viewModel.updateLabName("Quest Diagnostics")

        // Assert
        assertEquals("Quest Diagnostics", viewModel.uiState.value.labName)
    }

    @Test
    fun updateNotes_updatesUiState() = runTest {
        // Act
        viewModel.updateNotes("Fasted 12 hours")

        // Assert
        assertEquals("Fasted 12 hours", viewModel.uiState.value.notes)
    }

    private fun testDraft(
        biomarkerId: Long = 1L,
        biomarkerName: String = "Glucose (Fasting)",
        unit: String = "mg/dL",
        value: Double = 92.0,
    ) = BiomarkerEntryDraft(
        biomarkerId = biomarkerId,
        biomarkerName = biomarkerName,
        unit = unit,
        value = value,
    )

    private fun biomarker(
        id: Long = 1L,
        name: String = "Test Biomarker",
        unit: String = "mg/dL",
        category: BiomarkerCategory = BiomarkerCategory.OTHER,
        isArchived: Boolean = false,
    ) = Biomarker(
        id = id,
        name = name,
        unit = unit,
        category = category,
        refRangeLow = null,
        refRangeHigh = null,
        isPinned = false,
        isArchived = isArchived,
    )
}