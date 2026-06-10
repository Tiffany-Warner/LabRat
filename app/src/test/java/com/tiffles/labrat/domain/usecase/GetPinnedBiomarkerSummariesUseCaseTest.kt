package com.tiffles.labrat.domain.usecase

import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerCategory
import com.tiffles.labrat.domain.model.BiomarkerEntryRecord
import com.tiffles.labrat.domain.model.BiomarkerStatus
import com.tiffles.labrat.domain.model.BiomarkerTrend
import com.tiffles.labrat.domain.repository.BiomarkerEntryRepository
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetPinnedBiomarkerSummariesUseCaseTest {

    private val biomarkerRepository: BiomarkerRepository = mockk()
    private val entryRepository: BiomarkerEntryRepository = mockk()

    private val biomarkerFlow = MutableStateFlow<List<Biomarker>>(emptyList())

    private lateinit var useCase: GetPinnedBiomarkerSummariesUseCase

    @Before
    fun setup() {
        every { biomarkerRepository.getAll() } returns biomarkerFlow
        useCase = GetPinnedBiomarkerSummariesUseCase(
            biomarkerRepository = biomarkerRepository,
            entryRepository = entryRepository,
        )
    }

    // --- Filtering ---

    @Test
    fun invoke_returnsEmpty_whenNoBiomarkersPinned() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = false),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun invoke_excludesArchivedBiomarkers() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, isArchived = true),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun invoke_excludesBiomarkersWithNoEntries() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns emptyList()

        // Act
        val result = useCase().first()

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun invoke_returnsSummary_whenBiomarkerIsPinnedAndHasEntries() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 92.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Glucose (Fasting)", result[0].name)
    }

    // --- Summary mapping ---

    @Test
    fun invoke_mapsFieldsCorrectly() = runTest {
        // Arrange
        val date = LocalDate.of(2026, 6, 7)
        biomarkerFlow.value = listOf(
            testBiomarker(id = 5, name = "HbA1c", unit = "%", isPinned = true),
        )
        coEvery { entryRepository.getRecentEntries(5, 2) } returns listOf(
            testEntry(value = 5.4, date = date),
        )

        // Act
        val result = useCase().first()

        // Assert
        val summary = result[0]
        assertEquals(5L, summary.biomarkerId)
        assertEquals("HbA1c", summary.name)
        assertEquals(5.4, summary.latestValue, 0.001)
        assertEquals("%", summary.unit)
        assertEquals(date, summary.date)
    }

    // --- Status: NEUTRAL ---

    @Test
    fun computeStatus_returnsNeutral_whenNoReferenceRange() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = null, refRangeHigh = null),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 92.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.NEUTRAL, result[0].status)
    }

    // --- Status: IN_RANGE ---

    @Test
    fun computeStatus_returnsInRange_whenValueWithinBothBounds() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = 70.0, refRangeHigh = 99.0),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 85.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.IN_RANGE, result[0].status)
    }

    @Test
    fun computeStatus_returnsInRange_whenValueAtLowBound() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = 70.0, refRangeHigh = 99.0),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 70.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.IN_RANGE, result[0].status)
    }

    @Test
    fun computeStatus_returnsInRange_whenValueAtHighBound() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = 70.0, refRangeHigh = 99.0),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 99.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.IN_RANGE, result[0].status)
    }

    @Test
    fun computeStatus_returnsInRange_whenOnlyLowBound_andValueAbove() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = 70.0, refRangeHigh = null),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 85.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.IN_RANGE, result[0].status)
    }

    @Test
    fun computeStatus_returnsInRange_whenOnlyHighBound_andValueBelow() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = null, refRangeHigh = 99.0),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 85.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.IN_RANGE, result[0].status)
    }

    // --- Status: BORDERLINE ---

    @Test
    fun computeStatus_returnsBorderline_whenSlightlyBelowLowBound() = runTest {
        // Arrange — low is 70, 10% below is 63. Value 65 is between 63 and 70.
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = 70.0, refRangeHigh = 99.0),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 65.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.BORDERLINE, result[0].status)
    }

    @Test
    fun computeStatus_returnsBorderline_whenSlightlyAboveHighBound() = runTest {
        // Arrange — high is 99, 10% above is 108.9. Value 105 is between 99 and 108.9.
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = 70.0, refRangeHigh = 99.0),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 105.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.BORDERLINE, result[0].status)
    }

    // --- Status: OUT_OF_RANGE ---

    @Test
    fun computeStatus_returnsOutOfRange_whenFarBelowLowBound() = runTest {
        // Arrange — low is 70, 10% below is 63. Value 50 is well below.
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = 70.0, refRangeHigh = 99.0),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 50.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.OUT_OF_RANGE, result[0].status)
    }

    @Test
    fun computeStatus_returnsOutOfRange_whenFarAboveHighBound() = runTest {
        // Arrange — high is 99, 10% above is 108.9. Value 120 is well above.
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true, refRangeLow = 70.0, refRangeHigh = 99.0),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 120.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerStatus.OUT_OF_RANGE, result[0].status)
    }

    // --- Trend ---

    @Test
    fun computeTrend_returnsUp_whenLatestIsHigher() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 95.0),
            testEntry(value = 88.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerTrend.UP, result[0].trend)
    }

    @Test
    fun computeTrend_returnsDown_whenLatestIsLower() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 82.0),
            testEntry(value = 88.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerTrend.DOWN, result[0].trend)
    }

    @Test
    fun computeTrend_returnsStable_whenValuesAreEqual() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 88.0),
            testEntry(value = 88.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(BiomarkerTrend.STABLE, result[0].trend)
    }

    @Test
    fun computeTrend_returnsNull_whenOnlyOneEntry() = runTest {
        // Arrange
        biomarkerFlow.value = listOf(
            testBiomarker(id = 1, isPinned = true),
        )
        coEvery { entryRepository.getRecentEntries(1, 2) } returns listOf(
            testEntry(value = 88.0),
        )

        // Act
        val result = useCase().first()

        // Assert
        assertNull(result[0].trend)
    }

    // --- Helpers ---

    private fun testBiomarker(
        id: Long = 1L,
        name: String = "Glucose (Fasting)",
        unit: String = "mg/dL",
        category: BiomarkerCategory = BiomarkerCategory.METABOLIC,
        refRangeLow: Double? = 70.0,
        refRangeHigh: Double? = 99.0,
        isPinned: Boolean = false,
        isArchived: Boolean = false,
    ) = Biomarker(
        id = id,
        name = name,
        unit = unit,
        category = category,
        refRangeLow = refRangeLow,
        refRangeHigh = refRangeHigh,
        isPinned = isPinned,
        isArchived = isArchived,
    )

    private fun testEntry(
        value: Double = 92.0,
        date: LocalDate = LocalDate.of(2026, 6, 7),
    ) = BiomarkerEntryRecord(
        value = value,
        date = date,
    )
}
