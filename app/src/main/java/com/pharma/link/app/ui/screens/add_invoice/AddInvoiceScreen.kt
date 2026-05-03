package com.pharma.link.app.ui.screens.add_invoice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pharma.link.app.viewmodel.InvoiceViewModel
import com.pharma.link.app.viewmodel.PharmacyViewModel
import com.pharma.link.app.viewmodel.SalesRepViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInvoiceScreen(
    invoiceViewModel: InvoiceViewModel,
    pharmacyViewModel: PharmacyViewModel,
    salesRepViewModel: SalesRepViewModel,
    onBack: () -> Unit,
    preSelectedPharmacyId: String = ""
) {
    val pharmacies = pharmacyViewModel.filteredPharmacies.collectAsState()
    val salesReps  = salesRepViewModel.salesReps.collectAsState()

    var totalAmount  by remember { mutableStateOf("") }
    var notes        by remember { mutableStateOf("") }
    var dueDays      by remember { mutableStateOf("30") }

    // Pharmacy Dropdown
    var selectedPharmacyId    by remember { mutableStateOf(preSelectedPharmacyId) }
    var pharmacyDropdownOpen  by remember { mutableStateOf(false) }

    // SalesRep Dropdown
    var selectedRepId        by remember { mutableStateOf("") }
    var repDropdownOpen      by remember { mutableStateOf(false) }

    // Error states
    var amountError   by remember { mutableStateOf<String?>(null) }
    var pharmacyError by remember { mutableStateOf<String?>(null) }
    var repError      by remember { mutableStateOf<String?>(null) }

    val selectedPharmacyName = pharmacies.value
        .find { it.pharmacyId == selectedPharmacyId }?.name ?: "Select Pharmacy"

    val selectedRepName = salesReps.value
        .find { it.repId == selectedRepId }?.name ?: "Select Sales Rep"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Invoice") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ── Pharmacy Dropdown ─────────────────
            ExposedDropdownMenuBox(
                expanded = pharmacyDropdownOpen,
                onExpandedChange = { pharmacyDropdownOpen = it }
            ) {
                OutlinedTextField(
                    value = selectedPharmacyName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Pharmacy") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = pharmacyDropdownOpen
                        )
                    },
                    isError = pharmacyError != null,
                    supportingText = {
                        if (pharmacyError != null) {
                            Text(pharmacyError!!, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = pharmacyDropdownOpen,
                    onDismissRequest = { pharmacyDropdownOpen = false }
                ) {
                    if (pharmacies.value.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No pharmacies found") },
                            onClick = { pharmacyDropdownOpen = false }
                        )
                    } else {
                        pharmacies.value.forEach { pharmacy ->
                            DropdownMenuItem(
                                text = { Text(pharmacy.name) },
                                onClick = {
                                    selectedPharmacyId   = pharmacy.pharmacyId
                                    pharmacyError        = null
                                    pharmacyDropdownOpen = false
                                }
                            )
                        }
                    }
                }
            }

            // ── SalesRep Dropdown ─────────────────
            ExposedDropdownMenuBox(
                expanded = repDropdownOpen,
                onExpandedChange = { repDropdownOpen = it }
            ) {
                OutlinedTextField(
                    value = selectedRepName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sales Representative") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = repDropdownOpen)
                    },
                    isError = repError != null,
                    supportingText = {
                        if (repError != null) {
                            Text(repError!!, color = MaterialTheme.colorScheme.error)
                        } else if (salesReps.value.isEmpty()) {
                            Text(
                                "No sales reps found, please add one first",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = repDropdownOpen,
                    onDismissRequest = { repDropdownOpen = false }
                ) {
                    salesReps.value.forEach { rep ->
                        DropdownMenuItem(
                            text = { Text(rep.name) },
                            onClick = {
                                selectedRepId  = rep.repId
                                repError       = null
                                repDropdownOpen = false
                            }
                        )
                    }
                }
            }

            // ── Total Amount ──────────────────────
            OutlinedTextField(
                value = totalAmount,
                onValueChange = {
                    totalAmount  = it
                    amountError  = null
                },
                label = { Text("Total Amount (L.E.)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = amountError != null,
                supportingText = {
                    if (amountError != null) {
                        Text(amountError!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            // ── Due Days ──────────────────────────
            OutlinedTextField(
                value = dueDays,
                onValueChange = { dueDays = it },
                label = { Text("Due in (days)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Notes ─────────────────────────────
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ── Save Button ───────────────────────
            Button(
                onClick = {
                    // Validation
                    var isValid = true
                    if (selectedPharmacyId.isBlank()) {
                        pharmacyError = "Please select a pharmacy"
                        isValid = false
                    }
                    if (selectedRepId.isBlank()) {
                        repError = "Please select a sales representative"
                        isValid = false
                    }
                    if (totalAmount.isBlank() || totalAmount.toLongOrNull() == null) {
                        amountError = "Please enter a valid amount"
                        isValid = false
                    }
                    if (!isValid) return@Button

                    // احسب تاريخ الاستحقاق
                    val calendar = Calendar.getInstance()
                    calendar.add(
                        Calendar.DAY_OF_YEAR,
                        dueDays.toIntOrNull() ?: 30
                    )

                    invoiceViewModel.addInvoice(
                        pharmacyId  = selectedPharmacyId,
                        salesRepId  = selectedRepId,
                        totalAmount = totalAmount.toLong(),
                        dueDate     = calendar.timeInMillis,
                        notes       = notes.ifBlank { null }
                    )
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Invoice")
            }
        }
    }
}

