# Precision Anvil

Precision Anvil is a NeoForge mod for Minecraft 1.21.1 that improves how anvil costs are handled and displayed.

## Description

The mod replaces the vanilla anvil menu with a dedicated menu that:
- keeps compatibility with standard anvil behavior,
- separates the **required levels** to validate the craft from the **levels actually consumed**,
- displays both values directly in the UI.

This makes anvil progression clearer and allows server owners to tune XP economy precisely.

## Features

- Right-clicking any anvil opens the Precision Anvil menu.
- Compact cost display in the anvil UI: `used / required` levels.
- Pickup is blocked when the player does not meet required levels.
- Actual consumed levels are applied separately on take.
- Server-side configurable formulas for:
  - required level cap,
  - required level multiplier from vanilla,
  - used level multiplier from required.

## Configuration

Common/server config keys:
- `anvil_costs.required_level_cap`
- `anvil_costs.required_level_multiplier_from_vanilla`
- `anvil_costs.used_level_multiplier_from_required`

## Compatibility

- Minecraft: `1.21.1`
- NeoForge: `21.1.218`
- License: `MIT`

## Latest release

Current latest release: **v0.1.4**.

See [CHANGELOG.md](CHANGELOG.md) for full details.

## Development

```bash
./gradlew runClient
```

On Windows:

```powershell
.\gradlew.bat runClient
```
