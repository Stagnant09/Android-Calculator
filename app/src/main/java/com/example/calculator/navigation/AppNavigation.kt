package com.example.calculator.navigation

import androidx.navigation.NavController

fun NavController.navigateTo(route: AppRoute) {
    navigate(route) // ⚠️ notice — no .route string, direct object!
}
