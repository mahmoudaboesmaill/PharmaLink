package com.pharma.link.app.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pharma.link.app.ui.screens.pharmacy_list.PharmacyListScreen
import com.pharma.link.app.viewmodel.PharmacyViewModel


@Composable
fun PharmaNavHost() {
    // 1. تعريف الدليل (الـ NavController)
    val navController = rememberNavController()

    // 2. بناء الخريطة

    NavHost(
        navController = navController,
        startDestination = Screen.PharmacyList// أول شاشة هتفتح
    ) {
        // تعريف وجهة "قائمة الصيدليات"
        composable<Screen.PharmacyList> {
            val viewModel: PharmacyViewModel = hiltViewModel()
            PharmacyListScreen(
                viewModel = viewModel,
                onAddPharmacyClick = {
                    // لما المندوب يدوس إضافة، نوديه لشاشة الإضافة
                    navController.navigate(Screen.AddPharmacy)
                }
            )
        }

        // تعريف وجهة "إضافة صيدلية"
        composable<Screen.AddPharmacy> {
            // هنا هنحط شاشة الإضافة اللي لسه هنصممها
            // AddPharmacyScreen(onBack = { navController.popBackStack() })
            // حالياً هنحط نص مؤقت لحد ما نصمم الشاشة
            Text("شاشة إضافة صيدلية جديدة (قيد الإنشاء)")
        }
    }
}