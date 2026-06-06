# Phase 3.2 — Biomarker Detail Screen

## Overview

Tapping a biomarker card opens a detail screen with its metadata, a placeholder for the trend chart (Phase 3.3), and a scrollable history of all logged values.

## Requirements

### Screen (`ui/biomarkerdetail/BiomarkerDetailScreen.kt`)

- Accepts `biomarkerId: Long` as a nav argument
- Top section:
  - Biomarker name (large)
  - Category badge
  - Unit
  - Reference range text (e.g. "Ref: 70–99 mg/dL") — or "No reference range set"
  - Pin/unpin toggle in the top bar
- Chart placeholder — a `Box` with a "Chart coming soon" label for now (replaced in Phase 3.3)
- Date range filter segmented control: **3M / 6M / 1Y / All** — wired up and filters the history list below even before the chart exists
- Result history list:
  - Each row: date, value + unit, status dot, delta vs previous (e.g. "+4.2" or "−1.1")
  - Most recent first
  - Empty state: "No results logged yet"

### `BiomarkerDetailViewModel` (`ui/biomarkerdetail/BiomarkerDetailViewModel.kt`)

- Loads biomarker metadata and all entries sorted by date desc
- `UiState`: `Loading`, `Success(biomarker, filteredEntries, selectedRange)`
- `setDateRange(range)` — updates which entries are shown
- `togglePin()` — updates `isPinned` in the database

## Notes

- Delta: `currentValue - previousValue`, formatted with sign (e.g. "+2.1", "−0.8")
- Keep chart area as a clearly marked placeholder — Phase 3.3 drops the real chart in

## References

- @context/coding-standards.md
- @context/specs/phase3-1-dashboard-spec.md
