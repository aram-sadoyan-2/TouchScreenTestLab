@file:Suppress("FunctionName")

package com.algorithm.touchscreentestlab.ui.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.touchlab.ui.QuickTestScreen
import com.example.touchlab.ui.TouchLabHomeScreen

private object TouchLabRoutes {
    const val Home = "home"
    const val QuickTest = "quick_test"
    const val MultiTouch = "multi_touch"
    const val DrawTest = "draw_test"
    const val EdgeTest = "edge_test"
}

@Composable
fun TouchLabApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TouchLabRoutes.Home
    ) {
        composable(TouchLabRoutes.Home) {
            TouchLabHomeScreen(
                onQuickTestClick = { navController.navigate(TouchLabRoutes.QuickTest) },
                onMultiTouchClick = { navController.navigate(TouchLabRoutes.MultiTouch) },
                onDrawTestClick = { navController.navigate(TouchLabRoutes.DrawTest) },
                onEdgeTestClick = { navController.navigate(TouchLabRoutes.EdgeTest) }
            )
        }

        composable(TouchLabRoutes.QuickTest) {
            QuickTestScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(TouchLabRoutes.MultiTouch) {
            MultiTouchTestScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(TouchLabRoutes.DrawTest) {
            DrawTestScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(TouchLabRoutes.EdgeTest) {
            EdgeTestScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF060B22),
                        Color(0xFF091532),
                        Color(0xFF040817)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$title\nComing next...",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}