@file:Suppress("FunctionName")

package com.algorithm.touchscreentestlab.ui.compose


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
