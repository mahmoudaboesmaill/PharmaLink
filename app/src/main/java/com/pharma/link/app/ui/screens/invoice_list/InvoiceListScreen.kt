package com.pharma.link.app.ui.screens.invoice_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pharma.link.app.ui.components.PharmaBottomNavBar
import com.pharma.link.app.ui.theme.*
import com.pharma.link.app.viewmodel.InvoiceViewModel
import com.pharma.link.app.viewmodel.PharmacyViewModel

@Composable
fun InvoiceListScreen(
    invoiceViewModel: InvoiceViewModel,
    pharmacyViewModel: PharmacyViewModel,
    onAddInvoiceClick: () -> Unit,
    onInvoiceClick: (Int) -> Unit,
    onNavigate: (String) -> Unit,
) {
    val filteredInvoices by invoiceViewModel.filteredInvoices.collectAsState()
    val searchQuery      by invoiceViewModel.searchQuery.collectAsState()
    val selectedFilter   by invoiceViewModel.selectedFilter.collectAsState()
    val isLoading        by invoiceViewModel.isLoading.collectAsState()
    val pharmacies       by pharmacyViewModel.filteredPharmacies.collectAsState()
    val focusManager     = LocalFocusManager.current

    // Map عشان نجيب اسم الصيدلية بالـ ID بسرعة
    val pharmacyMap = remember(pharmacies) {
        pharmacies.associate { it.pharmacyId to it.name }
    }

    val filterOptions = listOf(
        "ALL"     to "All",
        "PENDING" to "Pending",
        "PAID"    to "Paid",
        "OVERDUE" to "Overdue"
    )

    Scaffold(
        containerColor = BackgroundColor,
        bottomBar = {
            PharmaBottomNavBar(
                currentRoute = "invoices",
                onItemClick  = onNavigate,
                onFabClick   = onAddInvoiceClick
            )
        }
    ) { paddingValues ->

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PharmaLinkBlue)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {

                // ── TopBar ────────────────────────────
                item(key = "topbar") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BackgroundColor)
                            .padding(horizontal = 20.dp)
                            .padding(top = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "PharmaLink",
                                    fontSize = 12.sp,
                                    color = PharmaLinkBlue,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Invoices",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryDarkText
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            value = searchQuery,
                            onValueChange = { invoiceViewModel.onSearchQueryChange(it) },
                            placeholder = {
                                Text("Search invoices...", fontSize = 14.sp, color = TabTextOff)
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    tint = TabTextOff,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor   = Color(0xFFEBF2FA),
                                unfocusedContainerColor = Color(0xFFEBF2FA),
                                focusedIndicatorColor   = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor  = Color.Transparent
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = { focusManager.clearFocus() }
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                // ── Filter Chips ──────────────────────
                item(key = "filters") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        filterOptions.forEach { (key, label) ->
                            val isSelected = selectedFilter == key
                            Surface(
                                onClick = { invoiceViewModel.onFilterSelected(key) },
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) PharmaLinkBlue else TabBackgroundOff
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isSelected) TabTextOn else TabTextOff,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp, vertical = 6.dp
                                    )
                                )
                            }
                        }
                    }
                }

                // ── Counter ───────────────────────────
                item(key = "counter") {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${filteredInvoices.size} Invoices",
                        fontSize = 13.sp,
                        color = TabTextOff,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // ── Empty State ───────────────────────
                if (filteredInvoices.isEmpty()) {
                    item(key = "empty") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 60.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = "🧾", fontSize = 48.sp)
                                Text(
                                    text = "No invoices found",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = PrimaryDarkText
                                )
                                Text(
                                    text = "Tap + to add a new invoice",
                                    fontSize = 13.sp,
                                    color = TabTextOff
                                )
                            }
                        }
                    }
                }

                // ── Invoice Cards ─────────────────────
                items(
                    items = filteredInvoices,
                    key   = { it.invoiceId ?: 0 }
                ) { invoice ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        InvoiceCard(
                            invoice      = invoice,
                            pharmacyName = pharmacyMap[invoice.pharmacyId] ?: "",
                            onClick      = { onInvoiceClick(invoice.invoiceId ?: 0) },
                            onDeleteClick = {
                                invoiceViewModel.deleteInvoice(invoice)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

