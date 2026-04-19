package com.pharma.link.app.ui.screens.pharmacy_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pharma.link.app.ui.components.PharmaBottomNavBar
import com.pharma.link.app.ui.theme.BackgroundColor
import com.pharma.link.app.ui.theme.PharmaLinkBlue
import com.pharma.link.app.ui.theme.PrimaryDarkText
import com.pharma.link.app.ui.theme.TabTextOff
import com.pharma.link.app.viewmodel.PharmacyViewModel

@Composable
fun PharmacyListScreen(
    viewModel: PharmacyViewModel,
    onAddPharmacyClick: () -> Unit
) {
    val pharmacies by viewModel.pharmacies.collectAsState()
    var selectedFilter by remember { mutableStateOf("ALL") }
    var currentRoute by remember { mutableStateOf("pharmacies") }

    // فلترة القائمة حسب الـ chip المختار
    val filteredPharmacies = remember(pharmacies, selectedFilter) {
        if (selectedFilter == "ALL") pharmacies
        else pharmacies.filter { it.status == selectedFilter }
    }

    Scaffold(
        containerColor = BackgroundColor,
        bottomBar = {
            PharmaBottomNavBar(
                currentRoute = currentRoute,
                onItemClick = { currentRoute = it },
                onFabClick = onAddPharmacyClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // ── Top Bar ──────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundColor)
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp)
            ) {
                // العنوان + الأيقونات
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
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Pharmacies",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDarkText
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        TopIconButton(icon = Icons.Default.Search, onClick = {})
                        TopIconButton(icon = Icons.Default.SwapVert, onClick = {})
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Search Bar
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFEBF2FA)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = TabTextOff,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Search...",
                            maxLines = 1,
                            fontSize = 14.sp,

                            color = TabTextOff
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

            // ── Filter Chips ─────────────────────────
            Spacer(modifier = Modifier.height(8.dp))
            PharmacyFilterChips(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            // ── عداد الصيدليات ───────────────────────
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredPharmacies.size}Registered pharmacy",
                    fontSize = 13.sp,
                    color = TabTextOff,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Explore Map",
                    fontSize = 12.sp,
                    color = PharmaLinkBlue,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ── LazyColumn ───────────────────────────
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(
                    items = filteredPharmacies,
                    key = { it.pharmacyId }
                ) { pharmacy ->
                    PharmacyCard(
                        pharmacy = pharmacy,
                        onEditClick = { /* هنفعله بعدين */ },
                        onMoreClick = { /* هنفعله بعدين */ }
                    )
                }
            }
        }
    }
}

// ── Composable صغير لأيقونات الـ Top Bar ──
@Composable
fun TopIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color(0xFFEBF2FA)),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(38.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PharmaLinkBlue,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}