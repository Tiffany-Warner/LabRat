package com.tiffles.labrat.data.local.dao

import org.junit.After
import org.junit.Before

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiffles.labrat.data.local.AppDatabase
import com.tiffles.labrat.data.local.entity.BiomarkerEntity
import com.tiffles.labrat.data.local.entity.BiomarkerEntryEntity
import com.tiffles.labrat.data.local.entity.LabResultEntity
import com.tiffles.labrat.domain.model.BiomarkerCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LabResultDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var labResultDao: LabResultDao
    private lateinit var biomarkerDao: BiomarkerDao
    private lateinit var biomarkerEntryDao: BiomarkerEntryDao

    // Reusable test fixtures
    private val testLabResult = LabResultEntity(
        id = 0,
        dateEpochDay = 19_000L, // some arbitrary date
        labName = "Quest Diagnostics",
        notes = "Fasted 12 hours",
    )

    private val testBiomarker = BiomarkerEntity(
        id = 1,
        name = "Glucose (Fasting)",
        unit = "mg/dL",
        category = BiomarkerCategory.METABOLIC,
        refRangeLow = 70.0,
        refRangeHigh = 99.0,
        isPinned = false,
        isArchived = false,
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        labResultDao = database.labResultDao()
        biomarkerDao = database.biomarkerDao()
        biomarkerEntryDao = database.biomarkerEntryDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertWithEntries_savesLabResult() = runBlocking {
        //Arrange
        biomarkerDao.insertAll(listOf(testBiomarker))

        val entries = listOf(
            BiomarkerEntryEntity(id = 0, labResultId = 0, biomarkerId = 1, value = 92.0)
        )
        //Act
        labResultDao.insertWithEntries(testLabResult, entries)

        //Assert
        val saved = labResultDao.getAll().first()
        assertEquals(1, saved.size)
        assertEquals("Quest Diagnostics", saved.first().labName)
    }

    @Test
    fun insertWithEntries_savesAllEntries() = runBlocking {
        //Arrange
        biomarkerDao.insertAll(listOf(testBiomarker))

        val entries = listOf(
            BiomarkerEntryEntity(id = 0, labResultId = 0, biomarkerId = 1, value = 92.0),
            BiomarkerEntryEntity(id = 0, labResultId = 0, biomarkerId = 1, value = 95.0),
            BiomarkerEntryEntity(id = 0, labResultId = 0, biomarkerId = 1, value = 88.0),
        )
        //Act
        labResultDao.insertWithEntries(testLabResult, entries)

        //Assert
        val resultId = labResultDao.getAll().first().first().id
        val savedEntries = biomarkerEntryDao.getEntriesForLabResult(resultId)
        assertEquals(3, savedEntries.size)
    }

    @Test
    fun insertWithEntries_assignsCorrectLabResultIdToEntries() = runBlocking {
        //Arrange
        biomarkerDao.insertAll(listOf(testBiomarker))

        val entries = listOf(
            BiomarkerEntryEntity(id = 0, labResultId = 0, biomarkerId = 1, value = 92.0)
        )
        //Act
        labResultDao.insertWithEntries(testLabResult, entries)

        //Assert
        val resultId = labResultDao.getAll().first().first().id
        val savedEntries = biomarkerEntryDao.getEntriesForLabResult(resultId)

        // Entry should have the generated lab result ID, not 0
        assertTrue(savedEntries.all { it.labResultId == resultId })
    }

    @Test
    fun insertWithEntries_entryLabResultIdIsNotZero() = runBlocking {
        //Arrange
        biomarkerDao.insertAll(listOf(testBiomarker))

        val entries = listOf(
            BiomarkerEntryEntity(id = 0, labResultId = 0, biomarkerId = 1, value = 92.0)
        )
        //Act
        labResultDao.insertWithEntries(testLabResult, entries)

        //Assert
        val resultId = labResultDao.getAll().first().first().id
        val savedEntries = biomarkerEntryDao.getEntriesForLabResult(resultId)

        assertTrue(savedEntries.none { it.labResultId == 0L })
    }

    @Test
    fun getAll_returnsEmptyList_whenNothingSaved() = runBlocking {
        //Act
        val results = labResultDao.getAll().first()
        assertTrue(results.isEmpty())
    }

    @Test
    fun getAll_returnsAllSavedLabResults() = runBlocking {
        //Arrange
        val second = testLabResult.copy(dateEpochDay = 19_001L, labName = "LabCorp")
        labResultDao.insert(testLabResult)
        labResultDao.insert(second)

        //Act
        val results = labResultDao.getAll().first()

        //Assert
        assertEquals(2, results.size)
    }

    @Test
    fun getAll_returnsMostRecentFirst() = runBlocking {
        //Arrange
        val older = testLabResult.copy(dateEpochDay = 18_000L, labName = "Older")
        val newer = testLabResult.copy(dateEpochDay = 19_000L, labName = "Newer")
        labResultDao.insert(older)
        labResultDao.insert(newer)

        //Act
        val results = labResultDao.getAll().first()

        //Assert
        assertEquals("Newer", results.first().labName)
    }

    @Test
    fun delete_removesLabResult() = runBlocking {
        //Arrange
        val id = labResultDao.insert(testLabResult)
        val inserted = labResultDao.getById(id)!!

        //Act
        labResultDao.delete(inserted)

        //Assert
        val results = labResultDao.getAll().first()
        assertTrue(results.isEmpty())
    }

    @Test
    fun delete_doesNotAffectOtherLabResults() = runBlocking {
        //Arrange
        val id1 = labResultDao.insert(testLabResult)
        labResultDao.insert(testLabResult.copy(dateEpochDay = 19_001L, labName = "LabCorp"))

        val toDelete = labResultDao.getById(id1)!!
        //Act
        labResultDao.delete(toDelete)

        //Assert
        val results = labResultDao.getAll().first()
        assertEquals(1, results.size)
        assertEquals("LabCorp", results.first().labName)
    }

    @Test
    fun getById_returnsCorrectLabResult() = runBlocking {
        //Arrange
        val id = labResultDao.insert(testLabResult)

        //Act
        val result = labResultDao.getById(id)

        //Assert
        assertEquals("Quest Diagnostics", result?.labName)
    }

    @Test
    fun getById_returnsNull_whenNotFound() = runBlocking {
        //Act
        val result = labResultDao.getById(999L)

        //Assert
        assertEquals(null, result)
    }
}