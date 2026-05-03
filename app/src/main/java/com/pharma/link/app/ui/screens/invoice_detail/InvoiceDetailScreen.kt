package com.pharma.link.app.ui.screens.invoice_detail

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pharma.link.app.data.entities.InvoiceEntity
import com.pharma.link.app.ui.screens.invoice_list.getInvoiceColors
import com.pharma.link.app.ui.theme.*
import com.pharma.link.app.viewmodel.InvoiceViewModel
import com.pharma.link.app.viewmodel.PharmacyViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceDetailScreen(
    invoiceId: Int,
    invoiceViewModel: InvoiceViewModel,
    pharmacyViewModel: PharmacyViewModel,
    onBack: () -> Unit
) {
    var invoice     by remember { mutableStateOf<InvoiceEntity?>(null) }
    val pharmacies  by pharmacyViewModel.filteredPharmacies.collectAsState()
    val dateFormat  = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    LaunchedEffect(invoiceId) {
        invoice = invoiceViewModel.getInvoiceById(invoiceId)
    }

    invoice?.let { inv ->
        val colors       = getInvoiceColors(inv.status)
        val pharmacyName = pharmacies.find {
            it.pharmacyId == inv.pharmacyId
        }?.name ?: inv.pharmacyId

        Scaffold(
            containerColor = BackgroundColor,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Invoice #${inv.invoiceId}",
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
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BackgroundColor
                    )
                )
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
                    shape    = RoundedCornerShape(18.dp),
                    colors   = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                    ) {
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
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Status Badge
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Invoice #${inv.invoiceId}",
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

                            // التفاصيل
                            InvoiceDetailRow("Pharmacy",   pharmacyName)
                            InvoiceDetailRow("Date",       dateFormat.format(Date(inv.date)))
                            InvoiceDetailRow("Due Date",   dateFormat.format(Date(inv.dueDate)))
                            if (!inv.notes.isNullOrBlank()) {
                                InvoiceDetailRow("Notes", inv.notes)
                            }
                        }
                    }
                }

                // ── Amount Card ───────────────────────
                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(18.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Total Amount",
                            fontSize = 13.sp,
                            color = TabTextOff
                        )
                        Text(
                            text = "${inv.totalAmount} L.E.",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.amountColor
                        )
                    }
                }

                // ── Action Buttons ────────────────────
                if (inv.status == "PENDING") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                invoiceViewModel.updateInvoiceStatus(inv, "PAID")
                                onBack()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = StatusActiveText
                            )
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Mark as Paid")
                        }

                        OutlinedButton(
                            onClick = {
                                invoiceViewModel.updateInvoiceStatus(inv, "OVERDUE")
                                onBack()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Mark Overdue", color = Color(0xFFD32F2F))
                        }
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
private fun InvoiceDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = TabTextOff)
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = PrimaryDarkText
        )
    }
}