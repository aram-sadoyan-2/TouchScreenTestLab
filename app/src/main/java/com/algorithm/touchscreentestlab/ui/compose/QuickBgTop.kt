@file:Suppress("FunctionName")

package com.example.touchlab.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val QuickBgTop = Color(0xFF060B22)
private val QuickBgMiddle = Color(0xFF091532)
private val QuickBgBottom = Color(0xFF040817)

private val QuickCyan = Color(0xFF43DBFF)
private val QuickPurple = Color(0xFF9A56FF)
private val QuickWhite = Color(0xFFF8FBFF)
private val QuickSecondary = Color(0xFFAEC2F2)
private val QuickCard = Color(0xFF11214B)
private val QuickCard2 = Color(0xFF0C1737)
private val QuickGreen = Color(0xFF5BFFB3)

private data class TouchMarker(
    val id: Long,
    val position: Offset
)

@Composable
fun QuickTestScreen(
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    val cols = 8
    val rows = 14
    val totalCells = cols * rows

    val visitedCells = remember { mutableStateListOf<Int>() }
    val activeTouches = remember { mutableStateListOf<TouchMarker>() }

    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var testCompleted by remember { mutableStateOf(false) }

    val progress = visitedCells.size
    val progressPercent = ((progress.toFloat() / totalCells.toFloat()) * 100f).toInt()

    fun resetTest() {
        visitedCells.clear()
        activeTouches.clear()
        testCompleted = false
    }

    fun markVisited(offset: Offset) {
        if (containerSize.width <= 0 || containerSize.height <= 0) return

        val cellWidth = containerSize.width.toFloat() / cols
        val cellHeight = containerSize.height.toFloat() / rows

        val col = (offset.x / cellWidth).toInt().coerceIn(0, cols - 1)
        val row = (offset.y / cellHeight).toInt().coerceIn(0, rows - 1)

        val index = row * cols + col

        if (!visitedCells.contains(index)) {
            visitedCells.add(index)
        }

        testCompleted = visitedCells.size == totalCells
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        QuickBgTop,
                        QuickBgMiddle,
                        QuickBgBottom
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            QuickTestTopBar(onBack = onBack)

            Spacer(modifier = Modifier.height(14.dp))

            QuickStatusCard(
                progress = progress,
                total = totalCells,
                percent = progressPercent,
                completed = testCompleted,
                onReset = ::resetTest
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (testCompleted) {
                    "Great! Full screen checked."
                } else {
                    "Touch all zones on the screen."
                },
                color = QuickWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Every touched area will light up. Try to cover all blocks.",
                color = QuickSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .border(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(
                            listOf(
                                QuickCyan.copy(alpha = 0.8f),
                                QuickPurple.copy(alpha = 0.55f)
                            )
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .background(
                        color = Color(0xFF071225),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF08162B),
                                    Color(0xFF0A1831),
                                    Color(0xFF061022)
                                )
                            ),
                            RoundedCornerShape(18.dp)
                        )
                        .onSizeChanged { size ->
                            containerSize = size
                        }
                        .pointerInput(Unit) {
                            awaitEachGesture {
                                while (true) {
                                    val event = awaitPointerEvent(PointerEventPass.Main)

                                    activeTouches.clear()

                                    event.changes.forEach { change ->
                                        if (change.pressed) {
                                            activeTouches.add(
                                                TouchMarker(
                                                    id = change.id.value,
                                                    position = change.position
                                                )
                                            )
                                            markVisited(change.position)
                                        }
                                    }

                                    if (event.changes.none { it.pressed }) {
                                        activeTouches.clear()
                                        break
                                    }
                                }
                            }
                        }
                ) {
                    QuickGridCanvas(
                        cols = cols,
                        rows = rows,
                        visitedCells = visitedCells.toSet(),
                        activeTouches = activeTouches.toList()
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            QuickBottomActions(
                completed = testCompleted,
                onReset = ::resetTest,
                onDone = onBack
            )
        }
    }
}

@Composable
private fun QuickTestTopBar(
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIconButton(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            onClick = onBack
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "Quick Test",
                color = QuickWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Find dead zones fast",
                color = QuickSecondary,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun QuickStatusCard(
    progress: Int,
    total: Int,
    percent: Int,
    completed: Boolean,
    onReset: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            QuickCard,
                            QuickCard2,
                            Color(0xFF162553)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (completed) "Status: Passed" else "Status: In progress",
                            color = if (completed) QuickGreen else QuickWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "$progress / $total cells covered",
                            color = QuickSecondary,
                            fontSize = 14.sp
                        )
                    }

                    CircleIconButton(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Refresh,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        onClick = onReset
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(
                            Color.White.copy(alpha = 0.08f),
                            RoundedCornerShape(50)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(
                                fraction = if (percent <= 0) 0f else percent / 100f
                            )
                            .height(12.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        QuickCyan,
                                        QuickPurple
                                    )
                                ),
                                RoundedCornerShape(50)
                            )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$percent%",
                    color = QuickWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun QuickGridCanvas(
    cols: Int,
    rows: Int,
    visitedCells: Set<Int>,
    activeTouches: List<TouchMarker>
) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val cellWidth = size.width / cols
        val cellHeight = size.height / rows

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val index = row * cols + col

                val left = col * cellWidth
                val top = row * cellHeight

                if (visitedCells.contains(index)) {
                    drawRoundRect(
                        color = QuickCyan.copy(alpha = 0.20f),
                        topLeft = Offset(left + 2f, top + 2f),
                        size = Size(
                            width = cellWidth - 4f,
                            height = cellHeight - 4f
                        ),
                        cornerRadius = CornerRadius(10f, 10f)
                    )
                }
            }
        }

        for (i in 0..cols) {
            val x = i * cellWidth
            drawLine(
                color = Color(0xFF6FD8FF).copy(alpha = 0.35f),
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 2f
            )
        }

        for (i in 0..rows) {
            val y = i * cellHeight
            drawLine(
                color = Color(0xFF6FD8FF).copy(alpha = 0.35f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 2f
            )
        }

        activeTouches.forEach { touch ->
            drawCircle(
                color = QuickCyan.copy(alpha = 0.18f),
                radius = 70f,
                center = touch.position
            )
            drawCircle(
                color = QuickCyan.copy(alpha = 0.30f),
                radius = 42f,
                center = touch.position
            )
            drawCircle(
                color = QuickCyan,
                radius = 16f,
                center = touch.position
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.7f),
                radius = 28f,
                center = touch.position,
                style = Stroke(width = 3f)
            )
        }
    }
}

@Composable
private fun QuickBottomActions(
    completed: Boolean,
    onReset: () -> Unit,
    onDone: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionButton(
            modifier = Modifier.weight(1f),
            title = "Reset",
            background = Brush.horizontalGradient(
                listOf(
                    Color(0xFF16335F),
                    Color(0xFF1C3F73)
                )
            ),
            onClick = onReset
        )

        ActionButton(
            modifier = Modifier.weight(1f),
            title = if (completed) "Done" else "Finish",
            background = Brush.horizontalGradient(
                listOf(
                    Color(0xFF18B8FF),
                    Color(0xFF6E61FF)
                )
            ),
            leading = {
                if (completed) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            onClick = onDone
        )
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    title: String,
    background: Brush,
    leading: (@Composable (() -> Unit))? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leading?.invoke()

            if (leading != null) {
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun CircleIconButton(
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .background(
                color = Color.White.copy(alpha = 0.08f),
                shape = CircleShape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}