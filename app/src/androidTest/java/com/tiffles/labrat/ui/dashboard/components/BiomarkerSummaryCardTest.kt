package com.tiffles.labrat.ui.dashboard.components

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
class BiomarkerSummaryCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val onClick: () -> Unit = mockk(relaxed = true)

    // --- Content display ---

    @Test
    fun card_showsBiomarkerName() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                BiomarkerSummaryCard(
                    summary = testSummary(name = "Glucose (Fasting)"),
                    onClick = onClick,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Glucose (Fasting)")
            .assertIsDisplayed()
    }

    @Test
    fun card_showsValueAndUnit() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                BiomarkerSummaryCard(
                    summary = testSummary(latestValue = 92.0, unit = "mg/dL"),
                    onClick = onClick,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("92.0 mg/dL")
            .assertIsDisplayed()
    }

    @Test
    fun card_showsFormattedDate() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                BiomarkerSummaryCard(
                    summary = testSummary(date = LocalDate.of(2026, 6, 7)),
                    onClick = onClick,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Jun 7")
            .assertIsDisplayed()
    }

    // --- Trend arrow ---

    @Test
    fun card_showsUpArrow_whenTrendIsUp() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                BiomarkerSummaryCard(
                    summary = testSummary(trend = BiomarkerTrend.UP),
                    onClick = onClick,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("↑")
            .assertIsDisplayed()
    }

    @Test
    fun card_showsDownArrow_whenTrendIsDown() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                BiomarkerSummaryCard(
                    summary = testSummary(trend = BiomarkerTrend.DOWN),
                    onClick = onClick,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("↓")
            .assertIsDisplayed()
    }

    @Test
    fun card_showsStableArrow_whenTrendIsStable() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                BiomarkerSummaryCard(
                    summary = testSummary(trend = BiomarkerTrend.STABLE),
                    onClick = onClick,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("→")
            .assertIsDisplayed()
    }

    @Test
    fun card_doesNotShowTrendArrow_whenTrendIsNull() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                BiomarkerSummaryCard(
                    summary = testSummary(trend = null),
                    onClick = onClick,
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithText("↑").assertDoesNotExist()
        composeTestRule.onNodeWithText("↓").assertDoesNotExist()
        composeTestRule.onNodeWithText("→").assertDoesNotExist()
    }

    // --- Click ---

    @Test
    fun card_callsOnClick_whenTapped() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                BiomarkerSummaryCard(
                    summary = testSummary(),
                    onClick = onClick,
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithText("Glucose (Fasting)")
            .performClick()

        // Assert
        verify { onClick() }
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