# qdran's Mace Cooldown

A Paper plugin for Minecraft 26.3 that puts the mace on an item cooldown after every hit, just like an ender pearl after a throw.

## Features

- Each time a player hits something with a mace in their main hand, the mace goes on cooldown. It shows the white cooldown overlay in the hotbar.
- Mace hits during the cooldown are cancelled and do no damage.
- `/qmcd <seconds>` changes the cooldown in-game. The value is saved to `config.yml`, so it survives restarts.

## Commands

| Command | Description |
| --- | --- |
| `/qmcd` | Show the current cooldown |
| `/qmcd <seconds>` | Set the cooldown. Decimals work (e.g. `2.5`). `0` disables it. Maximum `3600`. |

## Permissions

| Permission | Description | Default |
| --- | --- | --- |
| `qmcd.admin` | Use `/qmcd` | op |

## Configuration

`plugins/qdrans-mace-cooldown/config.yml`:

```yaml
# Cooldown after every mace hit, in seconds. 0 disables it. Change in-game with /qmcd <seconds>.
cooldown-seconds: 5.0
```

## Installation

1. Build the jar (see below), or take it from a release.
2. Copy `qdrans-mace-cooldown.jar` into your server's `plugins/` folder.
3. Restart the server.

## Building

Requires Java 25 and Maven.

```bash
mvn package
```

Output: `target/qdrans-mace-cooldown.jar`.

The Paper API version is set by the `paper.version` property in `pom.xml` (currently `26.3.build.211-stable`).

## Notes

- Cooldowns are not saved when a player logs out. A player who relogs has no cooldown.
- Only the main hand counts. A mace held in the offhand doesn't trigger or check the cooldown.
- The plugin name is `qdrans-mace-cooldown` because Bukkit doesn't allow apostrophes in plugin names.
