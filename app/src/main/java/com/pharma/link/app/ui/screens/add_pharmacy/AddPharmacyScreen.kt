package com.pharma.link.app.ui.screens.add_pharmacy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pharma.link.app.viewmodel.AddPharmacyResult
import com.pharma.link.app.viewmodel.PharmacyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPharmacyScreen(
    viewModel: PharmacyViewModel,
    onBack: () -> Unit
) {
    var name              by remember { mutableStateOf("") }
    var address           by remember { mutableStateOf("") }
    var phone             by remember { mutableStateOf("") }
    var email             by remember { mutableStateOf("") }
    var licenceNumber     by remember { mutableStateOf("") }
    var selectedStatus    by remember { mutableStateOf("ACTIVE") }
    var statusDropdownOpen by remember { mutableStateOf(false) }

    // ── Error States ─────────────────────────────
    var phoneError   by remember { mutableStateOf<String?>(null) }
    var emailError   by remember { mutableStateOf<String?>(null) }
    var licenceError by remember { mutableStateOf<String?>(null) }

    val statusOptions = listOf(
        "ACTIVE"    to "Active",
        "SUSPENDED" to "Suspended",
        "HIGH_DEBT" to "High Debt"
    )

    // ── استماع لنتيجة الإضافة من الـ ViewModel ───
    val addResult by viewModel.addResult.collectAsState()

    LaunchedEffect(addResult) {
        when (addResult) {
            is AddPharmacyResult.Success -> {
                viewModel.resetAddResult()
                onBack()
            }
            is AddPharmacyResult.DuplicatePhone -> {
                phoneError = "This phone number is already registered"
                viewModel.resetAddResult()
            }
            is AddPharmacyResult.DuplicateEmail -> {
                emailError = "This email is already registered"
                viewModel.resetAddResult()
            }
            is AddPharmacyResult.DuplicateLicence -> {
                licenceError = "This licence number is already registered"
                viewModel.resetAddResult()
            }
            null -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Pharmacy") },
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

            // ── Name ─────────────────────────────
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Pharmacy Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Address ──────────────────────────
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Phone ────────────────────────────
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = null // امسح الـ error لما يبدأ يكتب
                },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = phoneError != null,
                supportingText = {
                    if (phoneError != null) {
                        Text(
                            text = phoneError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            // ── Email ────────────────────────────
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = emailError != null,
                supportingText = {
                    if (emailError != null) {
                        Text(
                            text = emailError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            // ── Licence Number ───────────────────
            OutlinedTextField(
                value = licenceNumber,
                onValueChange = {
                    licenceNumber = it
                    licenceError = null
                },
                label = { Text("Licence Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = licenceError != null,
                supportingText = {
                    if (licenceError != null) {
                        Text(
                            text = licenceError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            // ── Status Dropdown ──────────────────
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

            // ── Save Button ──────────────────────
            Button(
                onClick = {
                    // امسح كل الـ errors القديمة
                    phoneError   = null
                    emailError   = null
                    licenceError = null

                    viewModel.addPharmacy(
                        name          = name,
                        address       = address,
                        phone         = phone,
                        email         = email,
                        licenceNumber = licenceNumber,
                        status        = selectedStatus
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && address.isNotBlank()
            ) {
                Text("Save Pharmacy")
            }
        }
    }
}