# Current Feature

Phase 3.1 — Dashboard & Biomarker Status Cards

## Status

In-Progress

## Goals

The home screen. Shows a grid of pinned biomarkers with their latest value, status, and trend at a glance.

### Deliverables

**Screen** (`ui/dashboard/DashboardScreen.kt`)
- Replaces the Phase 1 placeholder
- `LazyVerticalGrid` (2 columns) of `BiomarkerSummaryCard` components
- Only pinned biomarkers appear — empty state if none pinned
- Empty state: "Pin your first biomarker to get started" + button to Biomarkers tab
- FAB navigates to Add Lab Result screen

**`BiomarkerSummaryCard`** (`ui/dashboard/components/BiomarkerSummaryCard.kt`)
- Biomarker name, latest value + unit, date of last result
- Status dot: In Range / Borderline / Out of Range / Neutral
- Trend arrow: ↑ ↓ → vs previous result (hidden if only one result)
- Tapping navigates to Biomarker Detail (Phase 3.2)

**Status color tokens** (defined in `ui/theme/`)
- `StatusInRange`, `StatusBorderline`, `StatusOutOfRange`, `StatusNeutral`

**`DashboardViewModel`** (`ui/dashboard/DashboardViewModel.kt`)
- UiState sealed class: `Loading`, `Empty`, `Success(List<BiomarkerSummaryUiModel>)`
- `BiomarkerSummaryUiModel`: name, latestValue, unit, date, status, trend

## Notes

- Status and trend logic belongs in a domain-layer mapper, not in the ViewModel or composable
- Cards should be consistent height regardless of content

## References

- @context/specs/phase3-1-dashboard-spec.md
- @context/coding-standards.md

## History

- Initial Android Studio project created
- Core dependencies added (Hilt, Room, Navigation, Vico, Serialization)
- Hilt wired up (`LabRatApplication`, `MainActivity`)
- Project structure scaffolded to match clean architecture layout
- LabRat Material 3 theme applied (Color, Type, Shape, Theme)
- Bottom navigation shell — 4 tabs (Dashboard, Biomarkers, History, Settings) with placeholder screens
- Domain models defined (`Biomarker`, `BiomarkerCategory`, `LabResult`, `BiomarkerEntry`)
- Repository interfaces defined (`BiomarkerRepository`, `LabResultRepository`, `BiomarkerEntryRepository`)
- Room database layer implemented — entities, TypeConverter, DAOs, AppDatabase, mappers, repository impls, Hilt module
- 109 biomarkers seeded across all 13 categories, verified via Database Inspector
- Add Lab Result screen implemented — date picker, lab name, notes, entry list with remove, error display
- SaveLabResultUseCase with atomic @Transaction DAO insert for LabResult + BiomarkerEntry rows
- FAB on Dashboard navigates to Add Lab Result; NavRoutes sealed class added for non-tab destinations
- BiomarkerEntryDraft and AddLabResultUiState extracted to AddLabResultModels.kt for Phase 2.2 reuse
- LabResultDaoTest added
- BiomarkerPickerScreen implemented — searchable, grouped by category with sticky headers, checkmark for already-added biomarkers
- ValueInputDialog implemented — ref range helper text, positive-number validation, Add/Cancel
- ViewModel hoisted out of composables; navigation driven via SharedFlow (entryAdded, navigateUp)
- Search query resets on addEntry so picker opens clean each time
- Routes/NavRoutes renamed to TabRoutes/FullScreenRoutes
- Typography migrated from Google Fonts provider to bundled Inter font files
- AddLabResultViewModelTest (unit) and ValueInputDialogTest (instrumented) added
- mockk, mockk-android, kotlinx-coroutines-test added to version catalog