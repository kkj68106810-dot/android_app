package com.tetris.game.engine

/**
 * Pure Tetris rules. No Android or Compose types.
 *
 * The visible board is [width] by [height] (10x20). The active piece may sit
 * one or two rows above the board (negative y) while rotating at the ceiling.
 */
class TetrisGame(
    private val pieces: PieceSource,
    val width: Int = WIDTH,
    val height: Int = HEIGHT,
) {
    private val board: Array<Array<PieceType?>> = Array(height) { arrayOfNulls(width) }
    private var active: Piece? = null
    private var nextType: PieceType = pieces.next()

    var score: Int = 0
        private set
    var lines: Int = 0
        private set
    var level: Int = 1
        private set
    var isGameOver: Boolean = false
        private set

    init {
        require(width >= 4 && height >= 8)
        spawn()
    }

    fun snapshot(): GameSnapshot {
        val piece = active
        val activeCells = piece?.let { cellsOf(it) }.orEmpty()
        return GameSnapshot(
            width = width,
            height = height,
            locked = board.map { row -> row.toList() },
            activeType = piece?.type,
            activeRotation = piece?.rotation ?: 0,
            activeX = piece?.x ?: 0,
            activeY = piece?.y ?: 0,
            activeCells = activeCells,
            ghostCells = if (piece == null || isGameOver) emptyList() else ghostCells(piece),
            next = nextType,
            nextCells = Shapes.cellsOf(nextType, rotation = 0, originX = 0, originY = 0),
            score = score,
            lines = lines,
            level = level,
            isGameOver = isGameOver,
            dropIntervalMillis = dropIntervalMillis(level),
        )
    }

    fun moveLeft(): Boolean = tryMove(-1, 0)

    fun moveRight(): Boolean = tryMove(1, 0)

    fun rotateClockwise(): Boolean = rotate(1)

    fun rotateCounterClockwise(): Boolean = rotate(-1)

    /** Moves down one row, or locks the piece when it cannot fall. */
    fun softDrop(): Boolean {
        if (isGameOver) return false
        if (tryMove(0, 1)) {
            score += SOFT_DROP_SCORE
            return true
        }
        lock()
        return false
    }

    fun hardDrop() {
        if (isGameOver) return
        var distance = 0
        while (tryMove(0, 1)) distance++
        score += distance * HARD_DROP_SCORE
        lock()
    }

    /** Gravity tick. Locks the piece when it cannot fall. */
    fun tick() {
        if (isGameOver) return
        if (!tryMove(0, 1)) lock()
    }

    fun restart() {
        for (y in 0 until height) {
            for (x in 0 until width) board[y][x] = null
        }
        score = 0
        lines = 0
        level = 1
        isGameOver = false
        active = null
        spawn()
    }

    /** True when every cell of the piece is inside the board and unoccupied. */
    internal fun fits(type: PieceType, rotation: Int, x: Int, y: Int): Boolean {
        return !collides(type, rotation, x, y)
    }

    internal fun placeActiveForTest(type: PieceType, rotation: Int, x: Int, y: Int) {
        active = Piece(type, rotation, x, y)
        isGameOver = false
    }

    internal fun loadLockedForTest(cells: List<Pair<Int, Int>>, type: PieceType = PieceType.I) {
        for ((x, y) in cells) {
            if (x in 0 until width && y in 0 until height) board[y][x] = type
        }
    }

    internal fun clearCompletedLines(): Int = clearLines()

    private fun tryMove(dx: Int, dy: Int): Boolean {
        val piece = active ?: return false
        if (isGameOver) return false
        val next = piece.copy(x = piece.x + dx, y = piece.y + dy)
        if (collides(next)) return false
        active = next
        return true
    }

    private fun rotate(direction: Int): Boolean {
        val piece = active ?: return false
        if (isGameOver) return false
        val from = piece.rotation
        val to = (from + direction).mod(4)
        for ((kickX, kickY) in Shapes.kicks(piece.type, from, to)) {
            val next = piece.copy(rotation = to, x = piece.x + kickX, y = piece.y + kickY)
            if (!collides(next)) {
                active = next
                return true
            }
        }
        return false
    }

    private fun lock() {
        val piece = active ?: return
        if (isGameOver) return
        for (cell in cellsOf(piece)) {
            if (cell.x !in 0 until width || cell.y !in 0 until height) {
                isGameOver = true
                return
            }
            board[cell.y][cell.x] = piece.type
        }
        clearLines()
        spawn()
    }

    private fun spawn() {
        val type = nextType
        nextType = pieces.next()
        val spawned = Piece(type, rotation = 0, x = SPAWN_X, y = SPAWN_Y)
        active = spawned
        if (collides(spawned)) isGameOver = true
    }

    private fun clearLines(): Int {
        val kept = board.filter { row -> row.any { it == null } }
        val cleared = height - kept.size
        if (cleared == 0) return 0
        val blanks = List(cleared) { arrayOfNulls<PieceType?>(width) }
        val rebuilt = blanks + kept
        for (y in 0 until height) {
            board[y] = rebuilt[y]
        }
        score += lineScore(cleared) * level
        lines += cleared
        level = 1 + lines / LINES_PER_LEVEL
        return cleared
    }

    private fun ghostCells(piece: Piece): List<Cell> {
        var y = piece.y
        while (!collides(piece.type, piece.rotation, piece.x, y + 1)) y++
        return Shapes.cellsOf(piece.type, piece.rotation, piece.x, y)
    }

    private fun collides(piece: Piece): Boolean = collides(piece.type, piece.rotation, piece.x, piece.y)

    private fun collides(type: PieceType, rotation: Int, x: Int, y: Int): Boolean {
        for (cell in Shapes.cellsOf(type, rotation, x, y)) {
            if (cell.x !in 0 until width || cell.y >= height) return true
            if (cell.y >= 0 && board[cell.y][cell.x] != null) return true
        }
        return false
    }

    private fun cellsOf(piece: Piece): List<Cell> = Shapes.cellsOf(piece.type, piece.rotation, piece.x, piece.y)

    companion object {
        const val WIDTH = 10
        const val HEIGHT = 20
        const val SPAWN_X = 3
        const val SPAWN_Y = 0
        const val LINES_PER_LEVEL = 10
        const val SOFT_DROP_SCORE = 1
        const val HARD_DROP_SCORE = 2

        fun lineScore(cleared: Int): Int = when (cleared) {
            1 -> 100
            2 -> 300
            3 -> 500
            4 -> 800
            else -> 0
        }

        /** Gravity interval. Each level is a little faster, with a floor. */
        fun dropIntervalMillis(level: Int): Long {
            val safeLevel = level.coerceAtLeast(1)
            return (800L - (safeLevel - 1) * 70L).coerceAtLeast(80L)
        }
    }
}
