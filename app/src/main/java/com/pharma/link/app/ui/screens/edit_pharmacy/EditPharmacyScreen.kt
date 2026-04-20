package com.pharma.link.app.ui.screens.edit_pharmacy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pharma.link.app.data.entities.PharmacyEntity
import com.pharma.link.app.viewmodel.PharmacyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPharmacyScreen(
    pharmacyId: String,
    viewModel: PharmacyViewModel,
    onBack: () -> Unit
) {
    // جيب الداتا القديمة
    var pharmacy by remember { mutableStateOf<PharmacyEntity?>(null) }

    LaunchedEffect(pharmacyId) {
        pharmacy = viewModel.getPharmacyById(pharmacyId)
    }

    pharmacy?.let { p ->

        var name              by remember { mutableStateOf(p.name) }
        var address           by remember { mutableStateOf(p.address) }
        var phone             by remember { mutableStateOf(p.phone) }
        var email             by remember { mutableStateOf(p.email) }
        var licenceNumber     by remember { mutableStateOf(p.licenceNumber) }
        var selectedStatus    by remember { mutableStateOf(p.status) }
        var statusDropdownOpen by remember { mutableStateOf(false) }

        val statusOptions = listOf(
            "ACTIVE"    to "Active",
            "SUSPENDED" to "Suspended",
            "HIGH_DEBT" to "High Debt"
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Edit Pharmacy") },
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

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Pharmacy Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = licenceNumber,
                    onValueChange = { licenceNumber = it },
                    label = { Text("Licence Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // ── Status Dropdown ───────────────
                ExposedDropdownMenuBox(
                    expanded = statusDropdownOpen,
                    onExpandedChange = { statusDropdownOpen = it }
                ) {
                    OutlinedTextField(
                        value = statusOptions.find {
                            it.first == selectedStatus
                        }?.second ?: "Active",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = statusDropdownOpen
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = statusDropdownOpen,
                        onDismissRequest = { statusDropdownOpen = false }
                    ) {
                        statusOptions.forEach { (key, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    selectedStatus     = key
                                    statusDropdownOpen = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.updatePharmacy(
                            p.copy(
                                name          = name,
                                address       = address,
                                phone         = phone,
                                email         = email,
                                licenceNumber = licenceNumber,
                                status        = selectedStatus
                            )
                        )
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotBlank() && address.isNotBlank()
                ) {
                    Text("Save Changes")
                }
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}