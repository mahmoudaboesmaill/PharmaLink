package com.pharma.link.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pharma.link.app.ui.screens.add_pharmacy.AddPharmacyScreen
import com.pharma.link.app.ui.screens.edit_pharmacy.EditPharmacyScreen
import com.pharma.link.app.ui.screens.pharmacy_detail.PharmacyDetailScreen
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
                viewModel          = viewModel,
                onAddPharmacyClick = { navController.navigate(Screen.AddPharmacy) },
                onPharmacyClick    = { pharmacyId ->
                    navController.navigate(Screen.PharmacyDetail(pharmacyId))
                },
                onEditPharmacyClick = { pharmacyId ->
                    navController.navigate(Screen.EditPharmacy(pharmacyId))
                }
            )
        }

        composable<Screen.AddPharmacy> {
            val viewModel: PharmacyViewModel = hiltViewModel()
            AddPharmacyScreen(
                viewModel = viewModel,
                onBack    = { navController.popBackStack() }
            )
        }

        composable<Screen.PharmacyDetail> { backStackEntry ->
            val screen    = backStackEntry.toRoute<Screen.PharmacyDetail>()
            val viewModel: PharmacyViewModel = hiltViewModel()
            PharmacyDetailScreen(
                pharmacyId  = screen.pharmacyId,
                viewModel   = viewModel,
                onBack      = { navController.popBackStack() },
                onEditClick = { pharmacyId ->
                    navController.navigate(Screen.EditPharmacy(pharmacyId))
                }
            )
        }

        composable<Screen.EditPharmacy> { backStackEntry ->
            val screen    = backStackEntry.toRoute<Screen.EditPharmacy>()
            val viewModel: PharmacyViewModel = hiltViewModel()
            EditPharmacyScreen(
                pharmacyId = screen.pharmacyId,
                viewModel  = viewModel,
                onBack     = { navController.popBackStack() }
            )
        }
    }
}