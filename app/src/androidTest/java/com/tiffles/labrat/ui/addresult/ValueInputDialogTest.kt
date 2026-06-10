package com.tiffles.labrat.ui.addresult

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.model.BiomarkerCategory
import com.tiffles.labrat.ui.theme.LabRatTheme
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValueInputDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val onConfirm: (Double) -> Unit = mockk(relaxed = true)
    private val onDismiss: () -> Unit = mockk(relaxed = true)

    // --- Content display ---

    @Test
    fun dialog_showsBiomarkerName() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Glucose (Fasting)")
            .assertExists()
    }

    @Test
    fun dialog_showsReferenceRange_whenBothBoundsPresent() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(refRangeLow = 70.0, refRangeHigh = 99.0),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Reference range: 70.0–99.0 mg/dL")
            .assertExists()
    }

    @Test
    fun dialog_showsLowerBoundOnly_whenOnlyRefRangeLowPresent() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(refRangeLow = 70.0, refRangeHigh = null),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Reference range: ≥70.0 mg/dL")
            .assertExists()
    }

    @Test
    fun dialog_showsUpperBoundOnly_whenOnlyRefRangeHighPresent() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(refRangeLow = null, refRangeHigh = 99.0),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Reference range: ≤99.0 mg/dL")
            .assertExists()
    }

    @Test
    fun dialog_doesNotShowReferenceRange_whenNoBoundsPresent() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(refRangeLow = null, refRangeHigh = null),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Reference range:", substring = true)
            .assertDoesNotExist()
    }

    // --- Add button state ---

    @Test
    fun addButton_isDisabled_whenInputIsEmpty() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Add")
            .assertIsNotEnabled()
    }

    @Test
    fun addButton_isDisabled_whenInputIsZero() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithText("Value (mg/dL)")
            .performTextInput("0")

        // Assert
        composeTestRule
            .onNodeWithText("Add")
            .assertIsNotEnabled()
    }

    @Test
    fun addButton_isDisabled_whenInputIsNegative() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithText("Value (mg/dL)")
            .performTextInput("-5")

        // Assert
        composeTestRule
            .onNodeWithText("Add")
            .assertIsNotEnabled()
    }

    @Test
    fun addButton_isDisabled_whenInputIsNotNumeric() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithText("Value (mg/dL)")
            .performTextInput("abc")

        // Assert
        composeTestRule
            .onNodeWithText("Add")
            .assertIsNotEnabled()
    }

    @Test
    fun addButton_isEnabled_whenInputIsValidPositiveNumber() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithText("Value (mg/dL)")
            .performTextInput("92.0")

        // Assert
        composeTestRule
            .onNodeWithText("Add")
            .assertIsEnabled()
    }

    // --- Error message ---

    @Test
    fun errorMessage_isShown_whenInputIsInvalid() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithText("Value (mg/dL)")
            .performTextInput("abc")

        // Assert
        composeTestRule
            .onNodeWithText("Enter a positive number")
            .assertExists()
    }

    @Test
    fun errorMessage_isNotShown_whenInputIsEmpty() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Assert
        composeTestRule
            .onNodeWithText("Enter a positive number")
            .assertDoesNotExist()
    }

    // --- Callbacks ---

    @Test
    fun onConfirm_isCalledWithParsedValue_whenAddButtonClicked() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithText("Value (mg/dL)")
            .performTextInput("92.0")
        composeTestRule
            .onNodeWithText("Add")
            .performClick()

        // Assert
        verify { onConfirm(92.0) }
    }

    @Test
    fun onDismiss_isCalled_whenCancelButtonClicked() {
        // Arrange
        composeTestRule.setContent {
            LabRatTheme {
                ValueInputDialog(
                    biomarker = testBiomarker(),
                    onConfirm = onConfirm,
                    onDismiss = onDismiss,
                )
            }
        }

        // Act
        composeTestRule
            .onNodeWithText("Cancel")
            .performClick()

        // Assert
        verify { onDismiss() }
    }

    // --- Helpers ---

    private fun testBiomarker(
        refRangeLow: Double? = 70.0,
        refRangeHigh: Double? = 99.0,
    ) = Biomarker(
        id = 1,
        name = "Glucose (Fasting)",
        unit = "mg/dL",
        category = BiomarkerCategory.METABOLIC,
        refRangeLow = refRangeLow,
        refRangeHigh = refRangeHigh,
        isPinned = false,
        isArchived = false,
    )
}