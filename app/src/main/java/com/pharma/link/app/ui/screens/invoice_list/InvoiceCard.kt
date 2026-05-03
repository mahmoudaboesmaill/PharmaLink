package com.pharma.link.app.ui.screens.invoice_list

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
import com.pharma.link.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

// ── ألوان الفاتورة حسب الحالة ──────────────
data class InvoiceCardColors(
    val accentLine: Color,
    val badgeBg: Color,
    val badgeText: Color,
    val badgeLabel: String,
    val amountColor: Color,
)

fun getInvoiceColors(status: String): InvoiceCardColors {
    return when (status) {
        "PAID" -> InvoiceCardColors(
            accentLine  = StatusActiveText,
            badgeBg     = StatusActiveBg,
            badgeText   = StatusActiveText,
            badgeLabel  = "Paid",
            amountColor = StatusActiveText,
        )
        "OVERDUE" -> InvoiceCardColors(
            accentLine  = Color(0xFFD32F2F),
            badgeBg     = Color(0xFFFFEBEE),
            badgeText   = Color(0xFFD32F2F),
            badgeLabel  = "Overdue",
            amountColor = Color(0xFFD32F2F),
        )
        else -> InvoiceCardColors( // PENDING
            accentLine  = PharmaLinkBlue,
            badgeBg     = Color(0xFFEBF2FA),
            badgeText   = PharmaLinkBlue,
            badgeLabel  = "Pending",
            amountColor = PharmaLinkBlue,
        )
    }
}

@Composable
fun InvoiceCard(
    invoice: InvoiceEntity,
    pharmacyName: String = "",
    onClick: () -> Unit,
    onDeleteClick: () -> Unit = {},
) {
    val colors = getInvoiceColors(invoice.status)
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDropdown     by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Invoice") },
            text  = { Text("Are you sure you want to delete Invoice #${invoice.invoiceId}?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDeleteClick()
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Card(
        modifier  = Modifier.fillMaxWidth(),
        onClick   = onClick,
        shape     = RoundedCornerShape(18.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        border    = androidx.compose.foundation.BorderStroke(
            0.5.dp, Color(0xFFD6E6F5)
        )
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
                    .padding(14.dp)
            ) {
                // الصف الأول: ID + Badge + More
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Invoice ID
                    Text(
                        text = "Invoice #${invoice.invoiceId}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDarkText
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Status Badge
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = colors.badgeBg
                        ) {
                            Text(
                                text = colors.badgeLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.badgeText,
                                modifier = Modifier.padding(
                                    horizontal = 8.dp, vertical = 2.dp
                                )
                            )
                        }
                        // More button
                        Box {
                            IconButton(
                                onClick = { showDropdown = true },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = null,
                                    tint = SecondaryGrayText,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = showDropdown,
                                onDismissRequest = { showDropdown = false }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "Delete",
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    },
                                    onClick = {
                                        showDropdown     = false
                                        showDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // اسم الصيدلية
                if (pharmacyName.isNotBlank()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Default.LocalPharmacy,
                            contentDescription = null,
                            tint = SecondaryGrayText,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = pharmacyName,
                            fontSize = 13.sp,
                            color = SecondaryGrayText
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // تاريخ الاستحقاق
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = SecondaryGrayText,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "Due: ${dateFormat.format(Date(invoice.dueDate))}",
                        fontSize = 13.sp,
                        color = SecondaryGrayText
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFEBF2FA), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(8.dp))

                // المبلغ
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Total Amount",
                            fontSize = 11.sp,
                            color = SecondaryGrayText
                        )
                        Text(
                            text = "${invoice.totalAmount} L.E.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.amountColor
                        )
                    }
                    Text(
                        text = dateFormat.format(Date(invoice.date)),
                        fontSize = 12.sp,
                        color = TabTextOff
                    )
                }
            }
        }
    }
}