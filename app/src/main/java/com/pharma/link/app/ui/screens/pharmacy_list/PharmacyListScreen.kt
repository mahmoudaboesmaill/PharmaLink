package com.pharma.link.app.ui.screens.pharmacy_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pharma.link.app.ui.components.PharmaBottomNavBar
import com.pharma.link.app.ui.theme.*
import com.pharma.link.app.viewmodel.PharmacyViewModel

@Composable
fun PharmacyListScreen(
    viewModel: PharmacyViewModel,
    onAddPharmacyClick: () -> Unit,
    onPharmacyClick: (String) -> Unit = {}
) {
    // كل الداتا جاية من الـ ViewModel مباشرة
    val filteredPharmacies by viewModel.filteredPharmacies.collectAsState()
    val searchQuery        by viewModel.searchQuery.collectAsState()
    val selectedFilter     by viewModel.selectedFilter.collectAsState()
    var currentRoute       by remember { mutableStateOf("pharmacies") }
    val focusManager       = LocalFocusManager.current

    Scaffold(
        containerColor = BackgroundColor,
        bottomBar = {
            PharmaBottomNavBar(
                currentRoute = currentRoute,
                onItemClick  = { currentRoute = it },
                onFabClick   = onAddPharmacyClick
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

                // ── Search Bar حقيقي ─────────────────
                TextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    placeholder = {
                        Text(
                            text = "Search for a pharmacy...",
                            fontSize = 14.sp,
                            color = TabTextOff
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
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

            // ── Filter Chips ─────────────────────────
            Spacer(modifier = Modifier.height(8.dp))
            PharmacyFilterChips(
                selectedFilter   = selectedFilter,
                onFilterSelected = { viewModel.onFilterSelected(it) }
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
                    text = "${filteredPharmacies.size} Registered pharmacies",
                    fontSize = 13.sp,
                    color = TabTextOff,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "View Map",
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
                    key   = { it.pharmacyId }
                ) { pharmacy ->
                    PharmacyCard(
                        pharmacy    = pharmacy,
                        onClick     = { onPharmacyClick(pharmacy.pharmacyId) },
                        onEditClick = { /* هنفعله بعدين */ },
                        onMoreClick = { /* هنفعله بعدين */ }
                    )
                }
            }
        }
    }
}

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