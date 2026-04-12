package com.pharma.link.app.ui.screens.add_pharmacy

import com.pharma.link.app.viewmodel.PharmacyViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPharmacyScreen(
    viewModel: PharmacyViewModel,
    onBack: () -> Unit
) {
    // تعريف متغيرات الـ State لكل Input
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إضافة صيدلية جديدة") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "رجوع")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("اسم الصيدلية") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("العنوان") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("رقم التليفون") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (name.isNotBlank() && address.isNotBlank()) {
                        viewModel.addPharmacy(name, address, phone)
                        onBack() // ارجع للشاشة اللي فاتت بعد الحفظ
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() // الزرار مبيشتغلش غير لما يكتب الاسم
            ) {
                Text("حفظ الصيدلية")
            }
        }
    }
}