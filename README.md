# Tetris

A small Android Tetris game written in Kotlin with Jetpack Compose. The rules live in a pure Kotlin engine (`com.tetris.game.engine.TetrisGame`) and the UI only draws a snapshot of that state.

## Play

The board is the classic 10×20 well with all seven tetrominoes. Pieces rotate with Super Rotation System wall kicks. On-screen buttons cover left, right, clockwise and counter-clockwise rotation, soft drop, hard drop, and a new game. Hold Left, Right, or Soft to repeat. Gravity gets a little faster each level (every 10 lines).

Scoring follows the usual scale, multiplied by the current level: 100 / 300 / 500 / 800 for 1–4 lines, plus 1 point per soft-drop row and 2 points per hard-drop row.

## Requirements

- JDK 17 or newer
- Android SDK platform 37.2 and build-tools 36.0.0

Point `ANDROID_HOME` at the SDK (or put `sdk.dir` in a gitignored `local.properties`).

## Build

```bash
./gradlew assembleDebug
```

The debug APK is `app/build/outputs/apk/debug/app-debug.apk`.

## Install

With a device or emulator connected:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Tests

```bash
./gradlew test
```

Unit tests cover collision, rotation and wall kicks, line clears, scoring, level speed, game over, and restart. They do not need a device.

`./gradlew test assembleDebug` is what CI runs on pull requests.
