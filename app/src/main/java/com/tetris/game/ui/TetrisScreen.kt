package com.tetris.game.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetris.game.engine.BagPieceSource
import com.tetris.game.engine.Cell
import com.tetris.game.engine.GameSnapshot
import com.tetris.game.engine.PieceType
import com.tetris.game.engine.Shapes
import com.tetris.game.engine.TetrisGame
import kotlin.random.Random
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private val Background = Color(0xFF07080D)
private val Panel = Color(0xFF141821)
private val BoardColor = Color(0xFF0C1017)
private val GridColor = Color(0xFF222838)
private val Muted = Color(0xFF9AA3B2)
private val Accent = Color(0xFFE8EDF5)

private fun colorOf(type: PieceType): Color = when (type) {
    PieceType.I -> Color(0xFF2DE2E2)
    PieceType.O -> Color(0xFFF5D90A)
    PieceType.T -> Color(0xFFC15CFF)
    PieceType.S -> Color(0xFF3DDC6A)
    PieceType.Z -> Color(0xFFFF5A5A)
    PieceType.J -> Color(0xFF4C8DFF)
    PieceType.L -> Color(0xFFFF9F1C)
}

class TetrisController(seed: Long = System.currentTimeMillis()) {
    private val game = TetrisGame(BagPieceSource(Random(seed)))
    var snapshot by mutableStateOf(game.snapshot())
        private set

    fun left() = act { game.moveLeft() }
    fun right() = act { game.moveRight() }
    fun rotateClockwise() = act { game.rotateClockwise() }
    fun rotateCounterClockwise() = act { game.rotateCounterClockwise() }
    fun softDrop() = act { game.softDrop() }
    fun hardDrop() = act { game.hardDrop() }
    fun tick() = act { game.tick() }
    fun restart() = act { game.restart() }

    private fun act(block: () -> Unit) {
        block()
        snapshot = game.snapshot()
    }
}

@Composable
fun TetrisApp(controller: TetrisController = remember { TetrisController() }) {
    val snapshot = controller.snapshot
    LaunchedEffect(snapshot.isGameOver, snapshot.level) {
        while (!controller.snapshot.isGameOver) {
            delay(controller.snapshot.dropIntervalMillis)
            if (!controller.snapshot.isGameOver) controller.tick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Header(snapshot)
        Spacer(Modifier.height(12.dp))
        BoardArea(
            snapshot = snapshot,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        )
        Spacer(Modifier.height(12.dp))
        Controls(
            onLeft = controller::left,
            onRight = controller::right,
            onRotateCw = controller::rotateClockwise,
            onRotateCcw = controller::rotateCounterClockwise,
            onSoftDrop = controller::softDrop,
            onHardDrop = controller::hardDrop,
            onRestart = controller::restart,
        )
    }
}

@Composable
private fun Header(snapshot: GameSnapshot) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "TETRIS",
            color = Accent,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 4.sp,
            modifier = Modifier.weight(1f),
        )
        Stat("SCORE", snapshot.score)
        Spacer(Modifier.width(16.dp))
        Stat("LINES", snapshot.lines)
        Spacer(Modifier.width(16.dp))
        Stat("LVL", snapshot.level)
    }
}

@Composable
private fun Stat(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.End) {
        Text(label, color = Muted, fontSize = 10.sp, letterSpacing = 1.sp)
        Text(
            text = value.toString(),
            color = Accent,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun BoardArea(snapshot: GameSnapshot, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        val gap = 12.dp
        val nextWidth = 92.dp
        val cellByWidth = (maxWidth - nextWidth - gap) / snapshot.width
        val cellByHeight = maxHeight / snapshot.height
        val cell = minOf(cellByWidth, cellByHeight)
        val boardWidth = cell * snapshot.width
        val boardHeight = cell * snapshot.height
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Box(modifier = Modifier.size(boardWidth, boardHeight)) {
                BoardCanvas(snapshot, Modifier.fillMaxSize())
                if (snapshot.isGameOver) {
                    GameOverOverlay(Modifier.fillMaxSize())
                }
            }
            Spacer(Modifier.width(gap))
            NextPanel(
                type = snapshot.next,
                modifier = Modifier.width(nextWidth),
            )
        }
    }
}

@Composable
private fun BoardCanvas(snapshot: GameSnapshot, modifier: Modifier = Modifier) {
    Canvas(
        modifier
            .clip(RoundedCornerShape(8.dp))
            .background(BoardColor)
            .border(1.dp, GridColor, RoundedCornerShape(8.dp)),
    ) {
        val cellW = size.width / snapshot.width
        val cellH = size.height / snapshot.height
        for (x in 1 until snapshot.width) {
            drawLine(GridColor, Offset(x * cellW, 0f), Offset(x * cellW, size.height), strokeWidth = 1f)
        }
        for (y in 1 until snapshot.height) {
            drawLine(GridColor, Offset(0f, y * cellH), Offset(size.width, y * cellH), strokeWidth = 1f)
        }
        for (y in 0 until snapshot.height) {
            for (x in 0 until snapshot.width) {
                val type = snapshot.locked[y][x] ?: continue
                drawBlock(x, y, cellW, cellH, colorOf(type))
            }
        }
        drawCells(snapshot.ghostCells, cellW, cellH, Color.White.copy(alpha = 0.18f), filled = false)
        snapshot.activeType?.let { type ->
            drawCells(snapshot.activeCells, cellW, cellH, colorOf(type), filled = true)
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCells(
    cells: List<Cell>,
    cellW: Float,
    cellH: Float,
    color: Color,
    filled: Boolean,
) {
    for (cell in cells) {
        if (cell.y < 0) continue
        if (filled) drawBlock(cell.x, cell.y, cellW, cellH, color)
        else {
            val inset = minOf(cellW, cellH) * 0.12f
            drawRoundRect(
                color = color,
                topLeft = Offset(cell.x * cellW + inset, cell.y * cellH + inset),
                size = Size(cellW - inset * 2, cellH - inset * 2),
                cornerRadius = CornerRadius(4f, 4f),
                style = Stroke(width = maxOf(1.5f, cellW * 0.06f)),
            )
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawBlock(
    x: Int,
    y: Int,
    cellW: Float,
    cellH: Float,
    color: Color,
) {
    val inset = minOf(cellW, cellH) * 0.08f
    val left = x * cellW + inset
    val top = y * cellH + inset
    val w = cellW - inset * 2
    val h = cellH - inset * 2
    drawRoundRect(
        color = color,
        topLeft = Offset(left, top),
        size = Size(w, h),
        cornerRadius = CornerRadius(3.5f, 3.5f),
    )
    drawRoundRect(
        color = Color.White.copy(alpha = 0.28f),
        topLeft = Offset(left, top),
        size = Size(w, h * 0.42f),
        cornerRadius = CornerRadius(3.5f, 3.5f),
    )
}

@Composable
private fun GameOverOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color(0xCC07080D)),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("GAME OVER", color = Accent, fontWeight = FontWeight.Black, fontSize = 22.sp, letterSpacing = 2.sp)
            Spacer(Modifier.height(4.dp))
            Text("Tap New game", color = Muted, fontSize = 13.sp)
        }
    }
}

@Composable
private fun NextPanel(type: PieceType, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Panel)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("NEXT", color = Muted, fontSize = 11.sp, letterSpacing = 1.5.sp)
        Spacer(Modifier.height(8.dp))
        val cells = Shapes.cellsOf(type, rotation = 0, originX = 0, originY = 0)
        Canvas(Modifier.size(72.dp)) {
            val cell = size.minDimension / 4f
            val minX = cells.minOf { it.x }
            val maxX = cells.maxOf { it.x }
            val minY = cells.minOf { it.y }
            val maxY = cells.maxOf { it.y }
            val offsetX = (4 - (maxX - minX + 1)) / 2f - minX
            val offsetY = (4 - (maxY - minY + 1)) / 2f - minY
            for (cellPos in cells) {
                val inset = cell * 0.08f
                val left = (cellPos.x + offsetX) * cell + inset
                val top = (cellPos.y + offsetY) * cell + inset
                drawRoundRect(
                    color = colorOf(type),
                    topLeft = Offset(left, top),
                    size = Size(cell - inset * 2, cell - inset * 2),
                    cornerRadius = CornerRadius(3f, 3f),
                )
            }
        }
    }
}

@Composable
private fun Controls(
    onLeft: () -> Unit,
    onRight: () -> Unit,
    onRotateCw: () -> Unit,
    onRotateCcw: () -> Unit,
    onSoftDrop: () -> Unit,
    onHardDrop: () -> Unit,
    onRestart: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            ControlButton("CCW", "Rotate counter-clockwise", Modifier.weight(1f), repeat = false, onClick = onRotateCcw)
            ControlButton("Left", "Move left", Modifier.weight(1f), onClick = onLeft)
            ControlButton("Right", "Move right", Modifier.weight(1f), onClick = onRight)
            ControlButton("CW", "Rotate clockwise", Modifier.weight(1f), repeat = false, onClick = onRotateCw)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            ControlButton("Soft", "Soft drop", Modifier.weight(1f), onClick = onSoftDrop)
            ControlButton("Hard", "Hard drop", Modifier.weight(1f), repeat = false, onClick = onHardDrop)
            ControlButton("New", "New game", Modifier.weight(1f), repeat = false, onClick = onRestart)
        }
    }
}

@Composable
private fun ControlButton(
    label: String,
    description: String,
    modifier: Modifier = Modifier,
    repeat: Boolean = true,
    onClick: () -> Unit,
) {
    val action = rememberUpdatedState(onClick)
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Panel)
            .semantics { contentDescription = description }
            .pointerInput(repeat) {
                detectTapGestures(
                    onPress = {
                        action.value()
                        if (!repeat) {
                            tryAwaitRelease()
                            return@detectTapGestures
                        }
                        coroutineScope {
                            val job = launch {
                                delay(200)
                                while (isActive) {
                                    action.value()
                                    delay(45)
                                }
                            }
                            tryAwaitRelease()
                            job.cancel()
                        }
                    },
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            color = Accent,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
        )
    }
}
