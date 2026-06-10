package com.tiffles.labrat

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiffles.labrat.data.local.AppDatabase
import com.tiffles.labrat.data.local.BiomarkerSeeds
import com.tiffles.labrat.data.local.dao.BiomarkerDao
import com.tiffles.labrat.data.local.dao.BiomarkerEntryDao
import com.tiffles.labrat.data.local.dao.LabResultDao
import com.tiffles.labrat.data.repository.BiomarkerEntryRepositoryImpl
import com.tiffles.labrat.data.repository.BiomarkerRepositoryImpl
import com.tiffles.labrat.data.repository.LabResultRepositoryImpl
import com.tiffles.labrat.di.DatabaseModule
import com.tiffles.labrat.domain.repository.BiomarkerEntryRepository
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import com.tiffles.labrat.domain.repository.LabResultRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
@RunWith(AndroidJUnit4::class)
class LabRatEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    // --- Add Lab Result Flow ---

    @Test
    fun addLabResult_fullFlow_savesAndReturns() {
        // Arrange — start on Dashboard empty state
        composeTestRule
            .onNodeWithText("Pin your first biomarker to get started")
            .assertIsDisplayed()

        // Act — tap FAB to start adding
        composeTestRule
            .onNodeWithContentDescription("Add Lab Result")
            .performClick()

        // Assert — we're on the New Lab Result screen
        composeTestRule
            .onNodeWithText("New Lab Result")
            .assertIsDisplayed()

        // Act — tap Add Values to open picker
        composeTestRule
            .onNodeWithText("Add Values")
            .performClick()

        // Assert — we're on the picker screen
        composeTestRule
            .onNodeWithText("Search biomarkers")
            .assertIsDisplayed()

        // Act — search for Glucose and select it
        composeTestRule
            .onNodeWithText("Search biomarkers")
            .performTextInput("Glucose")
        composeTestRule
            .onNodeWithText("Glucose (Fasting)")
            .performClick()

        // Assert — value input dialog appears
        composeTestRule
            .onNodeWithText("Value (mg/dL)")
            .assertIsDisplayed()

        // Act — enter value and confirm
        composeTestRule
            .onNodeWithText("Value (mg/dL)")
            .performTextInput("92")
        composeTestRule
            .onNodeWithText("Add")
            .performClick()

        // Assert — back on Add Lab Result with entry shown
        composeTestRule
            .onNodeWithText("Glucose (Fasting)")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("92.0 mg/dL")
            .assertIsDisplayed()

        // Act — save
        composeTestRule
            .onNodeWithText("Save")
            .performClick()

        // Assert — back on Dashboard
        composeTestRule
            .onNodeWithText("Pin your first biomarker to get started")
            .assertIsDisplayed()
    }

    // --- Pin Biomarker Flow ---

    @Test
    fun pinBiomarker_afterAddingEntry_showsOnDashboard() {
        // Arrange — add a lab result first
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "92")

        // Act — navigate to Biomarkers tab
        composeTestRule
            .onNodeWithText("Biomarkers")
            .performClick()

        // Act — find Glucose and pin it
        composeTestRule
            .onNodeWithContentDescription("Pin Glucose (Fasting)")
            .performClick()

        // Act — navigate back to Dashboard
        composeTestRule
            .onNodeWithText("Dashboard")
            .performClick()

        // Assert — pinned biomarker card appears
        composeTestRule
            .onNodeWithText("Glucose (Fasting)")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("92.0 mg/dL")
            .assertIsDisplayed()
    }

    // --- Trend Arrow ---

    @Test
    fun trendArrow_showsUp_whenSecondEntryIsHigher() {
        // Arrange — add first lab result
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "88")

        // Arrange — add second lab result with higher value
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "95")

        // Act — pin the biomarker
        composeTestRule
            .onNodeWithText("Biomarkers")
            .performClick()
        composeTestRule
            .onNodeWithContentDescription("Pin Glucose (Fasting)")
            .performClick()

        // Act — go to Dashboard
        composeTestRule
            .onNodeWithText("Dashboard")
            .performClick()

        // Assert — trend arrow shows up
        composeTestRule
            .onNodeWithText("↑")
            .assertIsDisplayed()
    }

    @Test
    fun trendArrow_showsDown_whenSecondEntryIsLower() {
        // Arrange — add first lab result
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "95")

        // Arrange — add second lab result with lower value
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "88")

        // Act — pin the biomarker
        composeTestRule
            .onNodeWithText("Biomarkers")
            .performClick()
        composeTestRule
            .onNodeWithContentDescription("Pin Glucose (Fasting)")
            .performClick()

        // Act — go to Dashboard
        composeTestRule
            .onNodeWithText("Dashboard")
            .performClick()

        // Assert — trend arrow shows down
        composeTestRule
            .onNodeWithText("↓")
            .assertIsDisplayed()
    }

    @Test
    fun trendArrow_notShown_whenOnlyOneEntry() {
        // Arrange — add single lab result
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "92")

        // Act — pin the biomarker
        composeTestRule
            .onNodeWithText("Biomarkers")
            .performClick()
        composeTestRule
            .onNodeWithContentDescription("Pin Glucose (Fasting)")
            .performClick()

        // Act — go to Dashboard
        composeTestRule
            .onNodeWithText("Dashboard")
            .performClick()

        // Assert — no trend arrow
        composeTestRule.onNodeWithText("↑").assertDoesNotExist()
        composeTestRule.onNodeWithText("↓").assertDoesNotExist()
        composeTestRule.onNodeWithText("→").assertDoesNotExist()
    }

    @Test
    fun trendArrow_showsStable_whenBothEntriesAreEqual() {
        // Arrange
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "92")
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "92")

        // Act — pin and go to Dashboard
        composeTestRule.onNodeWithText("Biomarkers").performClick()
        composeTestRule.onNodeWithContentDescription("Pin Glucose (Fasting)").performClick()
        composeTestRule.onNodeWithText("Dashboard").performClick()

        // Assert
        composeTestRule.onNodeWithText("→").assertIsDisplayed()
    }

    @Test
    fun trendArrow_comparesOnlyLatestTwoEntries() {
        // Arrange — three entries: 80, 100, 95
        // Trend should be DOWN (95 vs 100), not UP (95 vs 80)
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "80")
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "100")
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "95")

        // Act — pin and go to Dashboard
        composeTestRule.onNodeWithText("Biomarkers").performClick()
        composeTestRule.onNodeWithContentDescription("Pin Glucose (Fasting)").performClick()
        composeTestRule.onNodeWithText("Dashboard").performClick()

        // Assert — compares 95 vs 100, not 95 vs 80
        composeTestRule.onNodeWithText("↓").assertIsDisplayed()
    }

    // --- Unpin ---

    @Test
    fun unpinBiomarker_removesFromDashboard() {
        // Arrange — add entry, pin, verify it shows
        addLabResultViaUi(biomarkerName = "Glucose (Fasting)", value = "92")
        composeTestRule.onNodeWithText("Biomarkers").performClick()
        composeTestRule.onNodeWithContentDescription("Pin Glucose (Fasting)").performClick()
        composeTestRule.onNodeWithText("Dashboard").performClick()
        composeTestRule.onNodeWithText("Glucose (Fasting)").assertIsDisplayed()

        // Act — go back to Biomarkers and unpin
        composeTestRule.onNodeWithText("Biomarkers").performClick()
        composeTestRule.onNodeWithContentDescription("Unpin Glucose (Fasting)").performClick()

        // Act — back to Dashboard
        composeTestRule.onNodeWithText("Dashboard").performClick()

        // Assert — empty state returns
        composeTestRule
            .onNodeWithText("Pin your first biomarker to get started")
            .assertIsDisplayed()
    }

    // --- Helper: Add a lab result through the UI ---

    private fun addLabResultViaUi(biomarkerName: String, value: String) {
        // Navigate to Dashboard first (in case we're on another tab)
        composeTestRule
            .onNodeWithText("Dashboard")
            .performClick()

        // Tap FAB
        composeTestRule
            .onNodeWithContentDescription("Add Lab Result")
            .performClick()

        // Tap Add Values
        composeTestRule
            .onNodeWithText("Add Values")
            .performClick()

        // Search and select biomarker
        composeTestRule
            .onNodeWithText("Search biomarkers")
            .performTextInput(biomarkerName.split(" ").first())
        composeTestRule
            .onNodeWithText(biomarkerName)
            .performClick()

        // Enter value and confirm
        composeTestRule
            .onNodeWithText("Value (mg/dL)")
            .performTextInput(value)
        composeTestRule
            .onNodeWithText("Add")
            .performClick()

        // Save
        composeTestRule
            .onNodeWithText("Save")
            .performClick()
    }

    // --- Test Hilt Module ---

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TestDatabaseModule {

        @Suppress("unused")

        @Binds @Singleton
        abstract fun bindBiomarkerRepository(impl: BiomarkerRepositoryImpl): BiomarkerRepository

        @Suppress("unused")
        @Binds @Singleton
        abstract fun bindLabResultRepository(impl: LabResultRepositoryImpl): LabResultRepository

        @Suppress("unused")
        @Binds @Singleton
        abstract fun bindBiomarkerEntryRepository(impl: BiomarkerEntryRepositoryImpl): BiomarkerEntryRepository

        companion object {

            @Provides @Singleton
            fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
                var db: AppDatabase? = null
                db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                    .allowMainThreadQueries()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(sqLiteDatabase: SupportSQLiteDatabase) {
                            super.onCreate(sqLiteDatabase)
                            CoroutineScope(Dispatchers.IO).launch {
                                db?.biomarkerDao()?.insertAll(BiomarkerSeeds.all)
                            }
                        }
                    })
                    .build()
                return db
            }

            @Provides
            fun provideBiomarkerDao(db: AppDatabase): BiomarkerDao = db.biomarkerDao()

            @Provides
            fun provideLabResultDao(db: AppDatabase): LabResultDao = db.labResultDao()

            @Provides
            fun provideBiomarkerEntryDao(db: AppDatabase): BiomarkerEntryDao = db.biomarkerEntryDao()
        }
    }
}