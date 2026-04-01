package com.algorithm.touchscreentestlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.algorithm.touchscreentestlab.ui.compose.TouchLabApp
import com.algorithm.touchscreentestlab.ui.theme.TouchScreenTestLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TouchScreenTestLabTheme {
                TouchLabApp()
            }
        }
    }
}