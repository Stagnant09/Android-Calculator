package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.calculator.ui.screens.main.AppNavHost
import com.example.calculator.ui.screens.main.main.MainScreen
import com.example.calculator.ui.screens.main.main.MainScreenViewmodel
import com.example.calculator.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                AppNavHost(
                    navController = rememberNavController(),
                )
            }
        }
    }
}