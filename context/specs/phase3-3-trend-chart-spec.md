# Phase 3.3 — Trend Chart

## Overview

Replace the chart placeholder from Phase 3.2 with a real Vico trend line chart showing biomarker values over time with a reference range overlay.

## Requirements

### Chart Component (`ui/biomarkerdetail/components/BiomarkerTrendChart.kt`)

- Use **Vico** (`com.patrykandpatrick.vico:compose`)
- Line chart — one data point per logged result
- X-axis: result dates
- Y-axis: biomarker value in its unit
- Data points colored by status (green / amber / red) — reuse status logic from Phase 3.1
- Reference range overlay: horizontal band between `refRangeLow` and `refRangeHigh` in a subtle tinted color — omit if no range defined
- Responds to the date range filter already wired in Phase 3.2

### Edge Cases

- Single data point: render the point, no line drawn
- All values identical: chart renders without crashing (flat line)
- No reference range: chart renders without the band overlay
- Empty data (filtered to zero points): show "No data for this range" message instead of chart

## Notes

- Spike the reference range band overlay first — it's the riskiest unknown in this feature
- The chart component should be pure UI — receive data and range as parameters, no ViewModel access

## References

- @context/coding-standards.md
- @context/specs/phase3-2-biomarker-detail-screen-spec.md
