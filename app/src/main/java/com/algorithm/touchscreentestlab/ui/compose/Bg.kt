@file:Suppress("FunctionName")

package com.example.touchlab.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BorderOuter
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.ScreenshotMonitor
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ScreenBgTop = Color(0xFF060B22)
private val ScreenBgMiddle = Color(0xFF091532)
private val ScreenBgBottom = Color(0xFF040817)

private val CyanGlow = Color(0xFF43DBFF)
private val PurpleGlow = Color(0xFF9A56FF)
private val PinkGlow = Color(0xFFFF73DA)

private val WhiteText = Color(0xFFF8FBFF)
private val SecondaryText = Color(0xFFAEC2F2)
private val LightBlueText = Color(0xFF63D8FF)
private val DividerColor = Color(0xFF2A3C72)

@Composable
fun TouchLabHomeScreen(
    onQuickTestClick: () -> Unit = {},
    onMultiTouchClick: () -> Unit = {},
    onDrawTestClick: () -> Unit = {},
    onEdgeTestClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ScreenBgTop,
                        ScreenBgMiddle,
                        ScreenBgBottom
                    )
                )
            )
    ) {
        DecorativeBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSection()

            Spacer(modifier = Modifier.height(22.dp))

            HeroCard()

            Spacer(modifier = Modifier.height(18.dp))

            ActionCard(
                title = "Quick Test",
                subtitle = "Run a full screen check",
                iconTint = Color(0xFFFFC7EA),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.ScreenshotMonitor,
                        contentDescription = null,
                        tint = Color(0xFFFFC7EA),
                        modifier = Modifier.size(28.dp)
                    )
                },
                onClick = onQuickTestClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            ActionCard(
                title = "Multi-Touch Test",
                subtitle = "Check multiple touch points",
                iconTint = CyanGlow,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.TouchApp,
                        contentDescription = null,
                        tint = CyanGlow,
                        modifier = Modifier.size(28.dp)
                    )
                },
                onClick = onMultiTouchClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            ActionCard(
                title = "Draw Test",
                subtitle = "Test touch drawing accuracy",
                iconTint = Color(0xFFFFB36E),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Draw,
                        contentDescription = null,
                        tint = Color(0xFFFFB36E),
                        modifier = Modifier.size(28.dp)
                    )
                },
                onClick = onDrawTestClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            ActionCard(
                title = "Edge Test",
                subtitle = "Examine screen edges",
                iconTint = Color(0xFFA38BFF),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.BorderOuter,
                        contentDescription = null,
                        tint = Color(0xFFA38BFF),
                        modifier = Modifier.size(28.dp)
                    )
                },
                onClick = onEdgeTestClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            DeviceOverviewCard()

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun DecorativeBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
                .size(320.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            CyanGlow.copy(alpha = 0.14f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 160.dp, end = 10.dp)
                .size(260.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            PurpleGlow.copy(alpha = 0.16f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-40).dp)
                .size(220.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            CyanGlow.copy(alpha = 0.10f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 30.dp, y = (-120).dp)
                .size(260.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            PurpleGlow.copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF6DE7FF),
                            Color(0xFF5E7BFF),
                            Color(0xFF5B1EFF)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.TouchApp,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TouchLab",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Screen & Touch Diagnostic",
                color = Color(0xFF53D9FF),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun HeroCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .clip(RoundedCornerShape(34.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF123A73),
                        Color(0xFF164C93),
                        Color(0xFF5D2D8A)
                    )
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF66CFFF).copy(alpha = 0.45f),
                        Color(0xFF7B7CFF).copy(alpha = 0.35f),
                        Color(0xFFFF8BF3).copy(alpha = 0.35f)
                    )
                ),
                shape = RoundedCornerShape(34.dp)
            )
    ) {
        HeroBackgroundDecor()

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 18.dp)
        ) {
            HeroPhonePreview()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(128.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 46.dp,
                        topEnd = 46.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF182E67),
                            Color(0xFF132657),
                            Color(0xFF0F214E)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF67D8FF).copy(alpha = 0.16f),
                            Color.White.copy(alpha = 0.05f),
                            Color(0xFFFF8EF4).copy(alpha = 0.14f)
                        )
                    ),
                    shape = RoundedCornerShape(
                        topStart = 46.dp,
                        topEnd = 46.dp
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 18.dp)
            ) {
                Text(
                    text = "Test your screen in seconds",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Check dead zones, multi-touch, and touch accuracy",
                    color = Color(0xFFA9D7FF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun HeroBackgroundDecor() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 34.dp, top = 54.dp)
                .size(90.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF59D8FF).copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 24.dp, top = 42.dp)
                .size(110.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF82F1).copy(alpha = 0.10f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        HeroParticle(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 58.dp, top = 88.dp),
            size = 6.dp
        )

        HeroParticle(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 74.dp, top = 76.dp),
            size = 8.dp
        )

        HeroParticle(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 44.dp, top = 110.dp),
            size = 5.dp
        )

        HeroParticle(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 52.dp),
            size = 7.dp
        )

        HeroComet(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 78.dp, top = 66.dp)
        )

        HeroComet(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 60.dp, top = 92.dp)
        )
    }
}

@Composable
private fun HeroParticle(
    modifier: Modifier,
    size: Dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(0xFFA8DFFF).copy(alpha = 0.65f))
    )
}

@Composable
private fun HeroComet(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .size(width = 42.dp, height = 12.dp)
            .rotate(-38f)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFF88E4FF).copy(alpha = 0.12f),
                        Color(0xFF88E4FF).copy(alpha = 0.55f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
    )
}

@Composable
private fun HeroPhonePreview() {
    Box(
        modifier = Modifier
            .width(175.dp)
            .height(215.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(34.dp))
                .background(Color(0xFF050B1F))
                .border(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF3DE0FF).copy(alpha = 0.85f),
                            Color(0xFF2F67FF).copy(alpha = 0.35f),
                            Color(0xFFFF5DE9).copy(alpha = 0.55f)
                        )
                    ),
                    shape = RoundedCornerShape(34.dp)
                )
                .padding(horizontal = 7.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(26.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF07152A),
                                Color(0xFF0A1E3C),
                                Color(0xFF08172F)
                            )
                        )
                    )
            ) {
                HeroPhoneGrid()

                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 9.dp)
                        .width(42.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF1C2A46))
                )

                HeroTouchPoint(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 22.dp, end = 16.dp),
                    outer = 28.dp,
                    inner = 15.dp
                )

                HeroTouchPoint(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 50.dp, start = 18.dp),
                    outer = 42.dp,
                    inner = 22.dp
                )

                HeroTouchPoint(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 20.dp, bottom = 12.dp),
                    outer = 50.dp,
                    inner = 26.dp
                )

                HeroTouchPoint(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 8.dp, end = 10.dp),
                    outer = 34.dp,
                    inner = 18.dp
                )

                HeroTouchPoint(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 28.dp, end = 18.dp),
                    outer = 36.dp,
                    inner = 19.dp
                )

                HeroTouchPoint(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp, bottom = 10.dp),
                    outer = 30.dp,
                    inner = 15.dp
                )
            }
        }
    }
}

@Composable
private fun HeroPhoneGrid() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 16.dp)
    ) {
        val cols = 7
        val rows = 9
        val dx = size.width / cols
        val dy = size.height / rows

        for (i in 0..cols) {
            drawLine(
                color = Color(0xFF67D8FF).copy(alpha = 0.42f),
                start = Offset(i * dx, 0f),
                end = Offset(i * dx, size.height),
                strokeWidth = 1.2.dp.toPx()
            )
        }

        for (j in 0..rows) {
            drawLine(
                color = Color(0xFF67D8FF).copy(alpha = 0.42f),
                start = Offset(0f, j * dy),
                end = Offset(size.width, j * dy),
                strokeWidth = 1.2.dp.toPx()
            )
        }
    }
}

@Composable
private fun HeroTouchPoint(
    modifier: Modifier,
    outer: Dp,
    inner: Dp
) {
    Box(
        modifier = modifier
            .size(outer)
            .clip(CircleShape)
            .background(Color(0xFF3DE5FF).copy(alpha = 0.20f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(outer * 0.72f)
                .clip(CircleShape)
                .background(Color(0xFF3DE5FF).copy(alpha = 0.26f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(inner)
                    .clip(CircleShape)
                    .background(Color(0xFF47E7FF))
            )
        }
    }
}

@Composable
private fun PhonePreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .aspectRatio(0.55f)
                .clip(RoundedCornerShape(28.dp))
                .background(Color(0xFF071122))
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0A1930),
                                Color(0xFF0E2546),
                                Color(0xFF08172F)
                            )
                        )
                    )
            ) {
                ScreenGrid()

                TouchPoint(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 30.dp, end = 20.dp),
                    size = 18.dp
                )
                TouchPoint(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 26.dp, top = 4.dp),
                    size = 22.dp
                )
                TouchPoint(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 8.dp, end = 12.dp),
                    size = 24.dp
                )
                TouchPoint(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 58.dp, end = 18.dp),
                    size = 18.dp
                )
                TouchPoint(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 26.dp, bottom = 26.dp),
                    size = 14.dp
                )
            }
        }
    }
}

@Composable
private fun ScreenGrid() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val columns = 4
            val rows = 6
            val cellWidth = size.width / columns
            val cellHeight = size.height / rows

            for (i in 1 until columns) {
                drawRect(
                    color = Color.Transparent,
                    blendMode = BlendMode.SrcOver
                )
                drawLine(
                    color = CyanGlow.copy(alpha = 0.25f),
                    start = androidx.compose.ui.geometry.Offset(cellWidth * i, 0f),
                    end = androidx.compose.ui.geometry.Offset(cellWidth * i, size.height),
                    strokeWidth = 2f
                )
            }

            for (j in 1 until rows) {
                drawLine(
                    color = CyanGlow.copy(alpha = 0.25f),
                    start = androidx.compose.ui.geometry.Offset(0f, cellHeight * j),
                    end = androidx.compose.ui.geometry.Offset(size.width, cellHeight * j),
                    strokeWidth = 2f
                )
            }
        }
    }
}

@Composable
private fun TouchPoint(
    modifier: Modifier,
    size: androidx.compose.ui.unit.Dp
) {
    Box(
        modifier = modifier
            .size(size * 2.2f)
            .clip(CircleShape)
            .background(CyanGlow.copy(alpha = 0.14f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size * 1.55f)
                .clip(CircleShape)
                .background(CyanGlow.copy(alpha = 0.22f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(CyanGlow)
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    iconTint: Color,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF152B5D),
                            Color(0xFF1A2F64),
                            Color(0xFF5A2F87)
                        )
                    )
                )
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.06f)),
                    contentAlignment = Alignment.Center
                ) {
                    icon()
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = title,
                        color = WhiteText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = subtitle,
                        color = SecondaryText,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun DeviceOverviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF11214B),
                            Color(0xFF0D1A3D)
                        )
                    )
                )
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Device Overview",
                color = WhiteText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 18.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = DividerColor)

            OverviewRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.TouchApp,
                        contentDescription = null,
                        tint = CyanGlow,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = "Max Touches:",
                value = "10 Points"
            )

            HorizontalDivider(color = DividerColor)

            OverviewRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.History,
                        contentDescription = null,
                        tint = Color(0xFF9EB8FF),
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = "Last Test:",
                value = "Not Run Yet"
            )

            HorizontalDivider(color = DividerColor)

            OverviewRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.PhoneAndroid,
                        contentDescription = null,
                        tint = Color(0xFF9EB8FF),
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = "Screen Size:",
                value = "6.5 inches"
            )

            HorizontalDivider(color = DividerColor)

            OverviewRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF66E7B1),
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = "Mode:",
                value = "Offline"
            )
        }
    }
}

@Composable
private fun OverviewRow(
    icon: @Composable () -> Unit,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = label,
            color = SecondaryText,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = value,
            color = WhiteText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}