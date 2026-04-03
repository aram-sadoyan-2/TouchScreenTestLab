package com.algorithm.touchscreentestlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.algorithm.touchscreentestlab.ui.compose.TouchLabApp
import com.algorithm.touchscreentestlab.ui.theme.TouchScreenTestLabTheme
import android.graphics.Color as AndroidColor
import androidx.core.graphics.toColorInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark("#040817".toColorInt())
        )


        setContent {
            TouchScreenTestLabTheme {
                TouchLabApp()
            }
        }
    }
}