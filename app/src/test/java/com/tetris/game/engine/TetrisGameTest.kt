package com.tetris.game.engine

import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TetrisGameTest {
    @Test
    fun eachRotationHasFourCells() {
        for (type in PieceType.entries) {
            for (rotation in 0 until 4) {
                assertEquals(4, Shapes.cellsOf(type, rotation, 0, 0).size)
            }
        }
    }

    @Test
    fun sevenBagYieldsEachPieceOncePerBag() {
        val source = BagPieceSource(Random(7))
        val first = List(7) { source.next() }
        val second = List(7) { source.next() }
        assertEquals(PieceType.entries.toSet(), first.toSet())
        assertEquals(7, first.distinct().size)
        assertEquals(PieceType.entries.toSet(), second.toSet())
    }

    @Test
    fun pieceCannotMoveThroughLeftWallOrLockedCell() {
        val game = gameOf(PieceType.O)
        game.placeActiveForTest(PieceType.O, 0, 0, 5)
        repeat(6) { game.moveLeft() }
        assertEquals(0, game.snapshot().activeCells.minOf { it.x })
        assertFalse(game.moveLeft())

        game.placeActiveForTest(PieceType.O, 0, 3, 5)
        // O at x=3 occupies columns 4 and 5. One step left occupies column 3.
        game.loadLockedForTest(listOf(3 to 5))
        assertFalse(game.fits(PieceType.O, 0, 2, 5))
        assertFalse(game.moveLeft())
        assertEquals(3, game.snapshot().activeX)
    }

    @Test
    fun pieceCannotOccupyFloorOrAnotherBlock() {
        val game = gameOf(PieceType.O)
        assertTrue(game.fits(PieceType.O, 0, 3, 18))
        assertFalse(game.fits(PieceType.O, 0, 3, 19))
        assertFalse(game.fits(PieceType.O, 0, 8, 0))
        game.loadLockedForTest(listOf(5 to 10))
        assertFalse(game.fits(PieceType.O, 0, 4, 10))
        assertTrue(game.fits(PieceType.O, 0, 0, 10))
    }

    @Test
    fun clockwiseRotationChangesCellsAndCounterClockwiseRestoresThem() {
        val game = gameOf(PieceType.T)
        game.placeActiveForTest(PieceType.T, 0, 3, 5)
        val before = game.snapshot().activeCells.toSet()
        assertTrue(game.rotateClockwise())
        val rotated = game.snapshot()
        assertEquals(1, rotated.activeRotation)
        assertEquals(
            Shapes.cellsOf(PieceType.T, 1, rotated.activeX, rotated.activeY).toSet(),
            rotated.activeCells.toSet(),
        )
        assertNotEquals(before, rotated.activeCells.toSet())
        assertTrue(game.rotateCounterClockwise())
        assertEquals(before, game.snapshot().activeCells.toSet())
    }

    @Test
    fun rotationUsesWallKickWhenBasicRotationIsOutOfBounds() {
        val game = gameOf(PieceType.I)
        // Vertical I flush with the left wall. The un-kicked rotation sticks out.
        game.placeActiveForTest(PieceType.I, 1, -2, 4)
        assertEquals(setOf(Cell(0, 4), Cell(0, 5), Cell(0, 6), Cell(0, 7)), game.snapshot().activeCells.toSet())
        assertTrue(game.rotateClockwise())
        val snap = game.snapshot()
        assertEquals(2, snap.activeRotation)
        assertEquals(0, snap.activeX)
        assertTrue(snap.activeCells.all { it.x in 0 until 10 && it.y in 0 until 20 })
    }

    @Test
    fun rotationFailsWhenEveryKickCollides() {
        val game = gameOf(PieceType.T)
        game.placeActiveForTest(PieceType.T, 0, 3, 8)
        val blocked = (0 until 10).flatMap { x -> (0 until 20).map { y -> x to y } }
        game.loadLockedForTest(blocked)
        assertFalse(game.rotateClockwise())
        assertEquals(0, game.snapshot().activeRotation)
        assertEquals(3, game.snapshot().activeX)
        assertEquals(8, game.snapshot().activeY)
    }

    @Test
    fun singleAndTetrisClearsScoreAndDropRowsAbove() {
        val game = gameOf(PieceType.I)
        game.loadLockedForTest((0 until 10).map { it to 19 })
        game.loadLockedForTest(listOf(4 to 18))
        assertEquals(1, game.clearCompletedLines())
        val afterSingle = game.snapshot()
        assertEquals(100, afterSingle.score)
        assertEquals(1, afterSingle.lines)
        assertEquals(1, afterSingle.level)
        assertEquals(PieceType.I, afterSingle.locked[19][4])
        assertEquals(null, afterSingle.locked[18][4])

        val tetris = gameOf(PieceType.I)
        for (y in 16..19) {
            tetris.loadLockedForTest((0 until 10).filter { it != 2 }.map { it to y })
        }
        tetris.placeActiveForTest(PieceType.I, 1, 0, 16)
        tetris.softDrop()
        val snap = tetris.snapshot()
        assertEquals(800, snap.score)
        assertEquals(4, snap.lines)
        assertTrue(snap.locked.all { row -> row.all { it == null } })
    }

    @Test
    fun levelRisesEveryTenLinesAndGravitySpeedsUp() {
        val game = gameOf(PieceType.O)
        repeat(9) {
            game.loadLockedForTest((0 until 10).map { it to 19 })
            assertEquals(1, game.clearCompletedLines())
        }
        assertEquals(1, game.snapshot().level)
        assertEquals(900, game.snapshot().score)
        game.loadLockedForTest((0 until 10).map { it to 19 })
        game.clearCompletedLines()
        assertEquals(2, game.snapshot().level)
        assertEquals(1000, game.snapshot().score)
        assertTrue(TetrisGame.dropIntervalMillis(2) < TetrisGame.dropIntervalMillis(1))
        assertEquals(800L, TetrisGame.dropIntervalMillis(1))
        assertEquals(80L, TetrisGame.dropIntervalMillis(50))
    }

    @Test
    fun softDropAndHardDropScoreAndLock() {
        val game = gameOf(PieceType.O, PieceType.T)
        assertTrue(game.softDrop())
        assertEquals(1, game.snapshot().score)
        assertEquals(1, game.snapshot().activeY)

        val dropped = gameOf(PieceType.O, PieceType.T)
        dropped.hardDrop()
        val snap = dropped.snapshot()
        assertEquals(36, snap.score)
        assertEquals(PieceType.O, snap.locked[18][4])
        assertEquals(PieceType.O, snap.locked[19][5])
        assertEquals(PieceType.T, snap.activeType)
        assertEquals(0, snap.activeY)
    }

    @Test
    fun spawnIntoFilledCellsEndsTheGameUntilRestart() {
        val game = gameOf(PieceType.T, PieceType.T)
        val spawn = game.snapshot().activeCells
        game.loadLockedForTest(spawn.map { it.x to it.y })
        game.placeActiveForTest(PieceType.O, 0, 3, 18)
        game.hardDrop()
        assertTrue(game.snapshot().isGameOver)
        assertFalse(game.moveLeft())
        assertFalse(game.rotateClockwise())

        game.restart()
        val restarted = game.snapshot()
        assertFalse(restarted.isGameOver)
        assertEquals(0, restarted.score)
        assertEquals(0, restarted.lines)
        assertEquals(1, restarted.level)
        assertTrue(restarted.locked.all { row -> row.all { it == null } })
    }

    private fun gameOf(vararg types: PieceType): TetrisGame {
        val sequence = if (types.isEmpty()) List(40) { PieceType.O } else types.toList() + List(40) { types.last() }
        return TetrisGame(SequencePieceSource(sequence))
    }
}
