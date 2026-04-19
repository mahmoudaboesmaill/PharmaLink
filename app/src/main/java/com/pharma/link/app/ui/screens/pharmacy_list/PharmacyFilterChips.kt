package com.pharma.link.app.ui.screens.pharmacy_list

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pharma.link.app.ui.theme.*

@Composable
fun PharmacyFilterChips(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf(
        "ALL"         to "All",
        "ACTIVE"      to "Active",
        "HIGH_DEBT"   to "High Debt",
        "SUSPENDED"   to "Suspended"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { (key, label) ->
            val isSelected = selectedFilter == key
            Surface(
                onClick = { onFilterSelected(key) },
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) PharmaLinkBlue else TabBackgroundOff,
                tonalElevation = 0.dp
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) TabTextOn else TabTextOff,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PharmacyFilterChipsPreview(){
    PharmacyFilterChips("HIGH_DEBT") { }
}