package com.tiffles.labrat.ui.dashboard

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiffles.labrat.domain.model.BiomarkerStatus
import com.tiffles.labrat.domain.model.BiomarkerTrend
import com.tiffles.labrat.domain.model.PinnedBiomarkerSummary
import com.tiffles.labrat.ui.theme.LabRatTheme
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val onNavigateToBiomarkers: () -> Unit = mockk(relaxed = true)

    // --- Loading state ---

    @Test
    fun loadingState_doesNotShowEmptyMessage() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Loading,
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Pin your first biomarker to get started")
            .assertDoesNotExist()
    }

    // --- Empty state ---

    @Test
    fun emptyState_showsEmptyMessage() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Empty,
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Pin your first biomarker to get started")
            .assertIsDisplayed()
    }

    @Test
    fun emptyState_showsGoToBiomarkersButton() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Empty,
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Go to Biomarkers")
            .assertIsDisplayed()
    }

    @Test
    fun emptyState_callsOnNavigateToBiomarkers_whenButtonClicked() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Empty,
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithText("Go to Biomarkers")
            .performClick()

        // Assert
        verify { onNavigateToBiomarkers() }
    }

    // --- Success state ---

    @Test
    fun successState_showsBiomarkerName() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Success(listOf(testSummary())),
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Glucose (Fasting)")
            .assertIsDisplayed()
    }

    @Test
    fun successState_showsValueAndUnit() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Success(listOf(testSummary())),
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("92.0 mg/dL")
            .assertIsDisplayed()
    }

    @Test
    fun successState_showsFormattedDate() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Success(
                        listOf(testSummary(date = LocalDate.of(2026, 6, 7)))
                    ),
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Jun 7")
            .assertIsDisplayed()
    }

    @Test
    fun successState_showsTrendArrow_whenPresent() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Success(
                        listOf(testSummary(trend = BiomarkerTrend.UP))
                    ),
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("↑")
            .assertIsDisplayed()
    }

    @Test
    fun successState_doesNotShowTrendArrow_whenNull() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Success(
                        listOf(testSummary(trend = null))
                    ),
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithText("↑").assertDoesNotExist()
        composeTestRule.onNodeWithText("↓").assertDoesNotExist()
        composeTestRule.onNodeWithText("→").assertDoesNotExist()
    }

    @Test
    fun successState_showsMultipleCards() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Success(
                        listOf(
                            testSummary(biomarkerId = 1, name = "Glucose (Fasting)"),
                            testSummary(biomarkerId = 2, name = "HbA1c"),
                            testSummary(biomarkerId = 3, name = "LDL Cholesterol"),
                        )
                    ),
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithText("Glucose (Fasting)").assertIsDisplayed()
        composeTestRule.onNodeWithText("HbA1c").assertIsDisplayed()
        composeTestRule.onNodeWithText("LDL Cholesterol").assertIsDisplayed()
    }

    @Test
    fun successState_doesNotShowEmptyMessage() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                DashboardScreen(
                    uiState = DashboardUiState.Success(listOf(testSummary())),
                    onNavigateToBiomarkers = onNavigateToBiomarkers,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Pin your first biomarker to get started")
            .assertDoesNotExist()
    }

    // --- Helpers ---

    private fun testSummary(
        biomarkerId: Long = 1L,
        name: String = "Glucose (Fasting)",
        latestValue: Double = 92.0,
        unit: String = "mg/dL",
        date: LocalDate = LocalDate.of(2026, 6, 7),
        status: BiomarkerStatus = BiomarkerStatus.IN_RANGE,
        trend: BiomarkerTrend? = BiomarkerTrend.DOWN,
    ) = PinnedBiomarkerSummary(
        biomarkerId = biomarkerId,
        name = name,
        latestValue = latestValue,
        unit = unit,
        date = date,
        status = status,
        trend = trend,
    )
}