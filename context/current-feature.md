# Current Feature

Phase 1.1 — Theming (Colors, Typography, Shapes)

## Status

In Progress

## Goals

Define the LabRat Material 3 theme — colors, typography, and shapes.
Light mode only for now.

### Color Direction

Inspired by the first reference screenshot. Clean, airy, wellness-focused:

- **Primary**: soft purple (~#7C5CBF or similar)
- **Background**: near-white, very slightly warm (~#FAFAFA)
- **Surface**: pure white (#FFFFFF) for cards
- **Status colors** (defined as custom tokens, not M3 roles):
    - In Range: soft green (~#4CAF82)
    - Borderline: amber (~#F5A623)
    - Out of Range: coral red (~#E05252)
- Keep the palette minimal — purple + greens/ambers/reds for status only

Use **Material Theme Builder** to generate the full M3 color scheme from the
primary purple, then adjust as needed.

### Typography

- Use **Inter** (Google Fonts) — clean, modern, highly legible for numbers
- Large bold numerals for biomarker values (the big "88 mg/dL" style from the references)
- Standard M3 type scale otherwise

### Shapes

- Rounded cards — `CornerSize(16.dp)` for cards, `CornerSize(12.dp)` for chips/badges

### Deliverables

- `ui/theme/Color.kt` — all color tokens including custom status colors
- `ui/theme/Type.kt` — Inter font, adjusted type scale
- `ui/theme/Shape.kt` — rounded corner definitions
- `ui/theme/Theme.kt` — wired together as `LabRatTheme`

## Notes

- `dynamicColor = false` — always use our palette, ignore system dynamic color
- Dark mode first, light mode later
- Do not hardcode any colors outside of `Color.kt`

## References

- @context/coding-standards.md
- @context/screenshots/reference-ui1.jpg
- @context/screenshots/reference-ui2.jpg

## History

- Initial Android Studio project created
