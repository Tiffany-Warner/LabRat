# Current Feature

Phase 3.2 — Biomarker Detail Screen

## Status

In Progress

## Goals

Tapping a biomarker card opens a detail screen with its metadata, a placeholder for the trend chart (Phase 3.3), and a scrollable history of all logged values.

### Deliverables

**Screen** (`ui/biomarkerdetail/BiomarkerDetailScreen.kt`)
- Accepts `biomarkerId: Long` as a nav argument
- Top section: biomarker name (large), category badge, unit, reference range text (e.g. "Ref: 70–99 mg/dL") or "No reference range set", pin/unpin toggle in top bar
- Chart placeholder — a `Box` with a "Chart coming soon" label (replaced in Phase 3.3)
- Date range filter segmented control: **3M / 6M / 1Y / All** — filters the history list even before chart exists
- Result history list:
  - Each row: date, value + unit, status dot, delta vs previous (e.g. "+4.2" or "−1.1")
  - Most recent first
  - Empty state: "No results logged yet"

**ViewModel** (`ui/biomarkerdetail/BiomarkerDetailViewModel.kt`)
- Loads biomarker metadata and all entries sorted by date desc
- `UiState`: `Loading`, `Success(biomarker, filteredEntries, selectedRange)`
- `setDateRange(range)` — updates which entries are shown
- `togglePin()` — updates `isPinned` in the database

## Notes

- Delta: `currentValue - previousValue`, formatted with sign (e.g. "+2.1", "−0.8")
- Keep chart area as a clearly marked placeholder — Phase 3.3 drops the real chart in
- Navigation: tapping a `BiomarkerSummaryCard` on the Dashboard navigates here with `biomarkerId`

## References

- @context/specs/phase3-2-biomarker-detail-screen-spec.md
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
- Dashboard screen implemented — BiomarkerSummaryCard grid, status dots, trend arrows, loading/empty/success states
- GetPinnedBiomarkerSummariesUseCase — status (10% borderline) and trend logic in domain layer
- BiomarkerStatus, BiomarkerTrend, PinnedBiomarkerSummary, BiomarkerEntryRecord domain models added
- Room join query for date-ordered entry history (BiomarkerEntryWithDate projection)
- BiomarkersScreen implemented — LazyColumn with sticky category headers, pin/unpin icon button per row
- BiomarkersViewModel observes getAll() Flow, filters archived client-side, togglePin() persists via repository.update()
- CategoryHeader extracted from BiomarkerPickerScreen to shared ui/components/ and reused in both screens
- All unit and instrumented tests passing (52 instrumented, all unit tests green)
- DAO query fixed: ORDER BY dateEpochDay DESC, id DESC to correctly order same-date entries by insertion