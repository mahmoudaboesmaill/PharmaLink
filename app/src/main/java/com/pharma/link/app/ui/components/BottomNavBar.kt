package com.pharma.link.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pharma.link.app.ui.theme.*

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun PharmaBottomNavBar(
    currentRoute: String,
    onItemClick: (String) -> Unit,
    onFabClick: () -> Unit
) {
    val items = listOf(
        BottomNavItem("Home",       Icons.Default.Home,        "home"),
        BottomNavItem("Pharmacies", Icons.Default.LocationOn,  "pharmacies"),
        BottomNavItem("Invoices",   Icons.Default.Description, "invoices"),
        BottomNavItem("Account",    Icons.Default.Person,      "account")
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // الخط الفاصل فوق
        HorizontalDivider(
            color = Color(0xFFD6E6F5),
            thickness = 0.5.dp,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        // الـ Row الرئيسي
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp)
                .padding(bottom = 20.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->

                // مكان فاضي في النص للـ FAB
                if (index == 2) {
                    Spacer(modifier = Modifier.width(72.dp))
                }

                // كل تاب
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onItemClick(item.route) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) PharmaLinkBlue
                        else BottomNavIconOff,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                        fontWeight = if (currentRoute == item.route)
                            FontWeight.SemiBold else FontWeight.Normal,
                        color = if (currentRoute == item.route) PharmaLinkBlue
                        else BottomNavIconOff
                    )
                }
            }
        }

        // الـ FAB في النص فوق الـ Row
        FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = PharmaLinkBlue,
            elevation = FloatingActionButtonDefaults.elevation(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}