package com.pharma.link.app.ui.screens.pharmacy_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.pharma.link.app.viewmodel.PharmacyViewModel


@Composable
fun PharmacyListScreen(
    viewModel: PharmacyViewModel,
    onAddPharmacyClick: () -> Unit
) {
    // 1. مراقبة البيانات اللي جاية من الـ ViewModel
    val pharmacies by viewModel.pharmacies.collectAsState()

    // 2. استخدام Scaffold عشان يوفر لنا مكان للـ Button (FAB)
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPharmacyClick) {
                Icon(Icons.Default.Add, contentDescription = "إضافة صيدلية")
            }
        }
    ) { paddingValues ->
        // هنا هنحط الـ LazyColumn اللي بيعرض الصيدليات
        // وهنستخدم الـ paddingValues عشان الداتا متبقاش مستخبية ورا الزرار
        Text(
            text = "قائمة الصيدليات (عددها: ${pharmacies.size})",
            modifier = Modifier.padding(paddingValues)
        )
    }
}
