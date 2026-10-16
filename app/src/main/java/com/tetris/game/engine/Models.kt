package com.tetris.game.engine

/** One cell on the board. x grows right, y grows down. */
data class Cell(val x: Int, val y: Int)

enum class PieceType {
    I,
    O,
    T,
    S,
    Z,
    J,
    L,
}

data class Piece(
    val type: PieceType,
    val rotation: Int,
    val x: Int,
    val y: Int,
)

/**
 * Immutable view of a game, safe to hand to the UI.
 * [locked] is indexed as locked[y][x].
 */
data class GameSnapshot(
    val width: Int,
    val height: Int,
    val locked: List<List<PieceType?>>,
    val activeType: PieceType?,
    val activeRotation: Int,
    val activeX: Int,
    val activeY: Int,
    val activeCells: List<Cell>,
    val ghostCells: List<Cell>,
    val next: PieceType,
    val nextCells: List<Cell>,
    val score: Int,
    val lines: Int,
    val level: Int,
    val isGameOver: Boolean,
    val dropIntervalMillis: Long,
)

/** Supplies the next tetromino. Tests inject a fixed sequence. */
fun interface PieceSource {
    fun next(): PieceType
}

/** Guideline 7-bag randomizer. */
class BagPieceSource(private val random: kotlin.random.Random) : PieceSource {
    private val bag = ArrayDeque<PieceType>()

    override fun next(): PieceType {
        if (bag.isEmpty()) {
            bag.addAll(PieceType.entries.shuffled(random))
        }
        return bag.removeFirst()
    }
}

/** Deterministic source. Repeats [PieceType.O] once the list is exhausted. */
class SequencePieceSource(pieces: List<PieceType>) : PieceSource {
    private val queue = ArrayDeque(pieces)

    override fun next(): PieceType = if (queue.isEmpty()) PieceType.O else queue.removeFirst()
}
