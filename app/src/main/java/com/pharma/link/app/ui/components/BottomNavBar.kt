package com.pharma.link.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
        BottomNavItem("Main",  Icons.Default.Home,        "home"),
        BottomNavItem("Pharmacies", Icons.Default.LocationOn,  "pharmacies"),
        BottomNavItem("Invoices",  Icons.Default.Description, "invoices"),
        BottomNavItem("Account",    Icons.Default.Person,      "account")
    )

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // الـ NavigationBar نفسه
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                // مكان فاضي في النص للـ FAB
                if (index == 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = { onItemClick(item.route) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            fontWeight = if (currentRoute == item.route)
                                FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PharmaLinkBlue,
                        selectedTextColor = PharmaLinkBlue,
                        unselectedIconColor = BottomNavIconOff,
                        unselectedTextColor = BottomNavIconOff,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }

        // الـ FAB في النص
        FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-16).dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = PharmaLinkBlue,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "إضافة",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable

fun PharmaBottomNavBarPreview(){
    PharmaBottomNavBar("home", {}, {})
}