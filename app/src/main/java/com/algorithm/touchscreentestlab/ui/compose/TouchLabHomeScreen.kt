@file:Suppress("FunctionName")

package com.algorithm.touchscreentestlab.ui.compose

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.algorithm.touchscreentestlab.R

private val ScreenBgTop = Color(0xFF060B22)
private val ScreenBgMiddle = Color(0xFF091532)
private val ScreenBgBottom = Color(0xFF040817)

private val CyanGlow = Color(0xFF43DBFF)
private val PurpleGlow = Color(0xFF9A56FF)

private val WhiteText = Color(0xFFF8FBFF)
private val SecondaryText = Color(0xFFAEC2F2)
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

            Spacer(modifier = Modifier.height(12.dp))

            HeroCard()

            Spacer(modifier = Modifier.height(18.dp))

            ActionCard(
                title = stringResource(R.string.quick_test),
                subtitle = stringResource(R.string.quick_test_subtitle),
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
                title = stringResource(R.string.multi_touch_test),
                subtitle = stringResource(R.string.multi_touch_test_subtitle),
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
                title = stringResource(R.string.draw_test),
                subtitle = stringResource(R.string.draw_test_subtitle),
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
                title = stringResource(R.string.edge_test),
                subtitle = stringResource(R.string.edge_test_subtitle),
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
            .padding(top = 2.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF6A37FF),
                            Color(0xFF3DD8FF),
                            Color(0xFF2F83FF)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.TouchApp,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(R.string.home_title),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.home_subtitle),
                color = Color(0xFF53D9FF),
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 10.sp
            )
        }
    }
}

@Composable
private fun HeroCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF18488C),
                        Color(0xFF315CB5),
                        Color(0xFF5C4EAF)
                    )
                )
            )
            .border(
                width = 1.5.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF67D8FF).copy(alpha = 0.55f),
                        Color(0xFF7C7CFF).copy(alpha = 0.24f),
                        Color(0xFFFF87F2).copy(alpha = 0.35f)
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            )
    ) {
        HeroSimpleBackgroundSmall()

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
        ) {
            HeroPhonePreviewCompactSmall()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(84.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF17356C),
                            Color(0xFF112B5B),
                            Color(0xFF0D234C)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.hero_title),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 15.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.hero_subtitle),
                    color = Color(0xFFAED6FF),
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 9.sp
                )
            }
        }
    }
}

@Composable
private fun HeroSimpleBackgroundSmall() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 34.dp, top = 58.dp)
                .size(5.dp)
                .clip(CircleShape)
                .background(Color(0xFFB6E5FF).copy(alpha = 0.75f))
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 58.dp, bottom = 30.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(Color(0xFFA8DFFF).copy(alpha = 0.72f))
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 30.dp, top = 72.dp)
                .size(width = 30.dp, height = 9.dp)
                .rotate(-40f)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF8EDFFF).copy(alpha = 0.18f),
                            Color(0xFF8EDFFF).copy(alpha = 0.70f)
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 42.dp, top = 2.dp)
                .size(4.dp)
                .clip(CircleShape)
                .background(Color(0xFFB2DEFF).copy(alpha = 0.75f))
        )
    }
}

@Composable
private fun HeroPhonePreviewCompactSmall() {
    Box(
        modifier = Modifier
            .width(112.dp)
            .height(142.dp)
            .graphicsLayer {
                rotationX = 12f
                rotationZ = 0f
                cameraDistance = 24 * density
            }
    ) {
        Box(modifier = Modifier.matchParentSize()) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-2).dp, y = (-16).dp)
                    .width(3.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF162042))
            )

            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-2).dp, y = 0.dp)
                    .width(3.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF162042))
            )

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 2.dp, y = (-6).dp)
                    .width(3.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF162042))
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xFF050D1F))
                    .border(
                        width = 1.5.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF39DEFF).copy(alpha = 0.92f),
                                Color(0xFF225DCC).copy(alpha = 0.35f),
                                Color(0xFFFF4FDF).copy(alpha = 0.55f)
                            )
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(horizontal = 5.dp, vertical = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF061428),
                                    Color(0xFF0A1D38),
                                    Color(0xFF07162D)
                                )
                            )
                        )
                ) {
                    HeroPhoneGridCompactSmall()

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 6.dp)
                            .width(24.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color(0xFF23324D))
                    )

                    HeroTouchPointCompactSmall(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 14.dp, end = 8.dp),
                        outer = 14.dp,
                        inner = 7.dp
                    )

                    HeroTouchPointCompactSmall(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 8.dp, bottom = 12.dp),
                        outer = 22.dp,
                        inner = 11.dp
                    )

                    HeroTouchPointCompactSmall(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 4.dp, start = 2.dp),
                        outer = 16.dp,
                        inner = 8.dp
                    )

                    HeroTouchPointCompactSmall(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 9.dp, top = 1.dp),
                        outer = 26.dp,
                        inner = 13.dp
                    )

                    HeroTouchPointCompactSmall(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 14.dp, start = 1.dp),
                        outer = 18.dp,
                        inner = 9.dp
                    )

                    HeroTouchPointCompactSmall(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 8.dp, bottom = 5.dp),
                        outer = 14.dp,
                        inner = 7.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun HeroPhoneGridCompactSmall() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        val cols = 7
        val rows = 9
        val dx = size.width / cols
        val dy = size.height / rows

        for (i in 0..cols) {
            drawLine(
                color = Color(0xFF6FD8FF).copy(alpha = 0.48f),
                start = Offset(i * dx, 0f),
                end = Offset(i * dx, size.height),
                strokeWidth = 0.8.dp.toPx()
            )
        }

        for (j in 0..rows) {
            drawLine(
                color = Color(0xFF6FD8FF).copy(alpha = 0.48f),
                start = Offset(0f, j * dy),
                end = Offset(size.width, j * dy),
                strokeWidth = 0.8.dp.toPx()
            )
        }
    }
}

@Composable
private fun HeroTouchPointCompactSmall(
    modifier: Modifier,
    outer: Dp,
    inner: Dp
) {
    Box(
        modifier = modifier
            .size(outer)
            .clip(CircleShape)
            .background(Color(0xFF42E6FF).copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(outer * 0.72f)
                .clip(CircleShape)
                .background(Color(0xFF42E6FF).copy(alpha = 0.18f)),
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
private fun ActionCard(
    title: String,
    subtitle: String,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                text = stringResource(R.string.device_overview),
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
                label = stringResource(R.string.max_touches_label),
                value = stringResource(R.string.max_touches_value)
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
                label = stringResource(R.string.last_test_label),
                value = stringResource(R.string.last_test_value)
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
                label = stringResource(R.string.screen_size_label),
                value = stringResource(R.string.screen_size_value)
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
                label = stringResource(R.string.mode_label),
                value = stringResource(R.string.mode_value)
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