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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
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

private val EdgeBgTop = Color(0xFF060B22)
private val EdgeBgMiddle = Color(0xFF091532)
private val EdgeBgBottom = Color(0xFF040817)

private val EdgeCyan = Color(0xFF43DBFF)
private val EdgePurple = Color(0xFF9A56FF)
private val EdgeWhite = Color(0xFFF8FBFF)
private val EdgeSecondary = Color(0xFFAEC2F2)
private val EdgeCard = Color(0xFF11214B)
private val EdgeCard2 = Color(0xFF0C1737)
private val EdgeGreen = Color(0xFF5BFFB3)

private data class EdgeTouchMarker(
    val id: Long,
    val position: Offset
)

@Composable
fun EdgeTestScreen(
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    val edgeSegmentsCount = 6
    val totalZones = edgeSegmentsCount * 4

    val visitedZones = remember { mutableStateListOf<Int>() }
    val activeTouches = remember { mutableStateListOf<EdgeTouchMarker>() }

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var currentTouches by remember { mutableIntStateOf(0) }
    var completed by remember { mutableStateOf(false) }

    val progress = visitedZones.size
    val percent = ((progress.toFloat() / totalZones.toFloat()) * 100f).toInt()

    fun resetTest() {
        visitedZones.clear()
        activeTouches.clear()
        currentTouches = 0
        completed = false
    }

    fun markEdgeVisited(position: Offset) {
        val width = canvasSize.width.toFloat()
        val height = canvasSize.height.toFloat()
        if (width <= 0f || height <= 0f) return

        val edgeThickness = minOf(width, height) * 0.12f
        val segmentWidth = width / edgeSegmentsCount
        val segmentHeight = height / edgeSegmentsCount

        val x = position.x.coerceIn(0f, width)
        val y = position.y.coerceIn(0f, height)

        val inTop = y <= edgeThickness
        val inBottom = y >= height - edgeThickness
        val inLeft = x <= edgeThickness
        val inRight = x >= width - edgeThickness

        if (inTop) {
            val index = (x / segmentWidth).toInt().coerceIn(0, edgeSegmentsCount - 1)
            val zoneId = index
            if (!visitedZones.contains(zoneId)) visitedZones.add(zoneId)
        }

        if (inRight) {
            val index = (y / segmentHeight).toInt().coerceIn(0, edgeSegmentsCount - 1)
            val zoneId = edgeSegmentsCount + index
            if (!visitedZones.contains(zoneId)) visitedZones.add(zoneId)
        }

        if (inBottom) {
            val index = (x / segmentWidth).toInt().coerceIn(0, edgeSegmentsCount - 1)
            val zoneId = edgeSegmentsCount * 2 + index
            if (!visitedZones.contains(zoneId)) visitedZones.add(zoneId)
        }

        if (inLeft) {
            val index = (y / segmentHeight).toInt().coerceIn(0, edgeSegmentsCount - 1)
            val zoneId = edgeSegmentsCount * 3 + index
            if (!visitedZones.contains(zoneId)) visitedZones.add(zoneId)
        }

        completed = visitedZones.size >= totalZones
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        EdgeBgTop,
                        EdgeBgMiddle,
                        EdgeBgBottom
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
            EdgeTestTopBar(onBack = onBack)

            Spacer(modifier = Modifier.height(14.dp))

            EdgeStatusCard(
                progress = progress,
                total = totalZones,
                percent = percent,
                currentTouches = currentTouches,
                completed = completed,
                onReset = ::resetTest
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (completed) {
                    "Great! Screen edges responded."
                } else {
                    "Trace your finger around all screen edges."
                },
                color = EdgeWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Move along top, right, bottom, and left borders.",
                color = EdgeSecondary,
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
                                EdgeCyan.copy(alpha = 0.8f),
                                EdgePurple.copy(alpha = 0.55f)
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
                        .onSizeChanged { canvasSize = it }
                        .pointerInput(Unit) {
                            awaitEachGesture {
                                while (true) {
                                    val event = awaitPointerEvent(PointerEventPass.Main)

                                    activeTouches.clear()

                                    event.changes.forEach { change ->
                                        if (change.pressed) {
                                            val p = change.position
                                            activeTouches.add(
                                                EdgeTouchMarker(
                                                    id = change.id.value,
                                                    position = p
                                                )
                                            )
                                            markEdgeVisited(p)
                                        }
                                    }

                                    currentTouches = activeTouches.size

                                    if (event.changes.none { it.pressed }) {
                                        activeTouches.clear()
                                        currentTouches = 0
                                        break
                                    }
                                }
                            }
                        }
                ) {
                    EdgeZonesCanvas(
                        canvasSize = canvasSize,
                        edgeSegmentsCount = edgeSegmentsCount,
                        visitedZones = visitedZones.toSet(),
                        activeTouches = activeTouches.toList()
                    )

                    if (visitedZones.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Trace edges",
                                    color = EdgeWhite.copy(alpha = 0.85f),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Cover the full border of the screen",
                                    color = EdgeSecondary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            EdgeBottomActions(
                completed = completed,
                onReset = ::resetTest,
                onDone = onBack
            )
        }
    }
}

@Composable
private fun EdgeTestTopBar(
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EdgeCircleIconButton(
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
                text = "Edge Test",
                color = EdgeWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Check border responsiveness",
                color = EdgeSecondary,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun EdgeStatusCard(
    progress: Int,
    total: Int,
    percent: Int,
    currentTouches: Int,
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
                            EdgeCard,
                            EdgeCard2,
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
                            color = if (completed) EdgeGreen else EdgeWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "$progress / $total edge zones covered",
                            color = EdgeSecondary,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Active fingers: $currentTouches",
                            color = EdgeSecondary,
                            fontSize = 14.sp
                        )
                    }

                    EdgeCircleIconButton(
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
                                        EdgeCyan,
                                        EdgePurple
                                    )
                                ),
                                RoundedCornerShape(50)
                            )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$percent%",
                    color = EdgeWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun EdgeZonesCanvas(
    canvasSize: IntSize,
    edgeSegmentsCount: Int,
    visitedZones: Set<Int>,
    activeTouches: List<EdgeTouchMarker>
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val edgeThickness = minOf(width, height) * 0.12f
        val segmentWidth = width / edgeSegmentsCount
        val segmentHeight = height / edgeSegmentsCount

        drawRect(
            color = Color(0xFF0A1730)
        )

        for (i in 0 until edgeSegmentsCount) {
            val topId = i
            val rightId = edgeSegmentsCount + i
            val bottomId = edgeSegmentsCount * 2 + i
            val leftId = edgeSegmentsCount * 3 + i

            val topRect = Rect(
                left = i * segmentWidth,
                top = 0f,
                right = (i + 1) * segmentWidth,
                bottom = edgeThickness
            )

            val rightRect = Rect(
                left = width - edgeThickness,
                top = i * segmentHeight,
                right = width,
                bottom = (i + 1) * segmentHeight
            )

            val bottomRect = Rect(
                left = i * segmentWidth,
                top = height - edgeThickness,
                right = (i + 1) * segmentWidth,
                bottom = height
            )

            val leftRect = Rect(
                left = 0f,
                top = i * segmentHeight,
                right = edgeThickness,
                bottom = (i + 1) * segmentHeight
            )

            fun drawZone(rect: Rect, active: Boolean) {
                drawRoundRect(
                    color = if (active) EdgeCyan.copy(alpha = 0.26f) else Color.White.copy(alpha = 0.04f),
                    topLeft = rect.topLeft,
                    size = rect.size,
                    cornerRadius = CornerRadius(14f, 14f)
                )
                drawRoundRect(
                    color = if (active) EdgeCyan.copy(alpha = 0.75f) else Color(0xFF6FD8FF).copy(alpha = 0.14f),
                    topLeft = rect.topLeft,
                    size = rect.size,
                    cornerRadius = CornerRadius(14f, 14f),
                    style = Stroke(width = 2f)
                )
            }

            drawZone(topRect, visitedZones.contains(topId))
            drawZone(rightRect, visitedZones.contains(rightId))
            drawZone(bottomRect, visitedZones.contains(bottomId))
            drawZone(leftRect, visitedZones.contains(leftId))
        }

        drawRoundRect(
            color = Color(0xFF6FD8FF).copy(alpha = 0.20f),
            topLeft = Offset(edgeThickness, edgeThickness),
            size = Size(
                width = width - edgeThickness * 2,
                height = height - edgeThickness * 2
            ),
            cornerRadius = CornerRadius(24f, 24f),
            style = Stroke(width = 2f)
        )

        activeTouches.forEach { touch ->
            drawCircle(
                color = EdgeCyan.copy(alpha = 0.18f),
                radius = 70f,
                center = touch.position
            )
            drawCircle(
                color = EdgeCyan.copy(alpha = 0.30f),
                radius = 42f,
                center = touch.position
            )
            drawCircle(
                color = EdgeCyan,
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
private fun EdgeBottomActions(
    completed: Boolean,
    onReset: () -> Unit,
    onDone: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EdgeActionButton(
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

        EdgeActionButton(
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
private fun EdgeActionButton(
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
private fun EdgeCircleIconButton(
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