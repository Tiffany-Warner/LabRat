# Phase 3.1 — Dashboard & Biomarker Status Cards

## Overview

The home screen. Shows a grid of pinned biomarkers with their latest value, status, and trend at a glance.

## Requirements

### Screen (`ui/dashboard/DashboardScreen.kt`)

- Replaces the placeholder from Phase 1.1
- `LazyVerticalGrid` (2 columns) of `BiomarkerSummaryCard` components
- Only pinned biomarkers appear — if none pinned, show empty state
- Empty state: message "Pin your first biomarker to get started" + button to Biomarkers tab
- FAB (bottom right) — "Add Lab Result" → navigates to Add Lab Result screen

### `BiomarkerSummaryCard` (`ui/dashboard/components/BiomarkerSummaryCard.kt`)

Each card shows:
- Biomarker name
- Latest value + unit
- Date of last result (e.g. "May 12")
- Status dot: 🟢 In Range / 🟡 Borderline / 🔴 Out of Range
  - In Range: value within `refRangeLow..refRangeHigh`
  - Borderline: within 10% outside the range
  - Out of Range: more than 10% outside the range
  - Neutral (grey): no reference range defined
- Trend arrow: ↑ ↓ → vs previous result — hidden if only one result exists
- Tapping navigates to Biomarker Detail (Phase 3.2)

### Status Color Tokens

Define in `ui/theme/` — do not hardcode colors inline:
- `StatusInRange` — green
- `StatusBorderline` — amber
- `StatusOutOfRange` — red
- `StatusNeutral` — muted grey

### `DashboardViewModel` (`ui/dashboard/DashboardViewModel.kt`)

- Loads pinned biomarkers with their two most recent entries
- `UiState` sealed class: `Loading`, `Empty`, `Success(List<BiomarkerSummaryUiModel>)`
- `BiomarkerSummaryUiModel`: name, latestValue, unit, date, status, trend

## Notes

- Status and trend logic belongs in a domain-layer mapper, not in the ViewModel or composable
- Cards should be consistent height regardless of content

## References

- @context/coding-standards.md
- @context/specs/phase2-2-biomarker-picker-spec.md
