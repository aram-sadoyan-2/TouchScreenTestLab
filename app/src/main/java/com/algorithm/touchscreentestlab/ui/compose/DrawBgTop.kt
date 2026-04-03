@file:Suppress("FunctionName")

package com.algorithm.touchscreentestlab.ui.compose

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.algorithm.touchscreentestlab.R

private val DrawBgTop = Color(0xFF060B22)
private val DrawBgMiddle = Color(0xFF091532)
private val DrawBgBottom = Color(0xFF040817)

private val DrawCyan = Color(0xFF43DBFF)
private val DrawPurple = Color(0xFF9A56FF)
private val DrawWhite = Color(0xFFF8FBFF)
private val DrawSecondary = Color(0xFFAEC2F2)
private val DrawCard = Color(0xFF11214B)
private val DrawCard2 = Color(0xFF0C1737)
private val DrawGreen = Color(0xFF5BFFB3)
private val DrawGrid = Color(0xFF6FD8FF)

private data class DrawStroke(
    val points: MutableList<Offset>,
    val color: Color
)

@Composable
fun DrawTestScreen(
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    val strokes = remember { mutableStateListOf<DrawStroke>() }
    var currentPointerCount by remember { mutableStateOf(0) }
    var totalPoints by remember { mutableStateOf(0) }
    var hasDrawn by remember { mutableStateOf(false) }

    fun resetTest() {
        strokes.clear()
        currentPointerCount = 0
        totalPoints = 0
        hasDrawn = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DrawBgTop, DrawBgMiddle, DrawBgBottom)
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
            DrawTestTopBar(onBack = onBack)

            Spacer(modifier = Modifier.height(14.dp))

            DrawStatusCard(
                hasDrawn = hasDrawn,
                totalStrokes = strokes.size,
                totalPoints = totalPoints,
                currentPointerCount = currentPointerCount,
                onReset = ::resetTest
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (hasDrawn) {
                    stringResource(R.string.draw_success)
                } else {
                    stringResource(R.string.draw_instruction)
                },
                color = DrawWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(R.string.draw_hint),
                color = DrawSecondary,
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
                                DrawCyan.copy(alpha = 0.8f),
                                DrawPurple.copy(alpha = 0.55f)
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
                        .pointerInput(Unit) {
                            awaitEachGesture {
                                var strokeStarted = false
                                var currentStroke: DrawStroke? = null

                                while (true) {
                                    val event = awaitPointerEvent(PointerEventPass.Main)
                                    val pressedChanges = event.changes.filter { it.pressed }

                                    currentPointerCount = pressedChanges.size

                                    if (pressedChanges.isNotEmpty()) {
                                        if (!strokeStarted) {
                                            val start = pressedChanges.first().position
                                            currentStroke = DrawStroke(
                                                points = mutableListOf(start),
                                                color = DrawCyan
                                            )
                                            strokes.add(currentStroke)
                                            totalPoints += 1
                                            hasDrawn = true
                                            strokeStarted = true
                                        } else {
                                            pressedChanges.forEachIndexed { index, change ->
                                                if (index == 0) {
                                                    currentStroke?.points?.add(change.position)
                                                    totalPoints += 1
                                                }
                                            }
                                        }
                                    }

                                    if (event.changes.none { it.pressed }) {
                                        currentPointerCount = 0
                                        break
                                    }
                                }
                            }
                        }
                ) {
                    DrawGridCanvas(strokes = strokes)

                    if (!hasDrawn) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = stringResource(R.string.draw_start_hint),
                                    color = DrawWhite.copy(alpha = 0.85f),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = stringResource(R.string.draw_start_subhint),
                                    color = DrawSecondary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            DrawBottomActions(
                completed = hasDrawn,
                onReset = ::resetTest,
                onDone = onBack
            )
        }
    }
}

@Composable
private fun DrawTestTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DrawCircleIconButton(
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
                text = stringResource(R.string.draw_screen_title),
                color = DrawWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.draw_screen_subtitle),
                color = DrawSecondary,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun DrawStatusCard(
    hasDrawn: Boolean,
    totalStrokes: Int,
    totalPoints: Int,
    currentPointerCount: Int,
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
                            DrawCard,
                            DrawCard2,
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
                            text = if (hasDrawn) {
                                stringResource(R.string.status_passed)
                            } else {
                                stringResource(R.string.status_waiting)
                            },
                            color = if (hasDrawn) DrawGreen else DrawWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(
                                R.string.draw_strokes_points,
                                totalStrokes,
                                totalPoints
                            ),
                            color = DrawSecondary,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = stringResource(R.string.active_fingers, currentPointerCount),
                            color = DrawSecondary,
                            fontSize = 14.sp
                        )
                    }

                    DrawCircleIconButton(
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
            }
        }
    }
}

@Composable
private fun DrawGridCanvas(strokes: List<DrawStroke>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val guideColor = DrawGrid.copy(alpha = 0.12f)

        for (i in 1..4) {
            val y = size.height * (i / 5f)
            drawLine(
                color = guideColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 2f
            )
        }

        for (i in 1..2) {
            val x = size.width * (i / 3f)
            drawLine(
                color = guideColor,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 2f
            )
        }

        strokes.forEach { stroke ->
            if (stroke.points.size == 1) {
                drawCircle(
                    color = stroke.color,
                    radius = 18f,
                    center = stroke.points.first()
                )
            } else if (stroke.points.size > 1) {
                val path = Path().apply {
                    moveTo(stroke.points.first().x, stroke.points.first().y)
                    for (i in 1 until stroke.points.size) {
                        lineTo(stroke.points[i].x, stroke.points[i].y)
                    }
                }

                drawPath(
                    path = path,
                    color = stroke.color.copy(alpha = 0.22f),
                    style = Stroke(
                        width = 28f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )

                drawPath(
                    path = path,
                    color = stroke.color,
                    style = Stroke(
                        width = 10f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}

@Composable
private fun DrawBottomActions(
    completed: Boolean,
    onReset: () -> Unit,
    onDone: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DrawActionButton(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.reset),
            background = Brush.horizontalGradient(
                listOf(Color(0xFF16335F), Color(0xFF1C3F73))
            ),
            onClick = onReset
        )

        DrawActionButton(
            modifier = Modifier.weight(1f),
            title = stringResource(if (completed) R.string.done else R.string.finish),
            background = Brush.horizontalGradient(
                listOf(Color(0xFF18B8FF), Color(0xFF6E61FF))
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
private fun DrawActionButton(
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
            if (leading != null) Spacer(modifier = Modifier.width(8.dp))

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
private fun DrawCircleIconButton(
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