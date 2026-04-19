package com.pharma.link.app.ui.screens.pharmacy_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pharma.link.app.data.entities.PharmacyEntity
import com.pharma.link.app.ui.theme.*

// ─────────────────────────────────────────
// Data class صغيرة بتحمل ألوان الكارت
// حسب الـ status
// ─────────────────────────────────────────
data class PharmacyCardColors(
    val accentLine: Color,
    val iconBg: Color,
    val iconTint: Color,
    val badgeBg: Color,
    val badgeText: Color,
    val badgeLabel: String,
    val balanceColor: Color,
    val actionBg: Color,
    val actionIcon: Color,
)

// ─────────────────────────────────────────
// الخطوة 5 — دالة بترجع الألوان الصح
// حسب الـ status بتاع الصيدلية
// ─────────────────────────────────────────
fun getCardColors(status: String): PharmacyCardColors {
    return when (status) {
        "ACTIVE" -> PharmacyCardColors(
            accentLine   = PharmaLinkBlue,
            iconBg       = Color(0xFFEBF2FA),
            iconTint     = PharmaLinkBlue,
            badgeBg      = StatusActiveBg,
            badgeText    = StatusActiveText,
            badgeLabel   = "Active",
            balanceColor = PharmaLinkBlue,
            actionBg     = Color(0xFFEBF2FA),
            actionIcon   = PharmaLinkBlue,
        )
        "HIGH_DEBT" -> PharmacyCardColors(
            accentLine   = DebtAccentLine,
            iconBg       = Color(0xFFFDF3E3),
            iconTint     = StatusDebtText,
            badgeBg      = StatusDebtBg,
            badgeText    = StatusDebtText,
            badgeLabel   = "High Debt",
            balanceColor = StatusDebtText,
            actionBg     = Color(0xFFFDF3E3),
            actionIcon   = StatusDebtText,
        )
        else -> PharmacyCardColors( // SUSPENDED
            accentLine   = Color(0xFF9AAFC0),
            iconBg       = Color(0xFFEBF0F5),
            iconTint     = Color(0xFF6B8399),
            badgeBg      = Color(0xFFE8EDF2),
            badgeText    = StatusPausedText,
            badgeLabel   = "Suspended",
            balanceColor = SecondaryGrayText,
            actionBg     = Color(0xFFE8EDF2),
            actionIcon   = Color(0xFF6B8399),
        )
    }
}

// ─────────────────────────────────────────
// الخطوة 1 و 2 — الكارت الرئيسي
// ─────────────────────────────────────────
@Composable
fun PharmacyCard(
    pharmacy: PharmacyEntity,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onMoreClick: () -> Unit,
) {
    val colors = getCardColors(pharmacy.status)

    // الخطوة 2 — الكارت الأبيض بالـ border
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 0.5.dp,
            color = Color(0xFFD6E6F5)
        )
    ) {
        // الخطوة 3 — Row بيحتوي الخط الجانبي + المحتوى
        Row(modifier = Modifier.fillMaxWidth()
            .height(IntrinsicSize.Min)) {

            // الخط الجانبي الملون
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(colors.accentLine)
            )

            // الخطوة 4 — المحتوى الداخلي
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                // الصف الأول: الأيقونة + الاسم + الـ ID
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // أيقونة الصيدلية
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(colors.iconBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = colors.iconTint,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // الاسم + الـ Badge
                        Column {
                            Text(
                                text = pharmacy.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDarkText
                            )
                            // الخطوة 5 — Status Badge
                            StatusBadge(colors = colors)
                        }
                    }

                    // الـ ID
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFEBF2FA)
                    ) {
                        Text(
                            text = "#${pharmacy.pharmacyId.take(6).uppercase()}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PharmaLinkBlue,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // العنوان
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = SecondaryGrayText,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = pharmacy.address,
                        fontSize = 13.sp,
                        color = SecondaryGrayText
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // التليفون
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = SecondaryGrayText,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = pharmacy.phone,
                        fontSize = 13.sp,
                        color = SecondaryGrayText
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFFEBF2FA), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(10.dp))

                // الرصيد + أزرار الأكشن
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // الرصيد
                    Column {
                        Text(
                            text = "Available Balance",
                            fontSize = 11.sp,
                            color = SecondaryGrayText
                        )
                        Text(
                            text = "0 L.E.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.balanceColor
                        )
                    }

                    // أزرار الأكشن
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ActionButton(
                            icon = Icons.Default.Phone,
                            bg = colors.actionBg,
                            tint = colors.actionIcon,
                            onClick = {}
                        )
                        ActionButton(
                            icon = Icons.Default.Edit,
                            bg = colors.actionBg,
                            tint = colors.actionIcon,
                            onClick = onEditClick
                        )
                        ActionButton(
                            icon = Icons.Default.MoreVert,
                            bg = colors.actionBg,
                            tint = colors.actionIcon,
                            onClick = onMoreClick
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────
// Composable صغير للـ Badge
// ─────────────────────────────────────────
@Composable
fun StatusBadge(colors: PharmacyCardColors) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = colors.badgeBg
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(colors.badgeText)
            )
            Text(
                text = colors.badgeLabel,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.badgeText
            )
        }
    }
}

// ─────────────────────────────────────────
// Composable صغير لأزرار الأكشن
// ─────────────────────────────────────────
@Composable
fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    bg: Color,
    tint: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(34.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PharmacyCardPreview() {
    PharmacyCard(pharmacy = PharmacyEntity(pharmacyId = "1",
        name = "Doaa",
        address = "abo",
        phone = "0123456789",
        status = "HIGH_DEBT",
        email = "mahmoudaboesmaill1991@gmail.com",
        licenceNumber = "1111100000"),
        onEditClick = { },
        onMoreClick = { },
        onClick = { })
}

