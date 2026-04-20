package com.pharma.link.app.ui.screens.pharmacy_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pharma.link.app.data.entities.PharmacyEntity
import com.pharma.link.app.ui.screens.pharmacy_list.getCardColors
import com.pharma.link.app.ui.theme.*
import com.pharma.link.app.viewmodel.PharmacyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmacyDetailScreen(
    pharmacyId: String,
    viewModel: PharmacyViewModel,
    onBack: () -> Unit,
    onEditClick: (String) -> Unit = {}
) {
    // جيب الصيدلية من الـ ViewModel
    var pharmacy by remember { mutableStateOf<PharmacyEntity?>(null) }

    LaunchedEffect(pharmacyId) {
        pharmacy = viewModel.getPharmacyById(pharmacyId)
    }

    pharmacy?.let { p ->
        val colors = getCardColors(p.status)

        Scaffold(
            containerColor = BackgroundColor,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = p.name,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDarkText
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = PharmaLinkBlue
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { onEditClick(pharmacyId) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = PharmaLinkBlue
                            )
                        }
                    })
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // ── Header Card ───────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                    ) {
                        // الخط الجانبي
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .fillMaxHeight()
                                .background(colors.accentLine)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // الاسم + الـ Badge
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = p.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryDarkText
                                )
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = colors.badgeBg
                                ) {
                                    Text(
                                        text = colors.badgeLabel,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = colors.badgeText,
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp, vertical = 4.dp
                                        )
                                    )
                                }
                            }

                            HorizontalDivider(color = Color(0xFFEBF2FA))

                            // تفاصيل
                            DetailRow(Icons.Default.LocationOn, p.address)
                            DetailRow(Icons.Default.Phone,      p.phone)
                            DetailRow(Icons.Default.Email,      p.email)
                            DetailRow(Icons.Default.Badge,      p.licenceNumber)
                        }
                    }
                }

                // ── Balance Card ──────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Outstanding Balance",
                            fontSize = 13.sp,
                            color = TabTextOff
                        )
                        Text(
                            text = "0 L.E.",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.balanceColor
                        )
                    }
                }

                // ── Action Buttons ────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { /* هنفعله بعدين */ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PharmaLinkBlue
                        )
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("New Invoice")
                    }

                    OutlinedButton(
                        onClick = { /* هنفعله بعدين */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.History, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("History")
                    }
                }
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PharmaLinkBlue)
    }
}

@Composable
private fun DetailRow(icon: ImageVector, value: String) {
    if (value.isBlank()) return
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TabTextOff,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = SecondaryGrayText
        )
    }
}

