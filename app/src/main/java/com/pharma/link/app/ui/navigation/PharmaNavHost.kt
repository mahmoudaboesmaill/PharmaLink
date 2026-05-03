package com.pharma.link.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pharma.link.app.ui.screens.add_invoice.AddInvoiceScreen
import com.pharma.link.app.ui.screens.add_pharmacy.AddPharmacyScreen
import com.pharma.link.app.ui.screens.edit_pharmacy.EditPharmacyScreen
import com.pharma.link.app.ui.screens.invoice_detail.InvoiceDetailScreen
import com.pharma.link.app.ui.screens.invoice_list.InvoiceListScreen
import com.pharma.link.app.ui.screens.pharmacy_detail.PharmacyDetailScreen
import com.pharma.link.app.ui.screens.pharmacy_list.PharmacyListScreen
import com.pharma.link.app.viewmodel.InvoiceViewModel
import com.pharma.link.app.viewmodel.PharmacyViewModel
import com.pharma.link.app.viewmodel.SalesRepViewModel

@Composable
fun PharmaNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PharmacyList
    ) {
        // ── Pharmacy Screens ──────────────────────
        composable<Screen.PharmacyList> {
            val pharmacyVm: PharmacyViewModel = hiltViewModel()
            PharmacyListScreen(
                viewModel           = pharmacyVm,
                onAddPharmacyClick  = { navController.navigate(Screen.AddPharmacy) },
                onPharmacyClick     = { navController.navigate(Screen.PharmacyDetail(it)) },
                onEditPharmacyClick = { navController.navigate(Screen.EditPharmacy(it)) },
                onNavigate          = { route ->
                    when (route) {
                        "invoices" -> navController.navigate(Screen.InvoiceList)
                        else       -> {}
                    }
                }
            )
        }

        composable<Screen.AddPharmacy> {
            val pharmacyVm: PharmacyViewModel = hiltViewModel()
            AddPharmacyScreen(
                viewModel = pharmacyVm,
                onBack    = { navController.popBackStack() }
            )
        }

        composable<Screen.PharmacyDetail> { backStackEntry ->
            val screen     = backStackEntry.toRoute<Screen.PharmacyDetail>()
            val pharmacyVm: PharmacyViewModel = hiltViewModel()
            PharmacyDetailScreen(
                pharmacyId  = screen.pharmacyId,
                viewModel   = pharmacyVm,
                onBack      = { navController.popBackStack() },
                onEditClick = { navController.navigate(Screen.EditPharmacy(it)) },
                onAddInvoiceClick = { navController.navigate(Screen.AddInvoice(preSelectedPharmacyId = it)) }
            )
        }

        composable<Screen.EditPharmacy> { backStackEntry ->
            val screen     = backStackEntry.toRoute<Screen.EditPharmacy>()
            val pharmacyVm: PharmacyViewModel = hiltViewModel()
            EditPharmacyScreen(
                pharmacyId = screen.pharmacyId,
                viewModel  = pharmacyVm,
                onBack     = { navController.popBackStack() }
            )
        }

        // ── Invoice Screens ───────────────────────
        composable<Screen.InvoiceList> {
            val invoiceVm:  InvoiceViewModel  = hiltViewModel()
            val pharmacyVm: PharmacyViewModel = hiltViewModel()
            InvoiceListScreen(
                invoiceViewModel  = invoiceVm,
                pharmacyViewModel = pharmacyVm,
                onAddInvoiceClick = { navController.navigate(Screen.AddInvoice()) },
                onInvoiceClick    = { navController.navigate(Screen.InvoiceDetail(it)) },
                onNavigate        = { route ->
                    when (route) {
                        "pharmacies" -> navController.navigate(Screen.PharmacyList)
                        else         -> {}
                    }
                }
            )
        }

        composable<Screen.AddInvoice> { backStackEntry ->
            val screen     = backStackEntry.toRoute<Screen.AddInvoice>()
            val invoiceVm:  InvoiceViewModel  = hiltViewModel()
            val pharmacyVm: PharmacyViewModel = hiltViewModel()
            val salesRepVm: SalesRepViewModel = hiltViewModel()
            AddInvoiceScreen(
                invoiceViewModel       = invoiceVm,
                pharmacyViewModel      = pharmacyVm,
                salesRepViewModel      = salesRepVm,
                preSelectedPharmacyId  = screen.preSelectedPharmacyId,
                onBack                 = { navController.popBackStack() }
            )
        }

        composable<Screen.InvoiceDetail> { backStackEntry ->
            val screen     = backStackEntry.toRoute<Screen.InvoiceDetail>()
            val invoiceVm:  InvoiceViewModel  = hiltViewModel()
            val pharmacyVm: PharmacyViewModel = hiltViewModel()
            InvoiceDetailScreen(
                invoiceId         = screen.invoiceId,
                invoiceViewModel  = invoiceVm,
                pharmacyViewModel = pharmacyVm,
                onBack            = { navController.popBackStack() }
            )
        }
    }
}