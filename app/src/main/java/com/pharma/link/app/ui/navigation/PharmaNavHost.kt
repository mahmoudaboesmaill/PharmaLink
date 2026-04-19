package com.pharma.link.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pharma.link.app.ui.screens.add_pharmacy.AddPharmacyScreen
import com.pharma.link.app.ui.screens.pharmacy_list.PharmacyListScreen
import com.pharma.link.app.viewmodel.PharmacyViewModel

@Composable
fun PharmaNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PharmacyList
    ) {
        composable<Screen.PharmacyList> {
            val viewModel: PharmacyViewModel = hiltViewModel()
            PharmacyListScreen(
                viewModel = viewModel,
                onAddPharmacyClick = {
                    navController.navigate(Screen.AddPharmacy)
                }
            )
        }

        composable<Screen.AddPharmacy> {
            val viewModel: PharmacyViewModel = hiltViewModel()
            AddPharmacyScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}