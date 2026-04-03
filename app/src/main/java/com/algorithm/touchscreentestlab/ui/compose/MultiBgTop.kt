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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.algorithm.touchscreentestlab.R

private val MultiBgTop = Color(0xFF060B22)
private val MultiBgMiddle = Color(0xFF091532)
private val MultiBgBottom = Color(0xFF040817)

private val MultiCyan = Color(0xFF43DBFF)
private val MultiPurple = Color(0xFF9A56FF)
private val MultiWhite = Color(0xFFF8FBFF)
private val MultiSecondary = Color(0xFFAEC2F2)
private val MultiCard = Color(0xFF11214B)
private val MultiCard2 = Color(0xFF0C1737)
private val MultiGreen = Color(0xFF5BFFB3)
private val MultiPink = Color(0xFFFF73D0)
private val MultiOrange = Color(0xFFFFB36E)

private data class MultiTouchPoint(
    val id: Long,
    val position: Offset
)

@Composable
fun MultiTouchTestScreen(
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    val activeTouches = remember { mutableStateListOf<MultiTouchPoint>() }
    var highestReached by remember { mutableIntStateOf(0) }

    val targetTouches = 5
    val currentTouches = activeTouches.size
    val completed = highestReached >= targetTouches

    fun resetTest() {
        activeTouches.clear()
        highestReached = 0
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MultiBgTop, MultiBgMiddle, MultiBgBottom)
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
            MultiTouchTopBar(onBack = onBack)

            Spacer(modifier = Modifier.height(14.dp))

            MultiTouchStatusCard(
                currentTouches = currentTouches,
                highestReached = highestReached,
                targetTouches = targetTouches,
                completed = completed,
                onReset = ::resetTest
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (completed) {
                    stringResource(R.string.multi_touch_success)
                } else {
                    stringResource(R.string.multi_touch_instruction)
                },
                color = MultiWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(R.string.multi_touch_hint),
                color = MultiSecondary,
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
                                MultiCyan.copy(alpha = 0.8f),
                                MultiPurple.copy(alpha = 0.55f)
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
                                while (true) {
                                    val event = awaitPointerEvent(PointerEventPass.Main)

                                    activeTouches.clear()

                                    event.changes.forEach { change ->
                                        if (change.pressed) {
                                            activeTouches.add(
                                                MultiTouchPoint(
                                                    id = change.id.value,
                                                    position = change.position
                                                )
                                            )
                                        }
                                    }

                                    if (activeTouches.size > highestReached) {
                                        highestReached = activeTouches.size
                                    }

                                    if (event.changes.none { it.pressed }) {
                                        activeTouches.clear()
                                        break
                                    }
                                }
                            }
                        }
                ) {
                    MultiTouchCanvas(touches = activeTouches.toList())

                    MultiTouchCenterInfo(
                        currentTouches = currentTouches,
                        highestReached = highestReached,
                        targetTouches = targetTouches,
                        completed = completed
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            MultiTouchBottomActions(
                completed = completed,
                onReset = ::resetTest,
                onDone = onBack
            )
        }
    }
}

@Composable
private fun MultiTouchTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MultiCircleIconButton(
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
                text = stringResource(R.string.multi_touch_screen_title),
                color = MultiWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.multi_touch_screen_subtitle),
                color = MultiSecondary,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun MultiTouchStatusCard(
    currentTouches: Int,
    highestReached: Int,
    targetTouches: Int,
    completed: Boolean,
    onReset: () -> Unit
) {
    val progressFraction = (highestReached.toFloat() / targetTouches.toFloat())
        .coerceIn(0f, 1f)

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
                            MultiCard,
                            MultiCard2,
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
                            text = if (completed) {
                                stringResource(R.string.status_passed)
                            } else {
                                stringResource(R.string.status_in_progress)
                            },
                            color = if (completed) MultiGreen else MultiWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(
                                R.string.multi_touch_current_highest,
                                currentTouches,
                                highestReached
                            ),
                            color = MultiSecondary,
                            fontSize = 14.sp
                        )
                    }

                    MultiCircleIconButton(
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
                            .fillMaxWidth(progressFraction)
                            .height(12.dp)
                            .background(
                                Brush.horizontalGradient(listOf(MultiCyan, MultiPurple)),
                                RoundedCornerShape(50)
                            )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.multi_touch_target, targetTouches),
                    color = MultiWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun MultiTouchCanvas(touches: List<MultiTouchPoint>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(color = Color(0xFF0A1730))

        val guideColor = Color(0xFF6FD8FF).copy(alpha = 0.12f)

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

        touches.forEachIndexed { index, touch ->
            val accent = when (index % 4) {
                0 -> MultiCyan
                1 -> MultiPurple
                2 -> MultiPink
                else -> MultiOrange
            }

            drawCircle(
                color = accent.copy(alpha = 0.14f),
                radius = 95f,
                center = touch.position
            )
            drawCircle(
                color = accent.copy(alpha = 0.24f),
                radius = 58f,
                center = touch.position
            )
            drawCircle(
                color = accent,
                radius = 20f,
                center = touch.position
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.75f),
                radius = 34f,
                center = touch.position,
                style = Stroke(width = 3f)
            )
        }
    }
}

@Composable
private fun MultiTouchCenterInfo(
    currentTouches: Int,
    highestReached: Int,
    targetTouches: Int,
    completed: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (currentTouches == 0) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.multi_touch_start_hint),
                    color = MultiWhite.copy(alpha = 0.85f),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.multi_touch_start_subhint),
                    color = MultiSecondary,
                    fontSize = 14.sp
                )
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = currentTouches.toString(),
                    color = if (completed) MultiGreen else MultiWhite,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = stringResource(
                        if (currentTouches == 1) {
                            R.string.multi_touch_finger_singular
                        } else {
                            R.string.multi_touch_finger_plural
                        }
                    ),
                    color = MultiSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(
                        R.string.multi_touch_highest_progress,
                        highestReached,
                        targetTouches
                    ),
                    color = if (completed) MultiGreen else MultiSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun MultiTouchBottomActions(
    completed: Boolean,
    onReset: () -> Unit,
    onDone: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MultiActionButton(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.reset),
            background = Brush.horizontalGradient(
                listOf(Color(0xFF16335F), Color(0xFF1C3F73))
            ),
            onClick = onReset
        )

        MultiActionButton(
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
private fun MultiActionButton(
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
private fun MultiCircleIconButton(
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