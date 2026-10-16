package com.tetris.game.engine

/**
 * Super Rotation System shapes and wall kicks.
 *
 * Shapes are stored in a 4x4 box with y growing down, matching the board.
 * Kick offsets are the official SRS tables (y grows up) and are flipped
 * when they are applied.
 */
internal object Shapes {
    val cells: Map<PieceType, List<List<Cell>>> = mapOf(
        PieceType.I to rotations(
            "....",
            "####",
            "....",
            "....",
            "..#.",
            "..#.",
            "..#.",
            "..#.",
            "....",
            "....",
            "####",
            "....",
            ".#..",
            ".#..",
            ".#..",
            ".#..",
        ),
        PieceType.O to rotations(
            "....",
            ".##.",
            ".##.",
            "....",
            "....",
            ".##.",
            ".##.",
            "....",
            "....",
            ".##.",
            ".##.",
            "....",
            "....",
            ".##.",
            ".##.",
            "....",
        ),
        PieceType.T to rotations(
            ".#..",
            "###.",
            "....",
            "....",
            ".#..",
            ".##.",
            ".#..",
            "....",
            "....",
            "###.",
            ".#..",
            "....",
            ".#..",
            "##..",
            ".#..",
            "....",
        ),
        PieceType.S to rotations(
            ".##.",
            "##..",
            "....",
            "....",
            ".#..",
            ".##.",
            "..#.",
            "....",
            "....",
            ".##.",
            "##..",
            "....",
            "#...",
            "##..",
            ".#..",
            "....",
        ),
        PieceType.Z to rotations(
            "##..",
            ".##.",
            "....",
            "....",
            "..#.",
            ".##.",
            ".#..",
            "....",
            "....",
            "##..",
            ".##.",
            "....",
            ".#..",
            "##..",
            "#...",
            "....",
        ),
        PieceType.J to rotations(
            "#...",
            "###.",
            "....",
            "....",
            ".##.",
            ".#..",
            ".#..",
            "....",
            "....",
            "###.",
            "..#.",
            "....",
            ".#..",
            ".#..",
            "##..",
            "....",
        ),
        PieceType.L to rotations(
            "..#.",
            "###.",
            "....",
            "....",
            ".#..",
            ".#..",
            ".##.",
            "....",
            "....",
            "###.",
            "#...",
            "....",
            "##..",
            ".#..",
            ".#..",
            "....",
        ),
    )

    fun cellsOf(type: PieceType, rotation: Int, originX: Int, originY: Int): List<Cell> {
        return cells.getValue(type)[rotation.mod(4)].map { cell ->
            Cell(originX + cell.x, originY + cell.y)
        }
    }

    /** Wall-kick offsets in board coordinates (y down) for a rotation transition. */
    fun kicks(type: PieceType, from: Int, to: Int): List<Pair<Int, Int>> {
        val raw = when (type) {
            PieceType.O -> listOf(0 to 0)
            PieceType.I -> I_KICKS.getValue(from to to)
            else -> JLSTZ_KICKS.getValue(from to to)
        }
        return raw.map { (x, yUp) -> x to -yUp }
    }

    private fun rotations(vararg rows: String): List<List<Cell>> {
        require(rows.size == 16) { "Expected 4 rotations of 4 rows, got ${rows.size}" }
        return rows.toList().chunked(4).map { block ->
            buildList {
                block.forEachIndexed { y, row ->
                    row.forEachIndexed { x, char ->
                        if (char == '#') add(Cell(x, y))
                    }
                }
            }
        }
    }

    // Official SRS kick data. y is positive upward.
    private val JLSTZ_KICKS: Map<Pair<Int, Int>, List<Pair<Int, Int>>> = mapOf(
        (0 to 1) to listOf(0 to 0, -1 to 0, -1 to 1, 0 to -2, -1 to -2),
        (1 to 0) to listOf(0 to 0, 1 to 0, 1 to -1, 0 to 2, 1 to 2),
        (1 to 2) to listOf(0 to 0, 1 to 0, 1 to -1, 0 to 2, 1 to 2),
        (2 to 1) to listOf(0 to 0, -1 to 0, -1 to 1, 0 to -2, -1 to -2),
        (2 to 3) to listOf(0 to 0, 1 to 0, 1 to 1, 0 to -2, 1 to -2),
        (3 to 2) to listOf(0 to 0, -1 to 0, -1 to -1, 0 to 2, -1 to 2),
        (3 to 0) to listOf(0 to 0, -1 to 0, -1 to -1, 0 to 2, -1 to 2),
        (0 to 3) to listOf(0 to 0, 1 to 0, 1 to 1, 0 to -2, 1 to -2),
    )

    private val I_KICKS: Map<Pair<Int, Int>, List<Pair<Int, Int>>> = mapOf(
        (0 to 1) to listOf(0 to 0, -2 to 0, 1 to 0, -2 to -1, 1 to 2),
        (1 to 0) to listOf(0 to 0, 2 to 0, -1 to 0, 2 to 1, -1 to -2),
        (1 to 2) to listOf(0 to 0, -1 to 0, 2 to 0, -1 to 2, 2 to -1),
        (2 to 1) to listOf(0 to 0, 1 to 0, -2 to 0, 1 to -2, -2 to 1),
        (2 to 3) to listOf(0 to 0, 2 to 0, -1 to 0, 2 to 1, -1 to -2),
        (3 to 2) to listOf(0 to 0, -2 to 0, 1 to 0, -2 to -1, 1 to 2),
        (3 to 0) to listOf(0 to 0, 1 to 0, -2 to 0, 1 to -2, -2 to 1),
        (0 to 3) to listOf(0 to 0, -1 to 0, 2 to 0, -1 to 2, 2 to -1),
    )
}
